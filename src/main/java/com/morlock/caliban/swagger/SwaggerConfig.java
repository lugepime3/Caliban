package com.morlock.caliban.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.morlock.caliban")).paths(PathSelectors.any()).build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Northwind API", // NAME
				"API-DOCUMENTATION", // DESCRIPTION
				"V. 1.0", // VERSION
				"TERMS OF SERVICE URL", // TERMS OF SERVICE URL
				new Contact("lpino", "lugepime3@gmail.com", "A@A.COM"), "LICENSE", // LISENSE
				"HTTP://WWW.JAVADESDE0.COM" // TERMS OF LICENSE URL
		);
	}

}
