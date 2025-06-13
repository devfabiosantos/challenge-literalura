package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)  // <-- esta linha é importante!
public record DadosLivro(
        @JsonProperty("title") String title,
        @JsonProperty("authors") List<DadosAutor> authors,
        @JsonProperty("download_count") Integer downloadCount,
        @JsonProperty("languages") List<String> languages
) {}
