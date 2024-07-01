package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Persona autor;
    @Enumerated(EnumType.STRING)
    private Lenguaje idioma;
    private Integer numeroDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro, Persona autor) {
        this.titulo = datosLibro.titulo();
        this.autor = autor;
        this.idioma = Lenguaje.fromString(datosLibro.idiomas().get(0).split(",")[0].trim());

        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    @Override
    public String toString() {
        return "-------------------------\n"+
                "TITULO:" + titulo + "\n" +
                "AUTOR:" + autor.getNombre() +"\n"+
                "IDIOMA:" + idioma + "\n"+
                "DESCARGAS:" + numeroDescargas+"\n"+
                "-------------------------\n";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Persona getAutor() {
        return autor;
    }

    public void setAutor(Persona autor) {
        this.autor = autor;
    }

    public Lenguaje getIdioma() {
        return idioma;
    }

    public void setIdioma(Lenguaje idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
