package wf.garnier.springboottesting.todos.simple.validation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import org.springframework.util.ReflectionUtils;

public class OneOfExactlyConstraintValidator implements ConstraintValidator<OneOf.Exactly, Object> {

	private String constraintMessage;

	@Override
	public void initialize(OneOf.Exactly constraintAnnotation) {
		constraintMessage = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object object, ConstraintValidatorContext context) {
		if (object == null) {
			// Unsure whether you can validate a `null` object...
			return true;
		}

		// @formatter:off
		List<OneOfField> annotatedFields = Stream.of(object.getClass().getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(OneOf.class))
				.peek(f -> f.setAccessible(true))
				.map(f -> {
					Object fieldValue = ReflectionUtils.getField(f, object);
					return new OneOfField(f.getName(), fieldValue != null);
				}).collect(Collectors.toList());

		String[] nonNullAnnotatedFieldNames = annotatedFields.stream()
				.filter(OneOfField::hasValue)
				.map(OneOfField::getName)
				.sorted()
				.toArray(String[]::new);

		String[] allAnnotatedFieldNames = annotatedFields.stream()
				.map(OneOfField::getName)
				.sorted()
				.toArray(String[]::new);

		if (annotatedFields.stream().filter(OneOfField::hasValue).count() == 1) {
			// Exactly one of the annotated field has value
			return true;
		}

		// The failing "property" will be either the first non-null field
		// OR the first annotated field in case all are null
		var reportedFailingProperty = annotatedFields.stream()
				.filter(OneOfField::hasValue)
				.map(OneOfField::getName)
				.sorted()
				.findFirst()
				.orElse(allAnnotatedFieldNames[0]);

		context.disableDefaultConstraintViolation(); // wokeignore:rule=disable

		context.unwrap(HibernateConstraintValidatorContext.class)
				.addMessageParameter("annotatedOneOfFields", allAnnotatedFieldNames)
				.addMessageParameter("nonNullOneOfFields", nonNullAnnotatedFieldNames)
				.addMessageParameter("nonNullOneOfFieldsCount", nonNullAnnotatedFieldNames.length);

		context.buildConstraintViolationWithTemplate(constraintMessage)
				.addPropertyNode(reportedFailingProperty)
				.addConstraintViolation();
		// @formatter:on
		return false;
	}

	static class OneOfField {

		private final String name;

		private final boolean hasValue;

		OneOfField(String name, boolean hasValue) {
			this.name = name;
			this.hasValue = hasValue;
		}

		public String getName() {
			return name;
		}

		public boolean hasValue() {
			return hasValue;
		}

	}

}
