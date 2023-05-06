package model;

import java.util.Date;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String sobrenome;
    private String email;
    private byte[] hashedPassword;
    private String descricao;
    private Date nascimento;
    private int idade;
    private String foto;

    // construtores
    public Usuario(){
        this.id = null;
        this.nome = this.sobrenome = this.email = this.descricao = this.foto = null;
        this.idade = 0;
        this.hashedPassword = null;
        this.nascimento = null;
    }

    public Usuario(UUID id, String nome, String email, byte[] hashedPassword) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public Usuario(String nome, String email, byte[] hashedPassword) {
        this.nome = nome;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.id = UUID.randomUUID();
    }

    //Gets
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public int getIdade() {
        return idade;
    }

    public String getFoto() {
        return foto;
    }

    //Sets
    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}