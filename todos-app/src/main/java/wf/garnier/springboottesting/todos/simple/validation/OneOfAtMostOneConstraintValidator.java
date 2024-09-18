package wf.garnier.springboottesting.todos.simple.validation;

import java.util.List;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import org.springframework.util.ReflectionUtils;

public class OneOfAtMostOneConstraintValidator implements ConstraintValidator<OneOf.AtMostOne, Object> {

	private String constraintMessage;

	@Override
	public void initialize(OneOf.AtMostOne constraintAnnotation) {
		constraintMessage = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object object, ConstraintValidatorContext context) {
		if (object == null) {
			// Unsure whether you can validate a `null` object...
			return true;
		}

		// @formatter:off
		List<AtMostOneOfField> annotatedFields = Stream.of(object.getClass().getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(OneOf.class))
				.peek(f -> f.setAccessible(true))
				.map(f -> {
					Object fieldValue = ReflectionUtils.getField(f, object);
					return new AtMostOneOfField(f.getName(), fieldValue != null);
				}).toList();

		String[] nonNullAnnotatedFieldNames = annotatedFields.stream()
				.filter(AtMostOneOfField::hasValue)
				.map(AtMostOneOfField::name)
				.sorted()
				.toArray(String[]::new);

		String[] allAnnotatedFieldNames = annotatedFields.stream()
				.map(AtMostOneOfField::name)
				.sorted()
				.toArray(String[]::new);

		if (annotatedFields.stream().filter(AtMostOneOfField::hasValue).count() <= 1) {
			// At most one of the annotated fields have a value
			return true;
		}

		// The failing "property" will be either the first non-null field
		// OR the first annotated field in case all are null
		var reportedFailingProperty = annotatedFields.stream()
				.filter(AtMostOneOfField::hasValue)
				.map(AtMostOneOfField::name)
				.sorted()
				.findFirst()
				.orElse(allAnnotatedFieldNames[0]);

		context.disableDefaultConstraintViolation(); // wokeignore:rule=disable

		context.unwrap(HibernateConstraintValidatorContext.class)
				.addMessageParameter("annotatedOneOfFields", allAnnotatedFieldNames)
				.addMessageParameter("nonNullAtMostOneOfFields", nonNullAnnotatedFieldNames)
				.addMessageParameter("nonNullAtMostOneOfFieldsCount", nonNullAnnotatedFieldNames.length);

		context.buildConstraintViolationWithTemplate(constraintMessage)
				.addPropertyNode(reportedFailingProperty)
				.addConstraintViolation();
		// @formatter:on
		return false;
	}

	record AtMostOneOfField(String name, boolean hasValue) {
	}

}
