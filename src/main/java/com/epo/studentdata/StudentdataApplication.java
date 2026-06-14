package com.epo.studentdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de StudentData
 *
 * @SpringBootApplication active :
 * - @ComponentScan : détection automatique des beans
 * - @EnableAutoConfiguration : configuration automatique
 * - @Configuration : classe de configuration Spring
 */
@SpringBootApplication
public class StudentdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				StudentdataApplication.class, args
		);
	}
}