package com.example.demo;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.concurrent.ScheduledExecutorService;

@SpringBootTest(classes = DemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(value = "file:src/test/resources/application.properties")
public abstract class AppTests {
    public static  PostgreSQLContainer<?> postgreSQLContainer;
    @MockBean
    private ScheduledExecutorService schedulerService;

    @BeforeAll
    static void setUpAll() {
        if (postgreSQLContainer == null) {
            postgreSQLContainer =  new PostgreSQLContainer<>("postgres:latest")
                    .withExposedPorts(5432)
                    .withDatabaseName("test")
                    .withUsername("postgres")
                    .withPassword("abc")
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5444), new ExposedPort(5432)))
                    ));
            postgreSQLContainer.start();
        }
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }


}
