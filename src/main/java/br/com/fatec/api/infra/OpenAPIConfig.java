package br.com.fatec.api.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Fatec Produtos")
                        .version("1.0")
                        .description("API para gerenciamento de alunos, cursos e matriculas da Fatec"));
    }
}
