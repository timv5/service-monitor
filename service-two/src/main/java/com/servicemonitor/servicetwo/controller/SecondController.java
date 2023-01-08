package com.servicemonitor.servicetwo.controller;

import com.servicemonitor.servicetwo.service.SecondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/second-service")
public class SecondController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondController.class);

    private final SecondService service;

    @Autowired
    public SecondController(SecondService service) {
        this.service = service;
    }

    @GetMapping("/greeting")
    public ResponseEntity<String> greetingSecondService(@RequestParam(value = "serviceName", defaultValue = "secondService") String serviceName) {
        LOGGER.info("Incoming request on service-two greetingSecondService(): {}", serviceName);
        Optional<String> response = service.simulateSuccessErrorResponse(serviceName);
         return response.map(s -> ResponseEntity.status(HttpStatus.OK).body(s))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
