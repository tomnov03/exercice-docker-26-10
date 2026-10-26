package com.exercice.logs;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    private final List<LogEntry> logs = new CopyOnWriteArrayList<>();

    @GetMapping
    public List<LogEntry> listLogs() {
        return Collections.unmodifiableList(logs);
    }

    @PostMapping
    public LogEntry addLog(@RequestBody LogEntry logEntry) {
        logs.add(logEntry);
        return logEntry;
    }
}
