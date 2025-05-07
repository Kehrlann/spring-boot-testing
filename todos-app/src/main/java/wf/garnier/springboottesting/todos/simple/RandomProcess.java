package wf.garnier.springboottesting.todos.simple;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

class RandomProcess {

	private final AtomicBoolean completed = new AtomicBoolean(false);

	private final Thread thread;

	public RandomProcess(Duration atLeast, Duration atMost) {
		this.thread = new Thread(() -> {
			var random = new Random();
			var sleepDuration = random.nextLong(atLeast.toMillis(), atMost.toMillis());
			try {
				Thread.sleep(sleepDuration);
				completed.set(true);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void start() {
		this.thread.start();
	}

	public boolean isCompleted() {
		return completed.get();
	}

}
