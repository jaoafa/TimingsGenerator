package net.okocraft.timingsgenerator;

import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.configapi.bukkit.BukkitConfig;
import com.github.siroshun09.configapi.common.Configuration;
import com.github.siroshun09.filelogger.FileLogger;
import org.bukkit.ChatColor;

public class TimingsGeneratorListener extends TimingsReportListener {

    private final Configuration config;
    private final FileLogger fileLogger;

    TimingsGeneratorListener(TimingsGeneratorPlugin plugin) {
        super(plugin.getServer().getConsoleSender());

        config = new BukkitConfig(plugin, "config.yml", true);
        fileLogger = new FileLogger(plugin.getDataFolder().toPath().resolve("logs"));
    }

    @Override
    public void sendMessage(String message) {
        if (message != null) {
            fileLogger.write(ChatColor.stripColor(message));
        }
    }
}