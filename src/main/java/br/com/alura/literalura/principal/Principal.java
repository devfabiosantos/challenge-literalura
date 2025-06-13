package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConversorDados;
import br.com.alura.literalura.dto.DadosLivro;
import br.com.alura.literalura.service.LivroService;
import org.springframework.stereotype.Component;
import br.com.alura.literalura.dto.DadosRespostaApi;


import java.util.Scanner;

@Component
public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final LivroRepository repositorio;
    private final ConsumoApi consumoApi;
    private final ConversorDados conversor;
    private LivroService livroService;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository repositorio, AutorRepository autorRepository,
                     ConsumoApi consumoApi, ConversorDados conversor, LivroService livroService) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
        this.livroService = livroService;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    Escolha o número da sua opção:
                    1- buscar livro pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    0 - sair
                    """);
            opcao = Integer.parseInt(leitura.nextLine());

            switch (opcao) {
                case 1 -> buscarLivroPeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosAno();
                case 5 -> listarLivrosIdioma();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        DadosLivro dados = getDadosLivro();
        if (dados == null) return;

        Livro livro = livroService.salvarLivro(dados);

        System.out.println("----- LIVRO -----");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutores().stream().findFirst().map(a -> a.getNome()).orElse("Autor desconhecido"));
        System.out.println("Idioma: " + livro.getIdioma());
        System.out.println("Número de downloads: " + livro.getDownloadCount());
        System.out.println("------------------");
    }


    private DadosLivro getDadosLivro() {
        System.out.println("Insira o nome do livro que você deseja procurar:");
        var nomeLivro = leitura.nextLine();

        String json = consumoApi.obterDados(nomeLivro);  // ← agora chama com o título apenas
        DadosRespostaApi resposta = conversor.converter(json, DadosRespostaApi.class);

        if (resposta.results().isEmpty()) {
            System.out.println("Nenhum livro encontrado com esse título.");
            return null;
        }

        return resposta.results().get(0); // pega o primeiro resultado
    }


    //    private void listarLivrosRegistrados() {
//        var livros = repositorio.findAll();
//        livros.forEach(System.out::println);
//    }
    private void listarLivrosRegistrados() {
        var livros = livroService.listarTodos();
        livros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosAno() {
        System.out.println("Digite o ano para verificar quais autores estavam vivos:");
        int ano = Integer.parseInt(leitura.nextLine());

        var autores = autorRepository.encontrarAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo nesse ano.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosIdioma() {
        System.out.println("Digite o idioma (ex: en, pt, es): ");
        var idioma = leitura.nextLine();
        var livros = repositorio.findByIdiomaIgnoreCase(idioma);
        livros.forEach(System.out::println);
//        System.out.println("Digite o idioma (ex: en, pt, es):");
//        String idioma = leitura.nextLine().trim();
//
//        var livros = repositorio.findByIdiomaIgnoreCase(idioma);
//        if (livros.isEmpty()) {
//            System.out.println("Nenhum livro encontrado com o idioma: " + idioma);
//        } else {
//            System.out.println("Livros no idioma \"" + idioma + "\":");
//            livros.forEach(System.out::println);
//        }
    }
}
