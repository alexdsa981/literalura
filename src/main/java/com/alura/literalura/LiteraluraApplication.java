package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.LibroRepositorio;
import com.alura.literalura.repository.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
	@Autowired
	LibroRepositorio libroRepositorio;
	@Autowired
	PersonaRepositorio personaRepositorio;
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepositorio, personaRepositorio);
		principal.mostrarMenu();
	}
}
