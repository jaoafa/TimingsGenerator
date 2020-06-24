package net.okocraft.timingsgenerator;

import co.aikar.timings.TimingsReportListener;
import com.github.siroshun09.filelogger.FileLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.nio.file.Path;

public class TimingsGeneratorListener extends TimingsReportListener {

    private final FileLogger fileLogger;

    TimingsGeneratorListener(Path dir) {
        super(Bukkit.getConsoleSender());

        fileLogger = new FileLogger(dir);
    }

    @Override
    public void sendMessage(String message) {
        if (message != null) {
            fileLogger.write(ChatColor.stripColor(message));
        }
    }
}