package com.lidaqian.user_center.confgiration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlywayMigrationCustomStrategy implements FlywayMigrationStrategy {

    private final Config config;

    @Override
    public void migrate(Flyway flyway) {
        createDatabaseIfNotExists();

        flyway.migrate();
    }

    private void createDatabaseIfNotExists() {
        String url = config.getDatasource().getUrl();
        String username = config.getDatasource().getUsername();
        String password = config.getDatasource().getPassword();

        String dbName = url.substring(url.lastIndexOf("/") + 1, (url.contains("?") ? url.indexOf("?") : url.length()));
        String adminUrl = url.replace("/" + dbName, "/postgres");

        try (Connection conn = DriverManager.getConnection(adminUrl, username, password)) {
            ResultSet resultSet = conn.createStatement().executeQuery(
                "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");

            if (!resultSet.next()) {
                conn.createStatement().executeUpdate("CREATE DATABASE \"" + dbName + "\"");
                log.info("Database created: {}", dbName);
            }
        } catch (Exception e) {
            log.error("Error creating database: {}", dbName, e);
            throw new RuntimeException("Failed to create database: " + dbName, e);
        }
    }
}
