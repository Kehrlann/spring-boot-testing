package wf.garnier.springboottesting.todos.simple;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class TodoPropertiesTests {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

}
