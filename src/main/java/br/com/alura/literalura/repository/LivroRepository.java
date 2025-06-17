package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Retorna todos os livros já com os autores carregados (evita LazyInitializationException)
    @EntityGraph(attributePaths = "autores")
    List<Livro> findAll();

    // Retorna livros filtrando por idioma, com autores carregados
    @EntityGraph(attributePaths = "autores")
    List<Livro> findByIdiomaIgnoreCase(String idioma);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores WHERE LOWER(l.idioma) = LOWER(:idioma)")
    List<Livro> buscarLivrosPorIdioma(@Param("idioma") String idioma);


    // Alternativa com JPQL (fetch join explícito) — mesma função que o @EntityGraph acima
    @Query("SELECT l FROM Livro l JOIN FETCH l.autores")
    List<Livro> findAllComAutores();
}
