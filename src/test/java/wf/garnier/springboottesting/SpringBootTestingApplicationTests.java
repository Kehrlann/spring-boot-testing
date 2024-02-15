package wf.garnier.springboottesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestSpringBootTestingApplication.class)
class SpringBootTestingApplicationTests {

	@Test
	void contextLoads() {
	}

}
