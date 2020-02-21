package net.okocraft.timingsgenerator;

import com.github.siroshun09.sirolibrary.file.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class LogFileCheckingTask implements Runnable {

    @Override
    public void run() {
        Path dirPath = TimingsGenerator.get().getDataFolder().toPath().resolve("logs");
        if (FileUtil.isNotExist(dirPath)) {
            return;
        }

        try {
            List<Path> files = Files.list(dirPath)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".log"))
                    .filter(this::isExpired)
                    .collect(Collectors.toList());

            for (Path file : files) {
                Files.deleteIfExists(file);
                TimingsGenerator.get().getLogger().info("ファイルを削除しました: " + file.toAbsolutePath().toString());
            }
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
}
