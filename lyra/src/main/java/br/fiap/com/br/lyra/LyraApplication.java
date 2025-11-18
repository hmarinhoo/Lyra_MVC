package br.fiap.com.br.lyra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LyraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyraApplication.class, args);
	}

}
