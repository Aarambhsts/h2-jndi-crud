package com.unisys.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    /**
     * This method will run every 10 seconds.
     */
    @Scheduled(fixedRate = 10000) // Runs every 10 seconds
    public void performTaskEvery10Seconds() {
        System.out.println("Task executed every 10 seconds");
        // Add your task logic here
    }

    /**
     * This method will run every day at 8 AM.
     */
    @Scheduled(cron = "0 0 8 * * ?") // Runs at 8 AM every day
    public void performTaskAt8AM() {
        System.out.println("Task executed at 8 AM every day");
        // Add your task logic here
    }
}
