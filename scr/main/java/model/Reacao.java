package model;

import java.util.UUID;

public class Reacao {
    private UUID postagemID;
    private UUID usuarioID;
    private String reacao;


    // construtores
    public Reacao(){
        this.postagemID = this.usuarioID = null;
        this.reacao = null;
    }

    public Reacao(UUID postagemID, UUID usuarioID) {
        this.usuarioID = usuarioID;
        this.postagemID = postagemID;
        this.reacao = null;
    }

    // Gets
    public UUID getPostagemID() {
        return postagemID;
    }

    public UUID getUsuarioID() {
        return usuarioID;
    }

    public String getReacao() {
        return reacao;
    }

    // Sets
    public void setPostagemID(UUID postagemID) {
        this.postagemID = postagemID;
    }

    public void setUsuarioID(UUID usuarioID) {
        this.usuarioID = usuarioID;
    }
    public void setReacao(String reacao) {
        this.reacao = reacao;
    }
}