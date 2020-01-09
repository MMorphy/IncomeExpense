package hr.petkovic.incomeexpense.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new PasswordEnconderTest();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(getPasswordEncoder())
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, roles.name as authority "
						+ "from users join user_roles on users.id=user_roles.user_id "
						+ "join roles on roles.id=user_roles.role_id where username=?");
	}

	public class PasswordEnconderTest implements PasswordEncoder {
		@Override
		public String encode(CharSequence charSequence) {
			return charSequence.toString();
		}

		@Override
		public boolean matches(CharSequence charSequence, String s) {
			return charSequence.toString().equals(s);
		}
	}
}
