package wf.garnier.springboottesting.todos.simple;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static wf.garnier.springboottesting.todos.simple.Assertions.assertThat;
import static wf.garnier.springboottesting.todos.simple.Assertions.assertThatLog;

class ToolsTests {

	@Nested
	class AssertJ {

		record Book(String title, String author, String country, int publicationYear) {

		}

		List<Book> books = List.of(new Book("The three-body problem", "Liu Cixin", "PRC", 2015),
				new Book("The Fifth Season", "N.K. Jemisin", "USA", 2015),
				new Book("The Obelisk Gate", "N.K. Jemisin", "USA", 2016),
				new Book("The Stone Skies", "N.K. Jemisin", "USA", 2017),
				new Book("The calculating stars", "Mary Robinette Kowal", "USA", 20218));

		@Test
		void example() {
			assertThat(books).hasSize(5)
				.filteredOn(book -> book.publicationYear > 2016)
				.extracting(Book::title)
				.hasSize(2)
				.containsExactlyInAnyOrder("The calculating stars", "The Stone Skies");
		}

		@Test
		void assertions() {
			assertThatLog("🕵️ user with IP [127.0.0.1] requested [/todo.js]. We responded with [200].")
				.hasIp("127.0.0.1");
		}

	}

}
