package model;

import java.util.UUID;

public class Modalidade {
    private UUID id;
    private String nome;
    private String sigla;
    private String descricao;

    // construtores
    public Modalidade(){
        this.id = null;
        this.nome = this.sigla = this.descricao = null;
    }

    public Modalidade(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Modalidade(String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    //Gets
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSigla() {
        return sigla;
    }

    //Sets
    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}