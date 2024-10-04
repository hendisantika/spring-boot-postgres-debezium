package id.my.hendisantika.springbootpostgresdebezium;

import id.my.hendisantika.springbootpostgresdebezium.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(
        properties = {
                "management.endpoint.health.show-details=always",
                "spring.datasource.url=jdbc:tc:postgresql:17.0-alpine3.19:///debezium"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SpringBootPostgresDebeziumApplicationTests {


    @Autowired
    private PolicyRepository policyRepository;

    @BeforeEach
    void deleteAll() {
        policyRepository.deleteAll();
    }

}
