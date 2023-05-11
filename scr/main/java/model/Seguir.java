package model;

public class Seguir {
    private int usuarioID;
    private int seguidor;
    private int seguindo;


    // construtores
    public Seguir(){
        this.usuarioID = 0;
        this.seguidor = this.seguindo = 0;
    }

    public Seguir(int usuarioID) {
        this.usuarioID = usuarioID;
        this.seguidor = this.seguindo = 0;
    }

    // Gets

    public int getUsuarioID() {
        return usuarioID;
    }
    
    public int getSeguidor() {
        return seguidor;
    }

    public int getSeguindo() {
        return seguindo;
    }

    // Sets
    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setSeguidor(int seguidor) {
        this.seguidor = seguidor;
    }

    public void setSeguindo(int seguindo) {
        this.seguindo = seguindo;
    }

}