package wf.garnier.springboottesting.todos.simple;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Tools {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//
	// 📚Book stuff!
	//
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	record Book(String author, String title) {

	}

	//@formatter:off
    List<Book> books = List.of(
            new Book("Becky Chambers", "The Galaxy and the Ground Within"),
            new Book("Liu Cixin", "The Three Body Problem"),
            new Book("T. Kingfisher", "Nettle and Bone"),
            new Book("Ann Leckie", "Ancillary Justice")
    );
    //@formatter:on

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//
	// ⚙️Automated process stuff!
	//
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	static class RandomProcess {

		// Thread-safety c'est pour les fragiles 😈
		boolean complete = false;

		void start() {
			new Thread(() -> {
				var random = new Random();
				try {
					Thread.sleep(Duration.ofMillis(random.nextInt(500, 2000)));
				}
				catch (InterruptedException _) {
				}
				complete = true;
			}).start();
		}

		boolean isComplete() {
			return complete;
		}

	}

}
