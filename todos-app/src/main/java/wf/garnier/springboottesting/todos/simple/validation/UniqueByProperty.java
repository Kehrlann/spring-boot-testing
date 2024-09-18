package wf.garnier.springboottesting.todos.simple.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that every object in the provided {@link Collection} is unique, using the
 * given {@code property} to compare them. Optionally, if the {@code property} is not
 * public, users may specify a {@code getterMethod} producer function for obtaining the
 * property.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueByPropertyConstraintValidator.class })
public @interface UniqueByProperty {

	/**
	 * The property used to compare elements in the collection and make sure they are
	 * unique. Ignore when {@link #getterMethod()} is set.
	 * @return the name of the property.
	 */
	String property();

	/**
	 * The getter method used to obtain the values used to compare elements in the
	 * collection. If defined, it will override {@link #property()} and be used instead.
	 * @return the name of the getter method.
	 */
	String getterMethod() default "";

	/**
	 * Allow null values in the collection.
	 * @return (dis)allow null values.
	 */
	boolean allowNullValues() default false;

	String message() default "List items should have distinct \"{property}\" properties. Found duplicates: [{duplicates}].";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
