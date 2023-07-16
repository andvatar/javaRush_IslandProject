package com.example.islandproject;

import java.util.concurrent.*;

public class IslandLauncher {
    public static void main(String[] args) {
        IslandSettings settings = new IslandSettings();
        Island island = new Island(settings);
        System.out.println(island);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(island::startIteration, 1,5, TimeUnit.SECONDS);

    }
}
