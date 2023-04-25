package wiwiel.training.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring Framework Guru")
                        .description("Spring Framework 5: Beginner to Guru")
                        .version("1.0").contact(new Contact().name("Wiwiel").url("https://github.com/Wiwiel/"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}