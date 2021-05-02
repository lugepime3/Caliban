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
				"Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men.\r\n" + 
				"Esta es la version de una API que detecta si un humano es mutante basándose en su secuencia de ADN.\r\n" + 
				"Recibe como parametro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN. \r\n" + 
				"Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN.\r\n" + 
				"<strong>Se sabra si un humano es mutante, si la API encuentra más de una secuencia de cuatro letras iguales​, de forma oblicua, horizontal o vertical.</strong>", // DESCRIPTION
				"V. 1.0", // VERSION
				"TERMS OF SERVICE URL", // TERMS OF SERVICE URL
				new Contact("Luis Gerardo Pino Mendoza", "https://github.com/lugepime3/Caliban", "lugepime3@gmail.com"), "", // LISENSE
				"" // TERMS OF LICENSE URL
		);
	}

}
