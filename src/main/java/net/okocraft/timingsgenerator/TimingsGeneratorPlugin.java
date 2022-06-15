package net.okocraft.timingsgenerator;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class TimingsGeneratorPlugin extends JavaPlugin {

    private static final boolean IS_PAPER;

    static {
        boolean bool;

        try {
            Class.forName("co.aikar.timings.Timing");
            bool = true;
        } catch (ClassNotFoundException e) {
            bool = false;
        }

        IS_PAPER = bool;
    }

    private TimingsGenerator generator;

    @Override
    public void onDisable() {
        if (IS_PAPER) {
            generator.shutdown();
        }
    }

    @Override
    public void onEnable() {
        if (IS_PAPER) {
            try {
                generator = new TimingsGenerator(this);
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().severe("Failed to create the log directory.");
                getLogger().severe("Disabling plugin...");
                getServer().getPluginManager().disablePlugin(this);
            }
        } else {
            getLogger().severe("This server is not running on Paper!");
            getLogger().severe("Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}