package java.wf.garnier.springboottesting.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestSpringBootIntegrationTestingApplication.class)
class SpringBootIntegrationTestingApplicationTests {

	@Test
	void contextLoads() {
	}

}
