package wf.garnier.springboottesting;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.awaitility.Awaitility;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

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
				.atMost(Duration.ofMillis(200))
				.pollDelay(Duration.ZERO)
				.pollInterval(Duration.ofMillis(10))
				.until(() -> {
			var tryCount = counter.getAndIncrement();
			System.out.println("Trying ... " + tryCount);
			return random.nextInt(10) == 9;
		});
	}


	// TODO: Hamcrest vs AssertJ

}
