package org.night.nightcore.stats.statdatabase;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.night.nightcore.Main;
import org.night.nightcore.stats.statmanager.PlayerAccount;

import java.io.Console;
import java.sql.*;
import java.util.UUID;

public class PvPDatabase {
    private Main plugin;
    private String username3 = Main.getInstance().getConfig().getString("MySQL Username");
    private String url3 = Main.getInstance().getConfig().getString("MySQL Url");
    private String password3 = Main.getInstance().getConfig().getString("MySQL Password");
    private Connection baglanti;


    public PvPDatabase(Main plugin) {
        this.plugin = plugin;
    }

    public Connection connect() throws SQLException {
        if (this.baglanti == null || this.baglanti.isClosed()) {
            this.baglanti = DriverManager.getConnection(this.url3, this.username3, this.password3);
        }
        return this.baglanti;
    }

    public void disconnect() {if (this.baglanti != null) {try {this.baglanti.close();} catch (SQLException e) {e.printStackTrace();}}}

    public void createTablePvP() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS statNight (id INT AUTO_INCREMENT PRIMARY KEY,`uuid` VARCHAR(255) NOT NULL,playerName VARCHAR(255) NOT NULL,olum INT DEFAULT 0,death INT DEFAULT 0,elo INT DEFAULT 0,streak INT DEFAULT 0)";
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        try { stmt.execute(sql); } catch (Exception e) { stmt.close(); }
        disconnect();
    }

    public void createPlayer(String uuid, String playerName, int kill, int death, int elo, int streak) throws SQLException {
        String sql = "INSERT INTO statNight(`uuid`, playerName, olum, death, elo, streak) VALUES(?, ?, ?, ?, ?, ?)";
        try {Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, playerName);
            pstmt.setInt(3, kill);
            pstmt.setInt(4, death);
            pstmt.setInt(5, elo);
            pstmt.setInt(6, streak);
            pstmt.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}    //afk biraz
        disconnect();
    }

    public void putAllAccounts() throws SQLException {
        var PvPDatabaseMap = Main.getInstance().getPvPDatabaseMap();
        var MySQLAccountMap = Main.getInstance().getMySQLAccountMap();
        var PapiMap = Main.getInstance().getPapiMap();
        String sql3 = "UPDATE statNight SET olum = ?, death = ?, elo = ?, streak = ? WHERE `uuid` = ?";
        try {Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql3);
            conn.setAutoCommit(false);
            Bukkit.getConsoleSender().sendMessage("Burası çalıştı 1 (try)");
            for (PlayerAccount account : PvPDatabaseMap.values()) {
                Bukkit.getConsoleSender().sendMessage("Burası çalıştı 2 (for) " + account.getUuid());
                PlayerAccount MySQLAcc = MySQLAccountMap.getOrDefault(UUID.fromString(account.getUuid()), null);
                if (MySQLAcc != null) {
                    Bukkit.getConsoleSender().sendMessage(""+ MySQLAcc.getPlayerElo());
                    Bukkit.getConsoleSender().sendMessage(""+ account.getPlayerElo());
                    if(account != MySQLAcc) {
                        Bukkit.getConsoleSender().sendMessage("Burası çalıştı 3 (if)");
                        Bukkit.getConsoleSender().sendMessage("Burası çalıştı 3 " + account.getUuid());
                        Bukkit.getConsoleSender().sendMessage("Burası çalıştı 3 " + account.getPlayerElo());
                        pstmt.setInt(1, account.getPlayerKill());
                        pstmt.setInt(2, account.getPlayerDeath());
                        pstmt.setInt(3, account.getPlayerElo());
                        pstmt.setInt(4, account.getPlayerStreak());
                        pstmt.setString(5, account.getUuid());
                        pstmt.addBatch();
                        MySQLAccountMap.clear();
                        MySQLAccountMap.putAll(PvPDatabaseMap);
                    }
                }else if(MySQLAcc == null) { MySQLAccountMap.clear(); MySQLAccountMap.putAll(PvPDatabaseMap); Bukkit.getConsoleSender().sendMessage("Burası çalıştı 3 (else if)");}
            }
            Bukkit.getConsoleSender().sendMessage("Burası çalıştı 4 (bitiş)");
            pstmt.executeBatch();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("Burası çalıştı 5 (catch)");
        }
        disconnect();
        PapiMap.clear();
        PapiMap.addAll(PvPDatabaseMap.values());
        Bukkit.getConsoleSender().sendMessage("Burası çalıştı 6 (en son)");
    }

    public void getPlayerAccount() throws SQLException {
        var PvPDatabaseMap = Main.getInstance().getPvPDatabaseMap();
        var MySQLAccountMap = Main.getInstance().getMySQLAccountMap();
        PvPDatabaseMap.clear();
        MySQLAccountMap.clear();
        String sql = "SELECT * FROM statNight";
        try {Connection conn = connect(); Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                PlayerAccount playerAccount = new PlayerAccount(rs.getString("`uuid`"), rs.getString("playerName"), rs.getInt("olum"), rs.getInt("death"), rs.getInt("elo"), rs.getInt("streak"));
                PvPDatabaseMap.put(UUID.fromString(rs.getString("`uuid`")), playerAccount);
                MySQLAccountMap.put(UUID.fromString(rs.getString("`uuid`")), playerAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public boolean playerAccountTrue(String uuid) {
        return Main.getInstance().getPvPDatabaseMap().get(UUID.fromString(uuid)) != null;
    }

    public void deleteAll() throws SQLException {
        String sql = "DROP TABLE statNight";
        try {Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {e.printStackTrace();}
        disconnect();
    }
    
}