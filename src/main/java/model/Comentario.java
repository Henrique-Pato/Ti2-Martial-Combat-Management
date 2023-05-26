package model;

import java.util.Date;
import java.time.Instant;
import java.util.Random;

public class Comentario {
    private int id;
    private int postagemID;
    private int usuarioID;
    private Date date;
    private String conteudo;
    private String resposta;

    // construtores
    public Comentario(){
        this.id = this.postagemID = this.usuarioID = 0;
        this.conteudo = this.resposta = null;
        this.date = null;
    }

    public Comentario(int id, int postagemID, int usuarioID) {
        this.id = id;
        this.usuarioID = usuarioID;
        this.postagemID = postagemID;
        this.resposta = null;
        this.date = new Date(Instant.now().toEpochMilli());
    }

    public Comentario(int postagemID, int usuarioID) {
        Random random = new Random();
        this.id = random.nextInt();
        this.usuarioID = usuarioID;
        this.postagemID = postagemID;
        this.resposta = null;
        this.date = new Date(Instant.now().toEpochMilli());
    }

    // Gets
    public int getId() {
        return id;
    }

    public int getPostagemID() {
        return postagemID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public Date getDate() {
        return date;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getResposta() {
        return resposta;
    }

    // Sets
    public void setId(int id) {
        this.id = id;
    }

    public void setPostagemID(int postagemID) {
        this.postagemID = postagemID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
