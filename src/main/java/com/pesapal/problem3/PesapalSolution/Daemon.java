package com.pesapal.problem3.PesapalSolution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = "https://github.com/puumCore">GitHub: Mandela Muriithi</a>
 * @version 1.3
 * @since 15/08/2022
 */

@Configuration
@EnableScheduling
@Slf4j
public class Daemon {

    @Autowired
    private Functions functions;

    @Scheduled(initialDelay = 30, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    void update_active_clients() {
        log.info("Updating connected clients...");
        new Thread(functions.update_online_clients()).start();
    }

}
