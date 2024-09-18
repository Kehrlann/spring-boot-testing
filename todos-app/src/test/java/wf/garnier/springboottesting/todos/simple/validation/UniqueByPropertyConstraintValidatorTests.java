package wf.garnier.springboottesting.todos.simple.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static wf.garnier.springboottesting.todos.simple.validation.ValidationResultAssert.assertThat;

class UniqueByPropertyConstraintValidatorTests {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Nested
	class ValidateByProperty {

		@Test
		void valid() {
			Container container = new Container(new Entity("one"), new Entity("two"));

			assertThat(validator.validate(container)).hasSize(0);
		}

		@Test
		void nullCollection() {
			Container container = new Container();
			container.setEntityList(null);

			assertThat(validator.validate(container)).hasSize(0);
		}

		@Test
		void emptyCollection() {
			Container container = new Container();

			assertThat(validator.validate(container)).hasSize(0);
		}

		@Test
		void duplicates() {
			Container container = new Container(new Entity("one"), new Entity("two"), new Entity("one"));

			assertThat(validator.validate(container)).hasSize(1)
				.hasViolationForProperty("entityList",
						"List items should have distinct \"publicName\" properties. Found duplicates: [one].");
		}

		@Test
		void missingProperty() {
			class MissingProperty {

				@UniqueByProperty(property = "foo")
				final public List<Entity> entityList = Collections.singletonList(new Entity("one"));

			}
			MissingProperty container = new MissingProperty();

			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingRootCause()
				.isInstanceOf(IllegalArgumentException.class)
				.withMessage("Could not find property \"foo\" on type " + Entity.class.getName());
		}

		@Test
		void privateProperty() {
			class PrivatePropertyContainer {

				@UniqueByProperty(property = "privateName")
				final public List<Entity> entityList = Collections.singletonList(new Entity("one"));

			}
			PrivatePropertyContainer container = new PrivatePropertyContainer();

			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingRootCause()
				.isInstanceOf(IllegalArgumentException.class)
				.withMessage("Could not access property \"privateName\" on type " + Entity.class.getName()
						+ ". Make sure the property is public.");
		}

		class Container {

			@UniqueByProperty(property = "publicName")
			List<Entity> entityList;

			Container(Entity... entities) {
				this.entityList = Arrays.asList(entities);
			}

			public void setEntityList(List<Entity> entityList) {
				this.entityList = entityList;
			}

		}

	}

	@Nested
	class ValidateByMethod {

		@Test
		void valid() {
			Container container = new Container(new Entity("one"), new Entity("two"));

			assertThat(validator.validate(container)).hasSize(0);
		}

		@Test
		void nullCollection() {
			Container container = new Container();
			container.setEntityList(null);

			assertThat(validator.validate(container)).hasSize(0);
		}

		@Test
		void emptyCollection() {
			Container container = new Container();

			assertThat(validator.validate(container)).hasSize(0);
		}

		@Test
		void duplicates() {
			Container container = new Container(new Entity("one"), new Entity("two"), new Entity("one"));

			assertThat(validator.validate(container)).hasSize(1)
				.hasViolationForProperty("entityList",
						"List items should have distinct \"some-property\" properties. Found duplicates: [one].");
		}

		@Test
		void missingProperty() {
			class MissingGetterContainer {

				@UniqueByProperty(property = "some-property", getterMethod = "foo")
				final public List<Entity> entityList = Collections.singletonList(new Entity("one"));

			}
			MissingGetterContainer container = new MissingGetterContainer();

			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingRootCause()
				.isInstanceOf(IllegalArgumentException.class)
				.withMessage("Could not find getter method \"foo\" on type " + Entity.class.getName()
						+ ". The method should not accept any argument.");
		}

		@Test
		void privateProperty() {
			class PrivateGetterContainer {

				@UniqueByProperty(property = "some-property", getterMethod = "privateGetter")
				final public List<Entity> entityList = Collections.singletonList(new Entity("one"));

			}
			PrivateGetterContainer container = new PrivateGetterContainer();

			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingRootCause()
				.isInstanceOf(IllegalArgumentException.class)
				.withMessage("Could not access getter method \"privateGetter\" on type " + Entity.class.getName()
						+ ". Make sure the method is public.");
		}

		@Test
		void wrongSignatureGetter() {
			class WrongSignatureContainer {

				@UniqueByProperty(property = "some-property", getterMethod = "wrongSignature")
				final public List<Entity> entityList = Collections.singletonList(new Entity("one"));

			}
			WrongSignatureContainer container = new WrongSignatureContainer();

			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingRootCause()
				.isInstanceOf(IllegalArgumentException.class)
				.withMessage("Could not find getter method \"wrongSignature\" on type " + Entity.class.getName()
						+ ". The method should not accept any argument.");
		}

		@Test
		void getterThrows() {
			class ThrowingGetterContainer {

				@UniqueByProperty(property = "some-property", getterMethod = "throwingGetter")
				final public List<Entity> entityList = Collections.singletonList(new Entity("one"));

			}
			ThrowingGetterContainer container = new ThrowingGetterContainer();

			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingCause()
				.withMessageContaining("Getter method \"throwingGetter\" on type " + Entity.class.getName()
						+ " threw exception: " + RuntimeException.class.getName())
				.havingRootCause()
				.isInstanceOf(RuntimeException.class)
				.withMessage("throw from the getter");
		}

		class Container {

			@UniqueByProperty(property = "some-property", getterMethod = "getPrivateName")
			List<Entity> entityList;

			Container(Entity... entities) {
				this.entityList = Arrays.asList(entities);
			}

			public void setEntityList(List<Entity> entityList) {
				this.entityList = entityList;
			}

		}

	}

	@Nested
	class NullElements {

		@Test
		void disallowNulls() {
			class NullValidatorContainer {

				@UniqueByProperty(property = "publicName")
				final public List<Entity> entityList = Arrays.asList(new Entity("one"), null);

			}

			NullValidatorContainer container = new NullValidatorContainer();
			Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> validator.validate(container))
				.havingRootCause()
				.isInstanceOf(IllegalArgumentException.class)
				.withMessage(
						"Error checking that collection has distinct \"publicName\" properties: collection contained null.");

		}

		@Test
		void allowNulls() {
			class NullValidatorContainer {

				@UniqueByProperty(property = "publicName", allowNullValues = true)
				final public List<Entity> entityList = Arrays.asList(new Entity("one"), null, new Entity("two"));

			}

			assertThat(validator.validate(new NullValidatorContainer())).isEmpty();
		}

	}

	static class Entity {

		public final String publicName;

		private final String privateName;

		Entity(String name) {
			this.privateName = name;
			this.publicName = name;
		}

		public String getPrivateName() {
			return privateName;
		}

		private String privateGetter() {
			return privateName;
		}

		public String wrongSignature(String someInput) {
			return privateName;
		}

		public String throwingGetter() {
			throw new RuntimeException("throw from the getter");
		}

	}

}
