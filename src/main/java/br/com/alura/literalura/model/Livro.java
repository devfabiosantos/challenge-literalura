package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.DadosLivro;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer numeroDownloads;

    @ManyToMany//(cascade = CascadeType.ALL)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    public Livro() {}

    public Livro(DadosLivro dados) {
        this.titulo = dados.title();
        this.idioma = dados.languages().isEmpty() ? "desconhecido" : dados.languages().get(0);
        this.numeroDownloads = dados.downloadCount();
        this.autores = dados.authors().stream()
                .map(Autor::new)
                .collect(Collectors.toSet());
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public Integer getDownloadCount() {  // Alternativo, se necessário
        return numeroDownloads;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    // Retorna nomes dos autores em uma string separada por vírgula
    public String getAutor() {
        return autores.stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDownloads=" + numeroDownloads +
                ", autores=" + getAutor() +
                '}';
    }
}
