package com.alura.literalura.repository;

import com.alura.literalura.model.Lenguaje;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepositorio extends JpaRepository <Libro, Long>{
    @Query("SELECT l FROM Libro l")
    List<Libro> obtenerListaDeLibros();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> obtenerLibrosEnDeterminadoIdioma(@Param("idioma") Lenguaje idioma);
}
