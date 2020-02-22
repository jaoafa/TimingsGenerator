package net.okocraft.timingsgenerator;

import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.sirolibrary.logging.FileLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class TimingsGeneratorListener extends TimingsReportListener {

    private final FileLogger fileLogger = new FileLogger(TimingsGenerator.get().getDataFolder().toPath().resolve("logs"));

    TimingsGeneratorListener() {
        super(Bukkit.getConsoleSender());
    }

    @Override
    public void sendMessage(@NotNull String message) {
        fileLogger.write(ChatColor.stripColor(message));
    }
}