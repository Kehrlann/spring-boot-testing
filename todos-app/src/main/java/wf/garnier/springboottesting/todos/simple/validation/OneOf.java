package wf.garnier.springboottesting.todos.simple.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Ensure that [exactly|at least|at most] one of the annotated fields is not null.
 *
 * Currently, only {@link Exactly} is implemented.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneOf {

	/**
	 * Ensure that exactly one of the fields annotated with {@link OneOf} is not null, not
	 * more, not less.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 *	&#64;OneOf.Exactly
	 *	class Foo {
	 *		&#64;OneOf
	 *		String bar;
	 *
	 *		&#64;OneOf
	 *		String baz;
	 *
	 *		public Foo(String bar, String baz) {
	 *			this.bar = bar;
	 *			this.baz = baz;
	 *		}
	 *	}
	 *
	 *	// Valid
	 *	Foo("bar", null);
	 *	Foo("baz", null);
	 *
	 *	// Not valid
	 *	Foo(null, null);
	 *	Foo("bar", "baz");
	 *	</pre>
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = OneOfExactlyConstraintValidator.class)
	@interface Exactly {

		String message() default "Exactly one of the following properties should be set: {annotatedOneOfFields}. "
                                 + "Found {nonNullOneOfFieldsCount} properties set: {nonNullOneOfFields}.";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

	}

	/**
	 * Ensure that at most one of the fields annotated with {@link OneOf} is not null.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 *	&#64;OneOf.AtMost
	 *	class Foo {
	 *		&#64;OneOf
	 *		String bar;
	 *
	 *		&#64;OneOf
	 *		String baz;
	 *
	 *		public Foo(String bar, String baz) {
	 *			this.bar = bar;
	 *			this.baz = baz;
	 *		}
	 *	}
	 *
	 *	// Valid
	 *	Foo("bar", null);
	 *	Foo(null, "baz");
	 *	Foo(null, null);
	 *
	 *	// Not valid
	 *	Foo("bar", "baz");
	 *	</pre>
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = OneOfAtMostOneConstraintValidator.class)
	@interface AtMostOne {

		String message() default "At most one of the following properties should be set: {annotatedOneOfFields}. "
                                 + "Found {nonNullAtMostOneOfFieldsCount} properties set: {nonNullAtMostOneOfFields}.";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

	}

}
