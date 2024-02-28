package org.jetbrains.assignment.containers;

import lombok.SneakyThrows;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class PostgresqlTestContainer {

    public static final String DB_NAME = "postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "mysecretpassword";
    public static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("registry.sovcombank.group/project-cache/library/postgres")
            .asCompatibleSubstituteFor("postgres");

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(DEFAULT_IMAGE_NAME.withTag("12"))
                .withCommand("postgres", "-c", "log_statement=all")
                .withDatabaseName(DB_NAME)
                .withUsername(USER)
                .withPassword(PASSWORD);
        POSTGRESQL_CONTAINER.start();
    }

    public static PostgreSQLContainer<?> defaultPostgresql() {
        return POSTGRESQL_CONTAINER;
    }

    public static void applyForLiquibase(ConfigurableApplicationContext configurableApplicationContext,
                                         String currentSchema) {
        apply(configurableApplicationContext, currentSchema);
        TestPropertyValues.of(
                "spring.liquibase.url=" + defaultPostgresql().getJdbcUrl(),
                "spring.liquibase.user=" + defaultPostgresql().getUsername(),
                "spring.liquibase.password=" + defaultPostgresql().getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }

    @SneakyThrows
    public static void apply(ConfigurableApplicationContext configurableApplicationContext,
                             String currentSchema) {
        POSTGRESQL_CONTAINER.execInContainer(
                "psql",
                "-U", USER,
                "-c", "CREATE SCHEMA IF NOT EXISTS " + currentSchema);

        final String baseUrl = defaultPostgresql().getJdbcUrl();
        final String delimiter = baseUrl.contains("?") ? "&" : "?";

        TestPropertyValues.of(
                "spring.datasource.url=" + baseUrl + delimiter + "currentSchema=" + currentSchema,
                "spring.datasource.username=" + defaultPostgresql().getUsername(),
                "spring.datasource.password=" + defaultPostgresql().getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
