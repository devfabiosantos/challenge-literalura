package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.DadosLivro;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Livro salvarLivro(DadosLivro dadosLivro) {
        Livro livro = new Livro(dadosLivro);
        Set<Autor> autores = new HashSet<>();

        for (var dadosAutor : dadosLivro.authors()) {
            Autor autor = autorRepository.findByNomeIgnoreCase(dadosAutor.name())
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor(dadosAutor);
                        return autorRepository.save(novoAutor);
                    });
            autores.add(autor);
        }

        livro.setAutores(autores);
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }
}
