package com.exercice.dogs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogClient {

    private final RestTemplate restTemplate;
    private final String logsApiUrl;

    public LogClient(RestTemplate restTemplate, @Value("${logs.api.url}") String logsApiUrl) {
        this.restTemplate = restTemplate;
        this.logsApiUrl = logsApiUrl;
    }

    public void log(String level, String source, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        body.put("source", source);
        body.put("timestamp", Instant.now().toString());
        body.put("level", level);
        restTemplate.postForObject(logsApiUrl + "/api/v1/logs", body, Void.class);
    }
}
