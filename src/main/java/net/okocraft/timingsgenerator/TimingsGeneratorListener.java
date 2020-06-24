package net.okocraft.timingsgenerator;

import co.aikar.timings.Timings;
import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.configapi.bukkit.BukkitConfig;
import com.github.siroshun09.configapi.common.Configuration;
import com.github.siroshun09.filelogger.FileLogger;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
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

        if (config.getBoolean("auto-delete")) {
            scheduler.execute(this::checkLogFiles);
        }
    }

    @Override
    public void sendMessage(String message) {
        if (message != null) {
            fileLogger.write(ChatColor.stripColor(message));
        }
    }

    private void generate() {
        plugin.getServer().getScheduler().runTask(plugin, () -> Timings.generateReport(this));
    }

    private void reschedule() {
        long interval = config.getLong("interval", 3);
        scheduler.scheduleAtFixedRate(this::generate, interval, interval, TimeUnit.HOURS);
    }

    private void checkLogFiles() {
        Path dirPath = fileLogger.getDirectory();

        if (!Files.exists(dirPath)) {
            return;
        }

        try {
            Files.list(dirPath)
                    .filter(Files::isRegularFile)
                    .filter(this::isLogFile)
                    .filter(this::isExpired)
                    .forEach(this::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isExpired(Path path) {
        try {
            return 30 < Duration.between(Files.getLastModifiedTime(path).toInstant(), Instant.now()).toDays();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isLogFile(Path path) {
        return path.toString().endsWith(".log");
    }

    private void delete(Path path) {
        try {
            Files.deleteIfExists(path);
            plugin.getLogger().info("The file was deleted: " + path.toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}