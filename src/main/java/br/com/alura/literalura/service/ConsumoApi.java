package br.com.alura.literalura.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class ConsumoApi {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public String obterDados(String titulo) {
        try {
            String tituloFormatado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String url = URL_BASE + tituloFormatado;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consumir API", e);
        }
    }
}
