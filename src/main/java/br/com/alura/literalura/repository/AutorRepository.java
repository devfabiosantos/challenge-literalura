package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCase(String name);

    // Busca autores vivos em um determinado ano
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento > :ano)")
    List<Autor> findByAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(@Param("ano") Integer ano);

    // Retorna autores com seus livros carregados (LEFT JOIN para incluir autores sem livros)
    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.livros ORDER BY a.id")
    List<Autor> findAllComLivros();

    @Query("""
    SELECT DISTINCT a FROM Autor a
    LEFT JOIN FETCH a.livros
    WHERE a.anoNascimento <= :ano
      AND (a.anoFalecimento IS NULL OR a.anoFalecimento > :ano)
    ORDER BY a.id
""")
    List<Autor> encontrarAutoresVivosNoAno(@Param("ano") Integer ano);

}

