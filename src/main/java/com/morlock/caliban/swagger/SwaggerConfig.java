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
				//.paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("com.morlock.caliban")).paths(PathSelectors.any()).build()
				.useDefaultResponseMessages(true)
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Caliban-API", // NAME
				"Detector del Gen Mutante", // DESCRIPTION
				"V. 1.0", // VERSION
				"TERMS OF SERVICE URL", // TERMS OF SERVICE URL
				new Contact("Luis G. Pino", "*cambiar*", "lugepime3@gmail.com"), "Test-Api", // LISENSE
				"*cambiar*" // TERMS OF LICENSE URL
		);
	}

}
