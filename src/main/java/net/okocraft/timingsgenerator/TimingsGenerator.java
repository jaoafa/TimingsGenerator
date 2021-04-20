package net.okocraft.timingsgenerator;

import co.aikar.timings.Timings;
import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.configapi.bukkit.BukkitYamlFactory;
import com.github.siroshun09.configapi.common.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimingsGenerator extends TimingsReportListener {

    private final TimingsGeneratorPlugin plugin;

    private final Configuration config;
    private final Path dirPath;
    private final ScheduledExecutorService scheduler;

    TimingsGenerator(TimingsGeneratorPlugin plugin) throws IOException {
        super(plugin.getServer().getConsoleSender());

        this.plugin = plugin;

        config = BukkitYamlFactory.loadUnsafe(plugin, "config.yml");
        dirPath = plugin.getDataFolder().toPath().resolve("logs");
        if(!Files.exists(dirPath)){
            Files.createDirectory(dirPath);
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();

        reschedule();

        if (config.getBoolean("auto-delete")) {
            scheduler.execute(this::checkLogFiles);
        }
    }

    void shutdown() {
        scheduler.shutdown();

        try {
            boolean bool = scheduler.awaitTermination(1, TimeUnit.SECONDS);
            if(!bool) System.out.println("Error: Scheduler timeout.");
        } catch (InterruptedException e) {
            plugin.getLogger().log(Level.SEVERE, "", e);
            e.printStackTrace();
        }

        plugin.getServer().getScheduler().cancelTasks(plugin);
    }

    @Override
    public void sendMessage(String message){
        Pattern pattern = Pattern.compile("View Timings Report: (.+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            double[] tps = Bukkit.getServer().getTPS();
            String result = Arrays.toString(tps) + " " + matcher.group(1);
            scheduler.execute(() -> write(ChatColor.stripColor(result)));
        }
    }

    private void generate() {
        plugin.getServer().getScheduler().runTask(plugin, () -> Timings.generateReport(this));
    }

    private void reschedule() {
        long interval = config.getLong("interval", 1);
        scheduler.scheduleAtFixedRate(this::generate, interval, interval, TimeUnit.HOURS);
    }

    private void write(String str) {
        LocalDate date = LocalDate.now();
        Path file = dirPath.resolve(DateTimeFormatter.ISO_LOCAL_DATE.format(date) + ".log");

        try (BufferedWriter writer = Files.newBufferedWriter(file,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
            writer.write(str);
            writer.newLine();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not write to " + file, e);
        }
    }

    private void checkLogFiles() {
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
            plugin.getLogger().info("The file was deleted: " + path.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}