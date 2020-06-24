package net.okocraft.timingsgenerator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TimingsGeneratorPlugin extends JavaPlugin {

    private final boolean isPaper = checkPaper();

    private static boolean checkPaper() {
        try {
            Class.forName("co.aikar.timings.Timing");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onDisable() {
        if (isPaper) {
            TimingsGenerator.shutdown();
            Bukkit.getScheduler().cancelTasks(this);
        }
    }

    @Override
    public void onEnable() {
        if (isPaper) {
            TimingsGenerator.start(this);
        } else {
            getLogger().severe("This server is not running on Paper!");
            getLogger().severe("Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}