package model;

import java.util.Random;

public class Modalidade {
    private int id;
    private String nome;
    private String sigla;
    private String descricao;

    // construtores
    public Modalidade(){
        this.id = 0;
        this.nome = this.sigla = this.descricao = null;
    }

    public Modalidade(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.sigla = null;
        this.descricao = null;
    }

    public Modalidade(String nome) {
        Random random = new Random();
        this.id = random.nextInt();
        this.nome = nome;
    }

    //Gets
    public int getId() {
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
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}