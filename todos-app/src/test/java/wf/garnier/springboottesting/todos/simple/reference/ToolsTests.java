package wf.garnier.springboottesting.todos.simple.reference;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.testcontainers.shaded.org.awaitility.Awaitility;

import static wf.garnier.springboottesting.todos.simple.reference.Assertions.assertThat;
import static wf.garnier.springboottesting.todos.simple.reference.Assertions.assertThatLog;

class ToolsTests {

	class AssertJ {

		record Book(String title, String author, String country, int publicationYear) {

		}

		List<Book> books = List.of(new Book("The three-body problem", "Liu Cixin", "PRC", 2015),
				new Book("The Fifth Season", "N.K. Jemisin", "USA", 2015),
				new Book("The Obelisk Gate", "N.K. Jemisin", "USA", 2016),
				new Book("The Stone Skies", "N.K. Jemisin", "USA", 2017),
				new Book("The calculating stars", "Mary Robinette Kowal", "USA", 20218));

		void example() {
			assertThat(books).hasSize(5)
				.filteredOn(book -> book.publicationYear > 2016)
				.extracting(Book::title)
				.hasSize(2)
				.containsExactlyInAnyOrder("The calculating stars", "The Stone Skies");
		}

		void awaitility() {
			var dice = new Dice(20);
			var count = new AtomicInteger(1);

			//@formatter:off
			Awaitility.await()
					.timeout(Duration.ofSeconds(2))
					.pollInterval(Duration.ofMillis(10))
					.untilAsserted(() -> {
						var result = dice.next();
						System.out.printf("Throw #%s - got %s%n", count.getAndIncrement(), result);
						assertThat(result).isEqualTo(dice.maxValue());
					});
			//@formatter:on;
		}

		void assertions() {
			assertThatLog("🕵️ user with IP [127.0.0.1] requested [/todo.js]. We responded with [200].")
				.hasIp("127.0.0.1");
		}

		static class Dice {

			private final int sides;

			private final Random random = new Random();

			public Dice(int sides) {
				this.sides = sides;
			}

			public int next() {
				return this.random.nextInt(1, this.sides + 1);
			}

			public int maxValue() {
				return this.sides;
			}

		}

	}

}
