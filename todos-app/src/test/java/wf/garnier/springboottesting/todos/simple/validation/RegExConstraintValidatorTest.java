package wf.garnier.springboottesting.todos.simple.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static wf.garnier.springboottesting.todos.simple.validation.ValidationResultAssert.assertThat;

class RegExConstraintValidatorTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@ParameterizedTest
	@ValueSource(strings = { ".*", "^adm", "per$" })
	void validRegEx(String regex) {
		assertThat(validator.validate(new Entity(regex))).isValid();
	}

	@ParameterizedTest
	@ValueSource(strings = { "[...../", "x[\\x" })
	void invalidRegEx(String regex) {
		assertThat(validator.validate(new Entity(regex)))
			.hasViolationWithMessage("Provided string is not a valid regular expression.");
	}

	@Test
	void nullRegex() {
		assertThat(validator.validate(new Entity(null))).isValid();
	}

	record Entity(@RegEx String filter) {

	}

}
