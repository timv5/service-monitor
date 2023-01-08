package com.servicemonitor.serviceone.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/first-service")
public class OneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public OneController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "greetingFirstServiceCircuit", fallbackMethod = "responseFallback")
    @GetMapping("/greeting")
    public ResponseEntity<?> greetingFirstService(@RequestParam(value = "serviceName", defaultValue = "firstService") String serviceName) {
        LOGGER.info("Incoming request on greetingFirstService with {}", serviceName);
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8081/second-service/greeting?" + serviceName, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("Response from second-service: {}", String.valueOf(response.getBody()));
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<?> responseFallback(Exception e) {
        LOGGER.error("responseFallback: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
