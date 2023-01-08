package com.servicemonitor.servicetwo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class SecondService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondService.class);

    public Optional<String> simulateSuccessErrorResponse(String serviceName) {
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            LOGGER.error("Error on second-service for {}", serviceName);
            return Optional.empty();
        } else {
            return Optional.of("Service " + serviceName + " responded");
        }
    }

}
