package wf.garnier.springboottesting.todos.simple.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * This qualifier is used to denote String values that should be a Regular expression.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { RegExConstraintValidator.class })
public @interface RegEx {

	String message() default "Provided string is not a valid regular expression.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
