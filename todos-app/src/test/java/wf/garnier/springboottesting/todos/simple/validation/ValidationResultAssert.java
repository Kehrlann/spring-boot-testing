package wf.garnier.springboottesting.todos.simple.validation;

import java.util.Collection;

import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.CollectionAssert;

public class ValidationResultAssert<T> extends CollectionAssert<ConstraintViolation<T>> {

    private ValidationResultAssert(Collection<? extends ConstraintViolation<T>> actual) {
        super(actual);
    }

    /**
     * Package-private, this is intended to be used through {@link Assertions}.
     * @param validationResult -
     * @param <T> -
     * @return -
     */
    static <T> ValidationResultAssert<T> assertThat(Collection<ConstraintViolation<T>> validationResult) {
        return new ValidationResultAssert<>(validationResult);
    }

    public static <T> org.assertj.core.api.ObjectAssert<T> coreAssert(T thing) {
        return org.assertj.core.api.Assertions.assertThat(thing);
    }

    public ValidationResultAssert<T> hasSize(int size) {
        isNotNull();
        super.hasSize(size);
        return this;
    }

    public ValidationResultAssert<T> isValid() {
        return hasSize(0);
    }

    public ValidationResultAssert<T> hasViolationForProperty(String propertyPath, String message) {
        isNotNull();
        anySatisfy(constraintViolation -> {
            coreAssert(constraintViolation.getPropertyPath().toString()).isEqualTo(propertyPath);
            coreAssert(constraintViolation.getMessage()).isEqualTo(message);
        });
        return this;
    }

    public ValidationResultAssert<T> hasViolationForProperty(String propertyPath) {
        isNotNull();
        anySatisfy(constraintViolation -> {
            coreAssert(constraintViolation.getPropertyPath().toString()).isEqualTo(propertyPath);
        });
        return this;
    }

    public ValidationResultAssert<T> hasViolationForProperties(String... propertyPath) {
        for (String property : propertyPath) {
            hasViolationForProperty(property);
        }
        return this;
    }

    public ValidationResultAssert<T> hasViolationWithMessage(String message) {
        isNotNull();
        anySatisfy(constraintViolation -> coreAssert(constraintViolation.getMessage()).isEqualTo(message));
        return this;
    }

    @Override
    public ValidationResultAssert<T> withFailMessage(String newErrorMessage, Object... args) {
        return (ValidationResultAssert<T>) super.withFailMessage(newErrorMessage, args);
    }

}
