package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.DadosAutor;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @ManyToMany(mappedBy = "autores")//, cascade = CascadeType.ALL)
    private Set<Livro> livros = new HashSet<>();

    public Autor() {
    }

    public Autor(String nome, Integer anoNascimento, Integer anoFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    // ✅ Construtor atualizado com nomes corretos do record
    public Autor(DadosAutor dados) {
        this.nome = dados.name();
        this.anoNascimento = dados.birthYear();
        this.anoFalecimento = dados.deathYear();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        String nomesLivros = livros.stream()
                .map(Livro::getTitulo)
                .toList()
                .toString();

        return "Autor: " + nome + "\n" +
                "Ano de nascimento: " + anoNascimento + "\n" +
                "Ano de falecimento: " + (anoFalecimento != null ? anoFalecimento : "vivo") + "\n" +
                "Livros: " + nomesLivros;
    }
}
