package wf.garnier.springboottesting.todos.simple.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static wf.garnier.springboottesting.todos.simple.validation.ValidationResultAssert.assertThat;

class OneOfExactlyConstraintValidatorTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void valid() {
		assertThat(validator.validate(new Entity("one", null, null, null))).hasSize(0);
		assertThat(validator.validate(new Entity("one", null, null, "foo"))).hasSize(0);
		assertThat(validator.validate(new Entity(null, "two", null, null))).hasSize(0);
		assertThat(validator.validate(new Entity(null, "two", null, "foo"))).hasSize(0);
		assertThat(validator.validate(new Entity(null, null, "three", null))).hasSize(0);
		assertThat(validator.validate(new Entity(null, null, "three", "foo"))).hasSize(0);
	}

	@Test
	void multipleFields() {
		assertThat(validator.validate(new Entity("1", "2", null, null))).hasViolationForProperty("one",
				"Exactly one of the following properties should be set: [alpha, one, two]. Found 2 properties set: [one, two].");
		assertThat(validator.validate(new Entity("1", null, "a", null))).hasViolationForProperty("alpha",
				"Exactly one of the following properties should be set: [alpha, one, two]. Found 2 properties set: [alpha, one].");
		assertThat(validator.validate(new Entity(null, "2", "a", null))).hasViolationForProperty("alpha",
				"Exactly one of the following properties should be set: [alpha, one, two]. Found 2 properties set: [alpha, two].");
		assertThat(validator.validate(new Entity("1", "2", "a", null))).hasViolationForProperty("alpha",
				"Exactly one of the following properties should be set: [alpha, one, two]. Found 3 properties set: [alpha, one, two].");
		assertThat(validator.validate(new Entity("1", "2", "a", "foo"))).hasViolationForProperty("alpha",
				"Exactly one of the following properties should be set: [alpha, one, two]. Found 3 properties set: [alpha, one, two].");
	}

	@Test
	void noFields() {
		String expected = "Exactly one of the following properties should be set: [alpha, one, two]. Found 0 properties set: [].";
		assertThat(validator.validate(new Entity(null, null, null, null))).hasViolationForProperty("alpha", expected);
		assertThat(validator.validate(new Entity(null, null, null, "foo"))).hasViolationForProperty("alpha", expected);
	}

	@OneOf.Exactly
	private static class Entity {

		@OneOf
		public final String one;

		@OneOf
		private final String two;

		@OneOf
		private final String alpha;

		private final String four;

		private Entity(String one, String two, String alpha, String four) {
			this.one = one;
			this.two = two;
			this.alpha = alpha;
			this.four = four;
		}

	}

}
