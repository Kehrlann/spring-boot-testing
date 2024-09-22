package wf.garnier.springboottesting.todos.simple.reference;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.FactoryBasedNavigableListAssert;
import wf.garnier.springboottesting.todos.simple.validation.ValidationResultAssert;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.http.HttpStatus;

/**
 * Single entrypoint for all assertThat calls.
 *
 * @see <a href=
 * "https://joel-costigliola.github.io/assertj/assertj-core-custom-assertions.html#single-assertion-entry-point">assertJ
 * documentation</a>
 */
class Assertions extends org.assertj.core.api.Assertions {

	public static LoggingListAssert assertThat(CapturedOutput output) {
		return LoggingListAssert.assertThat(output);
	}

	public static LoggingAssert assertThatLog(String logLine) {
		return new LoggingAssert(logLine);
	}

	public static <T> ValidationResultAssert<T> assertThatValidation(Collection<ConstraintViolation<T>> violations) {
		return ValidationResultAssert.assertThat(violations);
	}

	/**
	 * Assert on a log line, to make sure it has the correct IP, requested page, and
	 * response status.
	 */
	static public class LoggingAssert extends AbstractObjectAssert<LoggingAssert, String> {

		static final Pattern pattern = Pattern.compile(
				"user with IP \\[(?<ip>[\\d.]+)] requested \\[(?<request>[\\w/.?#]+)]. We responded with \\[(?<status>\\d{3})]");

		private final String ip;

		private final String request;

		private final String status;

		public LoggingAssert(String actual) {
			super(actual, LoggingAssert.class);
			var matcher = pattern.matcher(actual);
			if (!matcher.find()) {
				throw new AssertionError("Invalid log line");
			}
			ip = matcher.group("ip");
			request = matcher.group("request");
			status = matcher.group("status");
		}

		public LoggingAssert hasIp(String ip) {
			assertThat(this.ip).withFailMessage("expected ip to be [%s] but was [%s]", ip, this.ip).isEqualTo(ip);
			return myself;
		}

		public LoggingAssert hasRequest(String request) {
			assertThat(this.request)
				.withFailMessage("expected requested page to be [%s] but was [%s]", request, this.request)
				.isEqualTo(request);
			return myself;
		}

		public LoggingAssert hasStatus(HttpStatus status) {
			assertThat(this.status)
				.withFailMessage("expected status to be [%s] but was [%s]", status.value(), this.status)
				.asInt()
				.isEqualTo(status.value());
			return myself;
		}

	}

	/**
	 * Utility class to have assertions on a list of
	 */
	static public class LoggingListAssert
			extends FactoryBasedNavigableListAssert<LoggingListAssert, List<String>, String, LoggingAssert> {

		public LoggingListAssert(List<String> maps) {
			super(maps, LoggingListAssert.class, LoggingAssert::new);
		}

		static LoggingListAssert assertThat(CapturedOutput output) {
			var lines = output.getAll().split("\\n");
			var filtered = Arrays.stream(lines).filter(l -> LoggingAssert.pattern.matcher(l).find()).toList();
			return new LoggingListAssert(filtered);
		}

	}

}
