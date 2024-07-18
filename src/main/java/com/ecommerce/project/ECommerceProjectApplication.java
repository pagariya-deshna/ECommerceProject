package com.ecommerce.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@OpenAPIDefinition(info = @Info(title = "ECommerceProject", version = "2.0", description = "Ecommerse project system"))
@SecurityScheme(name = "eCommerceProject", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication

public class ECommerceProjectApplication {


    public static void main(String[] args) {
    	SpringApplication.run(ECommerceProjectApplication.class, args);  
}

}
