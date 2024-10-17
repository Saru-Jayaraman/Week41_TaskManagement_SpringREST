package se.lexicon.week41_taskmanagement_springrest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "TODO API - Task Management", version = "0.1", description = "Management of Tasks"))
public class SwaggerConfig {

}
