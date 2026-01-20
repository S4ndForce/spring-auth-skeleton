package com.example.system;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@RestController
public class ReadinessController {

    private final HikariDataSource dataSource;

    public ReadinessController(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/ready")
    public String ready() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            return "READY";
        }
    }
}
