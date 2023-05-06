package model;

import java.util.UUID;

public class Seguir {
    private UUID usuarioID;
    private int seguidor;
    private int seguindo;


    // construtores
    public Seguir(){
        this.usuarioID = null;
        this.seguidor = this.seguindo = 0;
    }

    public Seguir(UUID usuarioID) {
        this.usuarioID = usuarioID;
        this.seguidor = this.seguindo = 0;
    }

    // Gets

    public UUID getUsuarioID() {
        return usuarioID;
    }
    
    public int getSeguidor() {
        return seguidor;
    }

    public int getSeguindo() {
        return seguindo;
    }

    // Sets
    public void setUsuarioID(UUID usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setSeguidor(int seguidor) {
        this.seguidor = seguidor;
    }

    public void setSeguindo(int seguindo) {
        this.seguindo = seguindo;
    }

}