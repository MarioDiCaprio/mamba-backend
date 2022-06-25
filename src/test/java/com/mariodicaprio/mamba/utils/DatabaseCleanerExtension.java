package com.mariodicaprio.mamba.utils;


import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataSource dataSource = loadDataSource();
        cleanDatabase(dataSource);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private DataSource loadDataSource() throws IOException {
        String source = "application.properties";

        PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
        List<PropertySource<?>> propertySources = loader.load(source, new ClassPathResource(source));
        PropertySource<?> propertySource = propertySources.get(0);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.getPropertySources().addFirst(propertySource);

        DataSourceProperties properties =
                Binder
                .get(environment)
                .bind("spring.datasource", Bindable.of(DataSourceProperties.class))
                .get();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(properties.getUrl());
        dataSource.setUser(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    private void cleanDatabase(DataSource dataSource) throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            List<String> tables = loadTables(connection);
            cleanTables(tables, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new SQLException("Failed to clean up database");
        }
    }

    private List<String> loadTables(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), null, null, new String[] {"TABLE"});
        List<String> result = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            result.add(name);
        }
        return result;
    }

    private void cleanTables(List<String> tables, Connection connection) throws SQLException {
        if (tables.isEmpty()) {
            return;
        }
        String comaSeparatedTables = String.join(", ", tables);
        String command = "TRUNCATE " + comaSeparatedTables;
        connection.prepareStatement(command).execute();
    }

}
