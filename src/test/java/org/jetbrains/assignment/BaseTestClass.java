package org.jetbrains.assignment;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.jetbrains.assignment.config.SQLStatementCountValidator;
import org.jetbrains.assignment.containers.PostgresqlTestContainer;
import org.jetbrains.assignment.repositories.RobotRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureObservability
@AutoConfigureWireMock(port = 0)
@ContextConfiguration(initializers = BaseTestClass.Initializer.class)
@SpyBeans({
        @SpyBean(SQLStatementCountValidator.class)
})
public abstract class BaseTestClass {

    @LocalServerPort
    protected String port;
    @Value("${wiremock.server.port}")
    protected String wiremockPort;

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected SQLStatementCountValidator sqlStatementCountValidator;
    @Autowired
    protected RobotRepository robotRepository;

    @AfterEach
    final void afterEach() {
        WireMock.resetAllRequests();
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "coordinates"
        );
    }

    protected String buildURL(String resource) {
        return "http://localhost:" + port + resource;
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgresqlTestContainer.applyForLiquibase(applicationContext, "robot");
        }
    }

}
