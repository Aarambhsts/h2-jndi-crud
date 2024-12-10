package com.unisys.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * This method will run every 100 seconds.
     */
    @Scheduled(fixedRate = 100000) // Runs every 100 seconds
    public void performTaskEvery100Seconds() {
        logger.info("Task executed every 100 seconds");
        // Add your task logic here
    }

    /**
     * This method will run every day at 8 AM.
     */
    @Scheduled(cron = "0 0 8 * * ?") // Runs at 8 AM every day
    public void performTaskAt8AM() {
        logger.info("Task executed at 8 AM every day");
        // Add your task logic here
    }
}
