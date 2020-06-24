package net.okocraft.timingsgenerator;

import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.configapi.bukkit.BukkitConfig;
import com.github.siroshun09.configapi.common.Configuration;
import com.github.siroshun09.filelogger.FileLogger;
import org.bukkit.ChatColor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimingsGeneratorListener extends TimingsReportListener {

    private final TimingsGeneratorPlugin plugin;

    private final Configuration config;
    private final FileLogger fileLogger;
    private final ScheduledExecutorService scheduler;

    TimingsGeneratorListener(TimingsGeneratorPlugin plugin) {
        super(plugin.getServer().getConsoleSender());

        this.plugin = plugin;

        config = new BukkitConfig(plugin, "config.yml", true);
        fileLogger = new FileLogger(plugin.getDataFolder().toPath().resolve("logs"));
        scheduler = Executors.newSingleThreadScheduledExecutor();

        reschedule();
    }

    @Override
    public void sendMessage(String message) {
        if (message != null) {
            fileLogger.write(ChatColor.stripColor(message));
        }
    }

    TimingsGeneratorPlugin getPlugin() {
        return plugin;
    }

    void reschedule() {
        long interval = config.getLong("interval", 3);
        scheduler.scheduleAtFixedRate(new TimingsGenerateTask(this), interval, interval, TimeUnit.HOURS);
    }
}