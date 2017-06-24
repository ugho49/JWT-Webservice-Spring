package fr.nantes.uste.demowebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoWebserviceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebserviceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoWebserviceApplication.class);
	}
}
