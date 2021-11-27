package com.nasa.probesystem.configuration;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
// @EnableSwagger2
public class SwaggerConfig {
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.nada.probesystem.web.controllers"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(projectApiInformation());
  }

  private ApiInfo projectApiInformation() {
    return new ApiInfoBuilder()
        .title("ProbeSystem")
        // ADD static version handling here
        .version("0.0.1")
        .description("System to handle probe navigation on planets")
        .contact(new Contact("Marcos Vinicius Junqueira", "", "mviniciusjunqueira@gmail.com"))
        .build();
  }
}
