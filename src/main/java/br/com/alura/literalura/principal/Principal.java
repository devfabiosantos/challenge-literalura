package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;
import br.com.alura.literalura.dto.DadosLivro;
import br.com.alura.literalura.dto.DadosResultado;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final LivroRepository repositorio;
    private final AutorRepository autorRepository;

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        while (true) {
            System.out.println("""
        ------------
        Escolha o número da sua opção:
        1- buscar livro pelo título
        2- listar livros registrados
        3- listar autores registrados
        4- listar autores vivos em um determinado ano
        5- listar livros em um determinado idioma
        0 - sair
        """);

            int opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> {
                    System.out.println("Encerrando aplicação.");
                    System.exit(0); // Encerra a aplicação imediatamente
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro: ");
        var titulo = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO + titulo.replace(" ", "+"));
        var dados = conversor.obterDados(json, DadosResultado.class);

        if (dados.results().isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }

        DadosLivro dadosLivro = dados.results().get(0);
        Livro livro = new Livro(dadosLivro);

        // Persistência
        repositorio.save(livro);
        autorRepository.saveAll(livro.getAutores());

        System.out.println("Livro salvo com sucesso: " + livro.getTitulo());
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = repositorio.findAllComAutores(); // fetch join

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        for (Livro livro : livros) {
            System.out.println("----- LIVRO -----");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor(es): " + livro.getAutor());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Número de downloads: " + livro.getNumeroDownloads());
            System.out.println();
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAllComLivros();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
            return;
        }

        autores.sort(Comparator.comparing(Autor::getId)); // ordenar por ID

        for (Autor autor : autores) {
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
            System.out.println("Ano de falecimento: " + autor.getAnoFalecimento());

            var livros = autor.getLivros().stream()
                    .map(Livro::getTitulo)
                    .toList();

            System.out.println("Livros: " + livros + "\n");
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.print("Insira o ano que deseja pesquisar: ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autores = autorRepository.encontrarAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado para o ano informado.");
            return;
        }

        for (Autor autor : autores) {
            System.out.println("\nAutor: " + autor.getNome());
            System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
            System.out.println("Ano de falecimento: " +
                    (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Ainda vivo"));

            var livros = autor.getLivros().stream()
                    .map(Livro::getTitulo)
                    .toList();

            System.out.println("Livros: " + livros);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
        Insira o idioma para realizar a busca:
        es- espanhol
        en- inglês
        fr- francês
        pt- português
        """);
        //System.out.print("Idioma: ");
        String idioma = leitura.nextLine();

        List<Livro> livros = repositorio.findByIdiomaIgnoreCase(idioma);

        if (livros.isEmpty()) {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
            return;
        }

        for (Livro livro : livros) {
            System.out.println("----- LIVRO -----");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor(es): " + livro.getAutor());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Número de downloads: " + livro.getNumeroDownloads());
            System.out.println();
        }
    }
}
