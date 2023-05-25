package com.hvivox.srealizacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SrealizacaoApplication {

	public static void main(String[] args) {
		//Confi. para mostrar a hora padrão em UTC,
		// o Frontend terá que converter na hora adequada para o cliente
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		SpringApplication.run(SrealizacaoApplication.class, args);
	}

}
