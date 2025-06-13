package br.com.alura.literalura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class ConsumoApi {

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public String obterDados(String titulo) {
        try {
            String tituloFormatado = URLEncoder.encode(titulo, StandardCharsets.UTF_8.toString());
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(URL_BASE + tituloFormatado, String.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Erro ao codificar o título da busca", e);
        }
    }
}


