package org.night.nightcore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.night.nightcore.main.ConfigManager;
import org.night.nightcore.main.MainListener;
import org.night.nightcore.minimessage.Minimessage;
import org.night.nightcore.stats.autosave.AutoSave;
import org.night.nightcore.stats.placeholder.Papi;
import org.night.nightcore.stats.placeholder.PlaceHolderLists;
import org.night.nightcore.stats.statdatabase.PvPDatabase;
import org.night.nightcore.stats.statmanager.Commandelo;
import org.night.nightcore.stats.statmanager.Commands;
import org.night.nightcore.stats.statmanager.PlayerAccount;
import org.night.nightcore.stats.statmats.DeadnKillMath;
import org.night.nightcore.stats.statmats.EloMath;
import org.night.nightcore.stats.statmats.StrikeMath;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

public class Main extends JavaPlugin {
    private static Main plugin;

    @Getter private Map<Server, BukkitTask> timetask = new HashMap<>();
    @Getter private Map<UUID, PlayerAccount> PvPDatabaseMap = new HashMap<>();
    @Getter private List<PlayerAccount> PapiMap = new ArrayList<>();
    @Getter private Map<UUID, PlayerAccount> MySQLAccountMap = new HashMap<>();
    @Getter private PvPDatabase pvPDatabase;
    @Getter private DeadnKillMath deadnKillMath;
    @Getter private EloMath eloMath;
    @Getter private StrikeMath strikeMath;
    @Getter private Minimessage minimessage;
    @Getter private PlaceHolderLists placeHolderLists;
    @Getter private ConfigManager configManager;
    @Getter private AutoSave autoSave;
    public static Main getInstance() {
        return plugin;
    }
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        pvPDatabase = new PvPDatabase(this);
        deadnKillMath = new DeadnKillMath(this);
        eloMath = new EloMath(this);
        strikeMath = new StrikeMath(this);
        minimessage = new Minimessage(this);
        placeHolderLists = new PlaceHolderLists(this);
        autoSave = new AutoSave(this);
        nightsender();
        new MainListener();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "Config yapılandırmaları işleniyor.");
        configManager = new ConfigManager(getConfig().getInt("AntiAbuse"),getConfig().getInt("AntiAbuseSecond"),getConfig().getInt("AutoSaveTick"),
                getConfig().getInt("ListCommandsActive"),getConfig().getStringList("ListCommands"),getConfig().getString("ServerName"),
                getConfig().getInt("AntiItemNameAnvil"),getConfig().getInt("WhiteListMode"),getConfig().getStringList("WhiteListRegions"));
        setConfigManager();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "Tablo ayarları yapılıyor");
        try {getPvPDatabase().createTablePvP();} catch (SQLException e) {throw new RuntimeException(e);}
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "Hesaplar MySQL'den çekiliyor.");
        try {getPvPDatabase().getPlayerAccount();} catch (SQLException e) {throw new RuntimeException(e);}
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "Komutlar aktifleştiriliyor.");
        Objects.requireNonNull(getCommand("nightcore")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("nightcore")).setTabCompleter(new Commands());
        Objects.requireNonNull(getCommand("elo")).setExecutor(new Commandelo());
        Objects.requireNonNull(getCommand("elo")).setTabCompleter(new Commandelo());
        getAutoSave().SaveTask();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "Placeholderler kaydediliyor");
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {new Papi(plugin).register();}
        nightsender();
    }
    public void onDisable() {
        try {Main.getInstance().getPvPDatabase().putAllAccounts();} catch (SQLException e) {throw new RuntimeException(e);}pvPDatabase.disconnect();
        BukkitTask timetsk = getTimetask().remove(Bukkit.getServer());
        if (timetsk != null) timetsk.cancel();
        nightsender();
        timetsk.cancel();
        getPvPDatabaseMap().clear();
        getPapiMap().clear();
        getMySQLAccountMap().clear();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + " NightCore Disabled.");
        nightsender();
    }
    public static YamlConfiguration getLang() {
        String locale = getInstance().getConfig().getString("Language");
        String fileName = locale.equals("tr_TR") ? "tr_TR.yml" : "en_US.yml";
        File langFile = new File(getInstance().getDataFolder(), fileName);
        try {
            if (!langFile.exists() || YamlConfiguration.loadConfiguration(langFile).getKeys(true).isEmpty()) {
                langFile.createNewFile();
                InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getInstance().getResource(fileName)), StandardCharsets.UTF_8);
                YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);
                config.setDefaults(YamlConfiguration.loadConfiguration(reader));
                config.options().copyDefaults(true);
                config.save(langFile);
                reader.close();
                return config;
            }
            return YamlConfiguration.loadConfiguration(langFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void nightsender() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "███╗   ██╗██╗ ██████╗ ██╗  ██╗████████╗");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "████╗  ██║██║██╔════╝ ██║  ██║╚══██╔══╝");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "██╔██╗ ██║██║██║  ███╗███████║   ██║   ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "██║╚██╗██║██║██║   ██║██╔══██║   ██║  ____ ___ _  _ ___  _ ____ ____ ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "██║ ╚████║██║╚██████╔╝██║  ██║   ██║  [__   |  |  | |  | | |  | [__  ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "╚═╝  ╚═══╝╚═╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝  ___]  |  |__| |__/ | |__| ___] ");
    }
    public void setConfigManager() {
        getConfigManager().setAntiAbuse(getConfig().getInt("AntiAbuse"));
        getConfigManager().setAntiAbuseSecond(getConfig().getInt("AntiAbuseSecond"));
        getConfigManager().setAutoSaveTick(getConfig().getInt("AutoSaveTick"));
        getConfigManager().setListCommandsActive(getConfig().getInt("ListCommandsActive"));
        getConfigManager().setListCommands(getConfig().getStringList("ListCommands"));
        getConfigManager().setServerName(getConfig().getString("ServerName"));
        getConfigManager().setAntiItemNameAnvil(getConfig().getInt("AntiItemNameAnvil"));
        getConfigManager().setWhiteListMode(getConfig().getInt("WhiteListMode"));
        getConfigManager().setWhiteListRegions(getConfig().getStringList("WhiteListRegions"));
    }
}