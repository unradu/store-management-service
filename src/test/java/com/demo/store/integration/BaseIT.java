package com.demo.store.integration;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

abstract class BaseIT {
    @ServiceConnection
    protected static final MySQLContainer<?> mySqlContainer = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    static {
        mySqlContainer.setPortBindings(List.of("3307:3306"));
        mySqlContainer.start();
    }
}
