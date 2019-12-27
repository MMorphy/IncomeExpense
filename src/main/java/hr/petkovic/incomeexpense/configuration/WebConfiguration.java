package hr.petkovic.incomeexpense.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
//		registry.addViewController("/login");
//		registry.addViewController("/logout").setViewName("index");
//		registry.addViewController("/forbidden").setViewName("forbidden");
	}
}
