package wf.garnier.springboottesting.todos.simple;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import wf.garnier.springboottesting.todos.simple.validation.OneOf;
import wf.garnier.springboottesting.todos.simple.validation.RegEx;
import wf.garnier.springboottesting.todos.simple.validation.UniqueByProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 * Example configuration:
 *
 * <pre>
 * todo:
 *   search: \d{1-3}\.\d{1-3}.\d{1-3}.\d{1-3}
 *   profiles:
 *     - name: local-user
 *       internalUser:
 *         email: daniel@example.com
 *         password: this-is-secret
 *         first-name: Daniel
 *         last-name: Garnier-Moiroux
 *      - name: github-user
 *        github:
 *          id: kehrlann
 * </pre>
 */
@Validated
@ConfigurationProperties(prefix = "todo")
public class TodoProperties {

	@Valid
	@UniqueByProperty(property = "name", getterMethod = "name")
	private final List<UserProfile> profiles;

	@RegEx
	private final String search;

	@ConstructorBinding
	@JsonCreator
	public TodoProperties(List<UserProfile> profiles, String search) {
		this.profiles = profiles;
		this.search = search;
	}

	public @Valid List<UserProfile> getProfiles() {
		return profiles;
	}

	public String getSearch() {
		return search;
	}

	@OneOf.Exactly
	public record UserProfile(@NotBlank String name, @Valid @OneOf InternalUser internalUser,
			@Valid @OneOf Github github) {

		public record InternalUser(@NotBlank String email, @NotBlank String password, @NotBlank String firstName,
				@NotBlank String lastName, @Nullable String phoneNumber) {
		}

		public record Github(@NotBlank String id) {
		}
	}

}
