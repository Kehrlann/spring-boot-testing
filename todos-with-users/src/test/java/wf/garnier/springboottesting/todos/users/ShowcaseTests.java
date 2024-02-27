package wf.garnier.springboottesting.todos.users;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.awaitility.Awaitility;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.json.BasicJsonTester;

import static org.assertj.core.api.Assertions.assertThat;

class ShowcaseTests {

	@Test
	void jsonComparison() throws JSONException {
		var expected = "{ \"name\": \"Daniel\", \"root\" :  { \"nestedValue\":  42 }}";
		var actual = "{ \"root\" :  { \"nestedValue\":  42 }, \"name\": \"Daniel\", \"extraProperty\": \"ignored\"}";
		JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
	}

	@Test
	void awaitility() {
		var random = new Random();
		var counter = new AtomicInteger(0);
		Awaitility.await()
			.atMost(Duration.ofMillis(500))
			.pollDelay(Duration.ZERO)
			.pollInterval(Duration.ofMillis(10))
			.until(() -> {
				var tryCount = counter.getAndIncrement();
				System.out.println("Trying ... " + tryCount);
				return random.nextInt(10) == 9;
			});
	}

	private BasicJsonTester json = new BasicJsonTester(getClass());

	@Test
	void assertJ() {
		//@formatter:off
		var characters = List.of(
				new Character("Frodo", "Hobbit"),
				new Character("Sam", "Hobbit"),
				new Character("Gandalf", "Wizard"),
				new Character("Legolas", "Elf")
		);
		//@formatter:on

		assertThat(characters).hasSize(3)
			.filteredOn(c -> c.charClass.equals("Hobbit"))
			.hasSize(2)
			.extracting(Character::name)
			.containsExactlyInAnyOrder("Sam", "Frodo");
	}

	@Test
	void assertJWithJson() {
		var charJson = "{\"characters\": [{\"name\": \"Frodo\", \"charClass\": \"Hobbit\"}, {\"name\": \"Gandalf\", \"charClass\": \"Wizard\"}, {\"name\": \"Sam\", \"charClass\":  \"Hobbit\"}]}";
		assertThat(json.from(charJson)).extractingJsonPathArrayValue("$.characters[?(@.charClass == 'Hobbit')].name")
			.containsExactly("Frodo", "Sam");
	}

	record Character(String name, String charClass) {
	}

}
