package br.com.alura.screensound.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {
    @Column(unique = true)
    private String nome;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Aqui digo para gerar um valor autoincremetal
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoArtista tipo;
    //Precisamos colocar o cascade para de fato fazermos a relacao no banco de dados, sem ele não conseguimos salvar as musicas
    @OneToMany(mappedBy = "artista",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Musica> musicas = new ArrayList<>();

    public Artista(String nome, TipoArtista tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }
    public Artista() {}
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtista tipo) {
        this.tipo = tipo;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    @Override
    public String toString() {
        return "Artista= " + nome + '\'' +
                ", tipo=" + tipo +
                ", musicas=" + musicas +
                '}' ;
    }
}
