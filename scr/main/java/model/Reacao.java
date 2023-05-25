package model;

public class Reacao {
    private int postagemID;
    private int usuarioID;
    private String reacao;


    // construtores
    public Reacao(){
        this.postagemID = this.usuarioID = 0;
        this.reacao = null;
    }

    public Reacao(int postagemID, int usuarioID) {
        this.usuarioID = usuarioID;
        this.postagemID = postagemID;
        this.reacao = null;
    }

    // Gets
    public int getPostagemID() {
        return postagemID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public String getReacao() {
        return reacao;
    }

    // Sets
    public void setPostagemID(int postagemID) {
        this.postagemID = postagemID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }
    public void setReacao(String reacao) {
        this.reacao = reacao;
    }
}