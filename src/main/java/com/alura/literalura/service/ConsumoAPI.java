package com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obtenerDatos(String titulo) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books?search=" + titulo.replace(" ", "%20")))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error de E/S al enviar la solicitud", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Solicitud interrumpida", e);
        }

        if (response == null) {
            System.out.println("No se recibió respuesta del servidor.");
            return null;
        }

        String json = response.body();
        if (json == null || json.isEmpty()) {
            System.out.println("La respuesta JSON está vacía.");
            return null;
        }

        return json;
    }

}
