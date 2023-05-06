package model;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class Comentario {
    private UUID id;
    private UUID postagemID;
    private UUID usuarioID;
    private Date date;
    private String conteudo;
    private String resposta;

    // construtores
    public Comentario(){
        this.id = this.postagemID = this.usuarioID = null;
        this.conteudo = this.resposta = null;
        this.date = null;
    }

    public Comentario(UUID id, UUID postagemID, UUID usuarioID) {
        this.id = id;
        this.usuarioID = usuarioID;
        this.postagemID = postagemID;
        this.resposta = null;
        this.date = new Date(Instant.now().toEpochMilli());
    }

    public Comentario(UUID postagemID, UUID usuarioID) {
        this.id = UUID.randomUUID();
        this.usuarioID = usuarioID;
        this.postagemID = postagemID;
        this.resposta = null;
        this.date = new Date(Instant.now().toEpochMilli());
    }

    // Gets
    public UUID getId() {
        return id;
    }

    public UUID getPostagemID() {
        return postagemID;
    }

    public UUID getUsuarioID() {
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
    public void setId(UUID id) {
        this.id = id;
    }

    public void setPostagemID(UUID postagemID) {
        this.postagemID = postagemID;
    }

    public void setUsuarioID(UUID usuarioID) {
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