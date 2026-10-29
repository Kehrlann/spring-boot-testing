package wf.garnier.springboottesting.todos.simple;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ContainersConfiguration.class)
@Tag("integ")
class TodoApplicationTests {

	@Test
	void contextLoads() {
	}

}
