package br.com.alura.screensound.principal;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.Musica;
import br.com.alura.screensound.model.TipoArtista;
import br.com.alura.screensound.repository.ArtistaRepository;
import br.com.alura.screensound.service.ConsultaChatGPT;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final ArtistaRepository repositorio;
    private Scanner in;

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
        in = new Scanner(System.in);
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao =in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Pesquisar dados de qual artista? ");
        var nome = in.nextLine();
        var resposta = ConsultaChatGPT.obterInformacao(nome);
        System.out.println(resposta.trim());
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar músicas de qual artista? ");
        var nome = in.nextLine();
        List<Musica> musicas = repositorio.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastra música de qual artista: ");
        var nomeArtista = in.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);//derived querys
        if(artista.isPresent()){
            System.out.println("Qual o nome da musica que deseja cadastrar: ");
            var nomeMusica = in.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else{
            System.out.println("Artista não encontrado");
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("S")){
            System.out.println("Informe o nome do artista: ");
            var nome = in.nextLine();

            System.out.println("Informe o tipo do artista: (solo,dupla ou banda)");
            var tipo = in.nextLine();

            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome,tipoArtista);

            repositorio.save(artista);

            System.out.println("Deseja cadastrar mais artista? (S/N)");
            cadastrarNovo = in.nextLine();
        }

    }
}
