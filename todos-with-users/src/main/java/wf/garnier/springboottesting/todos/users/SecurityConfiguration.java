package wf.garnier.springboottesting.todos.users;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				User.withUsername("alice").password("{noop}password").roles("user", "admin").build(),
				User.withUsername("bob").password("{noop}password").roles("user").build());
	}

	@Bean
	public HttpSessionListener sessionListener() {
		return new HttpSessionListener() {
			@Override
			public void sessionCreated(HttpSessionEvent se) {
				System.out.println("➡️ SESSION CREATED");
			}

			@Override
			public void sessionDestroyed(HttpSessionEvent se) {
				System.out.println("➡️ SESSION DESTROYED");
			}
		};
	}

}
