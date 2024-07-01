package com.alura.literalura.model;

public enum Lenguaje {
    EN("en"), //INGLES
    ES("es"), //ESPAÑOL
    PT("pt"), //PORTUGUES
    FR("fr");  //FRANCES
    private String lenguaje;

    Lenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public static Lenguaje fromString(String text) {
        for (Lenguaje leng : Lenguaje.values()) {
            if (leng.lenguaje.equalsIgnoreCase(text)) {
                return leng;
            }
        }
        throw new IllegalArgumentException("Ningun lenguaje encontrado: " + text);
    }

}