package me.nicka.realclock.realclock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RealClock extends JavaPlugin {

    private static RealClock plugin;
    @Override
    public void onEnable() {

        plugin = this;
        getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ClockEvent(), this);

        Bukkit.getLogger().info("------------RealClock HAS BEEN ENABLED!!!------------");


    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("------------RealClock HAS BEEN DISABLED!!!------------");
    }

    public static RealClock getPlugin(){
        return plugin;
    }

}

