package net.okocraft.timingsgenerator;

import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.sirolibrary.logging.FileLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class TimingsGeneratorListener extends TimingsReportListener {
    private final static TimingsGeneratorListener INSTANCE = new TimingsGeneratorListener();

    private final FileLogger fileLogger = new FileLogger(TimingsGenerator.get().getDataFolder().toPath().resolve("logs"));

    private TimingsGeneratorListener() {
        super(Bukkit.getConsoleSender());
    }

    @NotNull
    static TimingsGeneratorListener get() {
        return INSTANCE;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        fileLogger.write(ChatColor.stripColor(message));
    }
}