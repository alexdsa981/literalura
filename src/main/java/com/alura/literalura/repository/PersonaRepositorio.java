package com.alura.literalura.repository;

import com.alura.literalura.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
    Persona findByNombre(String nombreAutor);


    @Query("SELECT p FROM Persona p")
    List<Persona> obtenerListaDeAutores();
    @Query("SELECT DISTINCT p FROM Persona p JOIN FETCH p.libros")
    List<Persona> obtenerAutoresConLibros();
    @Query("SELECT p FROM Persona p WHERE :year BETWEEN p.fechaNacimiento AND p.fechaFallecimiento")
    List<Persona> obtenerAutoresEnDeterminadoYear(@Param("year") Integer year);

}
