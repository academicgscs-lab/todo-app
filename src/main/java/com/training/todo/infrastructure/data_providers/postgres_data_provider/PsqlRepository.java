package com.training.todo.infrastructure.data_providers.postgres_data_provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class PsqlRepository {
    private final String url;
    private final String user;
    private final String password;

    public PsqlRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
