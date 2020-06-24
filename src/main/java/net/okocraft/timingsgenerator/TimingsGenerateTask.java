package net.okocraft.timingsgenerator;

import co.aikar.timings.Timings;

public class TimingsGenerateTask implements Runnable {

    private final TimingsGeneratorListener generator;

    TimingsGenerateTask(TimingsGeneratorListener generator) {
        this.generator = generator;
    }

    @Override
    public void run() {
        generator.getPlugin().getServer().getScheduler().runTask(generator.getPlugin(), this::generate);
        generator.reschedule();
    }

    private void generate() {
        Timings.generateReport(generator);
    }
}
