package wf.garnier.springboottesting.todos.simple.validation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Constraint validator invoked on fields annotated with {@link UniqueByProperty}. Reuses
 * logic of {@link org.hibernate.validator.constraints.UniqueElements} validator.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UniqueByPropertyConstraintValidator implements ConstraintValidator<UniqueByProperty, Collection> {

	private String propertyName;

	private String getterName;

	private boolean allowNullValues;

	@Override
	public void initialize(UniqueByProperty constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		propertyName = constraintAnnotation.property();
		getterName = constraintAnnotation.getterMethod();
		allowNullValues = constraintAnnotation.allowNullValues();
	}

	@Override
	public boolean isValid(Collection collection, ConstraintValidatorContext context) {
		if (CollectionUtils.isEmpty(collection)) {
			return true;
		}
		if (!allowNullValues) {
			ensureNoNullValues(collection);
		}
		collection = (Collection) collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(collection)) {
			return true;
		}
		UniqueElementsValidator uniqueValidator = new UniqueElementsValidator();
		List<Object> keys = StringUtils.hasText(getterName) ? getKeysByGetterMethod(collection)
				: getKeysByProperty(collection);
		return uniqueValidator.isValid(keys, context);
	}

	private List<Object> getKeysByProperty(Collection collection) {
		Object first = getFirstElement(collection);

		if (first == null) {
			return Collections.emptyList();
		}

		final Field targetField = ReflectionUtils.findField(first.getClass(), propertyName);
		if (targetField == null) {
			throw exception("Could not find property \"{0}\" on type {1}", propertyName, first.getClass().getName());
		}

		return (List<Object>) collection.stream().map(o -> {
			try {
				return targetField.get(o);
			}
			catch (IllegalAccessException e) {
				throw exception("Could not access property \"{0}\" on type {1}. Make sure the property is public.",
						propertyName, first.getClass().getName());
			}
		}).collect(Collectors.toList());
	}

	private List<Object> getKeysByGetterMethod(Collection collection) {
		Object first = getFirstElement(collection);

		if (first == null) {
			return Collections.emptyList();
		}

		final Method targetMethod = ReflectionUtils.findMethod(first.getClass(), getterName);
		if (targetMethod == null) {
			throw exception(
					"Could not find getter method \"{0}\" on type {1}. The method should not accept any argument.",
					getterName, first.getClass().getName());
		}

		return (List<Object>) collection.stream().map(o -> {
			try {
				return targetMethod.invoke(o);
			}
			catch (IllegalAccessException e) {
				throw exception("Could not access getter method \"{0}\" on type {1}. Make sure the method is public.",
						getterName, first.getClass().getName());
			}
			catch (InvocationTargetException e) {
				String exceptionMessage = MessageFormat.format("Getter method \"{0}\" on type {1} threw exception: {2}",
						getterName, first.getClass().getName(), e.getCause());
				throw new IllegalArgumentException(exceptionMessage, e.getCause());
			}
		}).collect(Collectors.toList());
	}

	private static Object getFirstElement(Collection collection) {
		return collection.stream().filter(Objects::nonNull).findFirst().orElse(null);
	}

	private void ensureNoNullValues(Collection collection) {
		if (collection.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException("Error checking that collection has distinct \"" + propertyName
					+ "\" properties: collection contained null.");
		}
	}

	private static IllegalArgumentException exception(String errorMessage, Object... params) {
		return new IllegalArgumentException(MessageFormat.format(errorMessage, params));
	}

}
