package wf.garnier.springboottesting.todos.simple;

import java.util.regex.Pattern;

import org.assertj.core.api.AbstractObjectAssert;

import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;

class Assertions {

	static class LoggingAssert extends AbstractObjectAssert<LoggingAssert, String> {

		private static final Pattern pattern = Pattern.compile(
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

}
