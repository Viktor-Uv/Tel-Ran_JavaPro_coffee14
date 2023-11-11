package com.example.coffee14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PeriodicTask {
    public static final Logger logger = LoggerFactory.getLogger(PeriodicTask.class);

    // https://www.baeldung.com/spring-scheduled-tasks

    //                 s m    h    M d   month
    @Scheduled(cron = "0 3,10 9-17 * 1-5 *", zone = "Europe/Paris")
    // @Scheduled(cron = "0 * * * * *", zone = "Europe/Paris")
    public void reportApplicationHealth() {
        logger.info("Current time is: " + System.currentTimeMillis());
    }
}
