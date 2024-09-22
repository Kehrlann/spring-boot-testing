package wf.garnier.springboottesting.todos.simple.reference;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import wf.garnier.springboottesting.todos.simple.TodoProperties;
import wf.garnier.springboottesting.todos.simple.TodoPropertiesConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static wf.garnier.springboottesting.todos.simple.reference.Assertions.assertThat;
import static wf.garnier.springboottesting.todos.simple.reference.Assertions.assertThatValidation;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoPropertiesTests {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Nested
	@Order(1)
	class ManualBeanBuilding {

		public void empty() {
			var empty = new TodoProperties(List.of(), null);
			assertThatValidation(validator.validate(empty)).isEmpty();
		}

		public void simple() {
			//@formatter:off
			var complex = new TodoProperties(
					List.of(
							new TodoProperties.UserProfile(
									"internal",
									new TodoProperties.UserProfile.InternalUser(
											"      ",
											null,
											"Daniel",
											"Garnier-Moiroux",
											null
									),
									null
							)
					),
					null);
			//@formatter:on

			// Need the full Spring mechanisms
			assertThatValidation(validator.validate(complex)).hasSize(2)
				.hasViolationForProperty("profiles[0].internalUser.email")
				.hasViolationForProperties("profiles[0].internalUser.password");
		}

		public void complex() {
			//@formatter:off
			var complex = new TodoProperties(
					List.of(
							new TodoProperties.UserProfile(
									"internal",
									new TodoProperties.UserProfile.InternalUser(
											"git@garnier.wf",
											"some-password",
											"Daniel",
											"Garnier-Moiroux",
											null
									),
									null
							),
							new TodoProperties.UserProfile(
									"internal",
									null,
									new TodoProperties.UserProfile.Github("kehrlann")
							)

					),
					null);
			//@formatter:on
			assertThatValidation(validator.validate(complex)).hasViolationForProperty("profiles",
					"List items should have distinct \"name\" properties. Found duplicates: [internal].");
		}

	}

	@Nested
	@Order(2)
	class Jackson {

		ObjectMapper mapper = YAMLMapper.builder()
			.propertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE)
			.configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
			.addModule(new ParameterNamesModule())
			.build();

		public void empty() throws JsonProcessingException {
			var empty = mapper.readValue("""
					profiles: []
					""", TodoProperties.class);
			assertThatValidation(validator.validate(empty)).isEmpty();
		}

		public void simple() throws JsonProcessingException {
			var props = mapper.readValue("""
					profiles:
					- name: internal
					  internal-user:
					    email: git@garnier.wf
					""", TodoProperties.class);
			//@formatter:on
			assertThatValidation(validator.validate(props))
				.hasViolationForProperties("profiles[0].internalUser.firstName", "profiles[0].internalUser.lastName");
		}

		public void complex() throws JsonProcessingException {
			var props = mapper.readValue("""
					profiles:
					- name: internal
					  internal-user:
					    email: git@garnier.wf
					    password: some-password
					    first-name: Daniel
					    last-name: Garnier-Moiroux
					- name: internal
					  github:
					    id: kerhlann
					""", TodoProperties.class);
			//@formatter:on
			assertThatValidation(validator.validate(props)).hasViolationForProperty("profiles",
					"List items should have distinct \"name\" properties. Found duplicates: [internal].");
		}

	}

	@Nested
	@Order(3)
	class IntegrationTest {

		static Instant start;

		@BeforeAll
		static void beforeAll() {
			start = Instant.now();
		}

		@AfterAll
		static void afterAll() {
			System.out.printf("~~~~~~~~~> done in %sms%n", Duration.between(start, Instant.now()).toMillis());
		}

		void validProperties() throws IOException {
			var config = new ByteArrayResource("""
					todo:
					  profiles:
					  - name: xxx
					    internal-user:
					      email: git@garnier.wf
					      password: some-password
					      first-name: Daniel
					      last-name: Garnier-Moiroux
					""".getBytes(StandardCharsets.UTF_8));

			List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load("env-from-inline-test",
					config);
			var env = new StandardEnvironment();
			env.getPropertySources().addFirst(propertySources.get(0));

			var app = new SpringApplicationBuilder(PropertiesLoader.class).web(WebApplicationType.NONE)
				.environment(env);
			assertThatNoException().isThrownBy(app::run);
			// assertThatExceptionOfType(ConfigurationPropertiesBindException.class).isThrownBy(app::run);
		}

		void invalidProperties() throws IOException {
			var config = new ByteArrayResource("""
					todo:
					  profiles:
					  - name: "  "
					    internal-user:
					      email: git@garnier.wf
					""".getBytes(StandardCharsets.UTF_8));

			List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load("env-from-inline-test",
					config);
			var env = new StandardEnvironment();
			env.getPropertySources().addFirst(propertySources.get(0));

			var app = new SpringApplicationBuilder(PropertiesLoader.class).web(WebApplicationType.NONE)
				.environment(env);
			assertThatExceptionOfType(ConfigurationPropertiesBindException.class).isThrownBy(app::run);
		}

		@Configuration
		@EnableConfigurationProperties(TodoProperties.class)
		static class PropertiesLoader {

		}

	}

	@Nested
	@SpringBootTest(classes = IntegrationTest.PropertiesLoader.class)
	@TestPropertySource(properties = """
			todo.profiles[0].name = internal
			todo.profiles[0].internal-user.email: git@garnier.wf
			todo.profiles[0].internal-user.password: some-password
			todo.profiles[0].internal-user.first-name: Daniel
			todo.profiles[0].internal-user.last-name: Garnier-Moiroux
			""")
	@Order(4)
	class SpringBoot {

		@Autowired
		TodoProperties properties;

		static Instant start;

		@BeforeAll
		static void beforeAll() {
			start = Instant.now();
		}

		@AfterAll
		static void afterAll() {
			System.out.printf("~~~~~~~~~> @SpringBootTest done in %sms%n",
					Duration.between(start, Instant.now()).toMillis());
		}

		void loads() {
			assertThat(properties.getProfiles().get(0).internalUser().email()).isEqualTo("git@garnier.wf");
			// This is slower, ~600ms
		}

	}

	@Nested
	@ExtendWith(SpringExtension.class)
	@TestPropertySource(properties = """
			todo.profiles[0].name = internal
			todo.profiles[0].internal-user.email: git@garnier.wf
			todo.profiles[0].internal-user.password: some-password
			todo.profiles[0].internal-user.first-name: Daniel
			todo.profiles[0].internal-user.last-name: Garnier-Moiroux
			""")
	@Order(5)
	class SpringExtensionTests {

		@Autowired
		TodoProperties properties;

		static Instant start;

		@BeforeAll
		static void beforeAll() {
			start = Instant.now();
		}

		@AfterAll
		static void afterAll() {
			System.out.printf("~~~~~~~~~> done in %sms%n", Duration.between(start, Instant.now()).toMillis());
		}

		void loads() {
			assertThat(properties.getProfiles().get(0).internalUser().email()).isEqualTo("git@garnier.wf");
		}

		@Configuration
		@EnableConfigurationProperties(TodoProperties.class)
		static class PropertiesLoader {

		}

	}

	@Nested
	@ExtendWith(OutputCaptureExtension.class)
	@ExtendWith(SpringExtension.class)
	@ContextConfiguration(initializers = SpringExtensionYamlTests.YamlPropsInitializer.class)
	@Order(6)
	class SpringExtensionYamlTests {

		@Autowired
		TodoProperties properties;

		static Instant start;

		@BeforeAll
		static void beforeAll() {
			start = Instant.now();
		}

		@AfterAll
		static void afterAll() {
			System.out.printf("~~~~~~~~~> done in %sms%n", Duration.between(start, Instant.now()).toMillis());
		}

		void loads() {
			assertThat(properties.getProfiles().get(0).internalUser().email()).isEqualTo("git@garnier.wf");
		}

		void printsProfileCount(CapturedOutput output) {
			assertThat(output.getOut()).contains("found 1 profile(s)");
		}

		@Configuration
		@Import(TodoPropertiesConfiguration.class)
		static class PropertiesLoader {

		}

		static class YamlPropsInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

			@Override
			public void initialize(ConfigurableApplicationContext applicationContext) {
				var config = new ByteArrayResource("""
						todo:
						  profiles:
						  - name: xxx
						    internal-user:
						      email: git@garnier.wf
						      password: some-password
						      first-name: Daniel
						      last-name: Garnier-Moiroux
						""".getBytes(StandardCharsets.UTF_8));

				List<PropertySource<?>> propertySources = null;
				try {
					propertySources = new YamlPropertySourceLoader().load("env-from-inline-test", config);
				}
				catch (IOException e) {
				}

				applicationContext.getEnvironment().getPropertySources().addFirst(propertySources.get(0));
			}

		}

	}

}