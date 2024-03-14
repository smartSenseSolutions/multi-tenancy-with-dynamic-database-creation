package com.multitenant.multitenancy.config;

import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class TenantUtil {
    public static void runLiquibase() {
        try (Connection connection = getTenantDataSource().getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            new CommandScope(UpdateCommandStep.COMMAND_NAME)
                    .addArgumentValue("changelogFile", "migrations/changelog-master.xml")
                    .addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database)
                    .execute();

        } catch (LiquibaseException | SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void createDatabase(String dbName) {
        DataSource dataSource = getMasterDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute("CREATE DATABASE " + dbName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getMasterDataSource() {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:5432/mul-ten-master", "admin", "admin");
    }

    public static DataSource getTenantDataSource() {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:5432/" + TenantContext.getCurrentTenant(), "admin", "admin");
    }
}
