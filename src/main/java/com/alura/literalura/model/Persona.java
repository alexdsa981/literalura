package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Persona() {
    }

    public Persona(DatosAutor datosAutor) {
        this.nombre = cambiarOrden(datosAutor.nombre());
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
        this.libros = libros;
    }

    public static String cambiarOrden(String nombre) {
        String[] partes = nombre.split(",", 2);
        String parte1 = partes[0].trim();
        String parte2 = partes.length > 1 ? partes[1].trim() : "";
        return parte2 + " " + parte1;
    }

    @Override
    public String toString() {
        String apertura = "-------------------------\n" +
                "NOMBRE AUTOR:" + nombre + "\n" +
                "AÑO DE NACIMIENTO:" + fechaNacimiento + "\n" +
                "AÑO DE FALLECIMIENTO:" + fechaFallecimiento + "\n" +
                "LIBROS ESCRITOS:";
        List<String> listaTitulos = new ArrayList<>();
        for (Libro libro : getLibros()) {
            String titulo= libro.getTitulo();
            listaTitulos.add(titulo);
        }


        String cierre = "\n-------------------------\n";
        return apertura +listaTitulos+ cierre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
