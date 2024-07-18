//package com.ecommerce.project.security;
//
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//
//@Configuration
//public class SwaggerConfig {
//	@Bean
//	public OpenAPI customOpenAPI() {
//		
//		return new OpenAPI()
//				.info(new io.swagger.v3.oas.models.info.Info
//						().title("JavaInUse Authentication Service"))				
//				.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("JavaInUseSecurityScheme"))
//				.components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new io.swagger.v3.oas.models.security.SecurityScheme()
//						.name("eCommerceProject").scheme("bearer").bearerFormat("JWT")));
//		
//	}
//
//
//}
