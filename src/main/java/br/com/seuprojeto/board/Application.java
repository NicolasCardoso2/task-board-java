package br.com.seuprojeto.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da API REST (Spring Boot).
 *
 * Para rodar a API:  mvn spring-boot:run
 * Para rodar o console: mvn exec:java -Dexec.mainClass="br.com.seuprojeto.board.Main"
 *
 * Endpoints disponíveis em http://localhost:8080/tasks
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
