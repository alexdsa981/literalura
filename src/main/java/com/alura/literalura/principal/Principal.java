package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.LibroRepositorio;
import com.alura.literalura.repository.PersonaRepositorio;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Scanner;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    LibroRepositorio libroRepositorio;
    PersonaRepositorio personaRepositorio;
    ConsumoAPI api = new ConsumoAPI();
    ConvierteDatos conversor = new ConvierteDatos();
    int flag = 99;

    public Principal(LibroRepositorio libroRepositorio, PersonaRepositorio personaRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.personaRepositorio = personaRepositorio;
    }

    public void mostrarMenu() {
        while (flag != 0) {
            String menu = """
                    Elija la opción a través de su número:
                    1- buscar libro por titulo
                    2- listar libros registrados
                    3- listar autores registrados
                    4- listar autores vivos en un determinado año
                    5- listar libros por idioma
                    0- salir
                    """;
            System.out.println(menu);
            flag = teclado.nextInt();
            teclado.nextLine();
            switch (flag) {
                case 0:
                    System.out.println("Finalizando programa.");
                    break;
                case 1:
                    obtenerResultado();
                    break;
                case 2:
                    obtenerListaDeLibrosRegistradosEnBD();
                    break;
                case 3:
                    obtenerListaDeAutoresRegistradosEnBD();
                    break;
                case 4:
                    obtenerListaDeAutoresVivosEnDeterminadoYear();
                    break;
                case 5:
                    obtenerListaDeLibrosPorIdioma();
                    break;
                default:
                    System.out.println("ERROR: Seleccione una opción correcta!!");
                    break;
            }
        }
    }

    /////////////////////////////////////////////////////
    private void obtenerResultado() {
        System.out.println("Ingresa el titulo del libro que deseas buscar: ");
        String tituloEntrada = teclado.nextLine();
        String json = api.obtenerDatos(tituloEntrada);
        DatosResultado resultado = conversor.obtenerDatos(json, DatosResultado.class);
        if (!resultado.results().isEmpty()) {
            System.out.println("El primer resultado de la busqueda es:");
            DatosLibro primerResultado = resultado.results().get(0);
            Persona autor = new Persona(primerResultado.autores().get(0));
            Persona autorExistente = comprobarExistenciaAutor(autor.getNombre());
            Libro libro;
            if (autorExistente == null) {
                libro = new Libro(primerResultado, autor);
            } else {
                libro = new Libro(primerResultado, autorExistente);
            }

            System.out.println(libro);
            guardarPersonaEnBD(autor);
            guardarLibroEnBD(libro);

        } else {
            System.out.println("No se ha encontrado un resultado");
        }

    }

    /////////////////////////////////////////////////////
    public void guardarLibroEnBD(Libro libro) {
        try {
            libroRepositorio.save(libro);
            System.out.println("Libro guardado exitosamente");
        } catch (DataIntegrityViolationException e) {
            System.out.println("AVISO: Libro ya guardado anteriormente!");
        }
    }

    public void guardarPersonaEnBD(Persona autor) {
        try {
            personaRepositorio.save(autor);
            System.out.println("Autor guardado exitosamente");
        } catch (DataIntegrityViolationException e) {
            System.out.println("AVISO: Autor ya guardado anteriormente!");
        }
    }

    public Persona comprobarExistenciaAutor(String nombreAutor) {
        Persona autor = personaRepositorio.findByNombre(nombreAutor);
        return autor;
    }

    /////////////////////////////////////////////////////
    private void obtenerListaDeLibrosRegistradosEnBD() {
        List<Libro> registroDeLibros = libroRepositorio.obtenerListaDeLibros();
        registroDeLibros.forEach(System.out::println);
    }

    /////////////////////////////////////////////////////
    private void obtenerListaDeAutoresRegistradosEnBD() {
        List<Persona> registroDePersonas = personaRepositorio.obtenerAutoresConLibros();
        registroDePersonas.forEach(System.out::println);
    }

    /////////////////////////////////////////////////////
    public void obtenerListaDeAutoresVivosEnDeterminadoYear() {
        System.out.println("Indique el año que desea buscar: ");
        int year = teclado.nextInt();
        teclado.nextLine();
        List<Persona> listaAutores = personaRepositorio.obtenerAutoresEnDeterminadoYear(year);

        if (listaAutores.isEmpty()) {
            System.out.println("AVISO: Aún no se han registrado autores vivos en ese año -> " + year);
        } else {
            listaAutores.forEach(System.out::println);
        }

    }

    /////////////////////////////////////////////////////
    public void obtenerListaDeLibrosPorIdioma() {
        System.out.println("Indique el idioma de los libros que desea buscar");
        System.out.println("""
                  en - INGLES
                  es - ESPAÑOL
                  pt - PORTUGUES
                  fr - FRANCES
                """);
        String entrada = teclado.nextLine();
        Lenguaje idioma;
        switch (entrada.toUpperCase()) {
            case "EN":
                idioma = Lenguaje.EN;
                break;
            case "ES":
                idioma = Lenguaje.ES;
                break;
            case "PT":
                idioma = Lenguaje.PT;
                break;
            case "FR":
                idioma = Lenguaje.FR;
                break;
            default:
                idioma = null;
        }
        if (idioma == null) {
            System.out.println("ERROR: Seleccione una opción valida!!");
        } else {
            List<Libro> listaLibros = libroRepositorio.obtenerLibrosEnDeterminadoIdioma(idioma);
            if (listaLibros.isEmpty()) {
                System.out.println("AVISO: Aún no se han registrado libros en ese idioma -> " + idioma.name());
            } else {
                listaLibros.forEach(System.out::println);
            }
        }

    }

}
