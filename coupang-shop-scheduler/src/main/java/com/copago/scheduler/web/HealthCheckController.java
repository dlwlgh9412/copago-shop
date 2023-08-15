package com.copago.scheduler.web;

import com.copago.scheduler.web.dto.HealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private final BuildProperties buildProperties;
    private final Environment environment;

    @GetMapping({"/", "/health"})
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(
                new HealthResponse(buildProperties.getName(), Arrays.stream(environment.getActiveProfiles()).collect(Collectors.toList()), "OK", LocalDateTime.now()));
    }
}
