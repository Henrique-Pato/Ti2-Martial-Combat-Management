package model;

import java.sql.Date;
import java.time.Instant;
import java.util.Random;


public class Postagem {
  private int id;
  private int usuarioID;
  private int modalidadeID;
  private String conteudo;
  private String foto;
  private Date date;
 

  // Construtores
  public Postagem(){
    this.id = this.modalidadeID = this.usuarioID = 0;
    this.conteudo = this.foto = null;
    this.date = null;
}

  public Postagem(int id, String conteudo,  int modalidadeID,  int usuarioID) {
    this.id = id;
    this.conteudo = conteudo;
    this.foto = null;
    this.modalidadeID = modalidadeID;
    this.date = new Date(Instant.now().toEpochMilli());
    this.usuarioID = usuarioID;
  }

  public Postagem(String conteudo, int modalidadeID, int usuarioID) {
    this.conteudo = conteudo;
    this.foto = null;
    this.modalidadeID = modalidadeID;
    this.usuarioID = usuarioID;
    Random random = new Random();
    this.id = Math.abs(random.nextInt());
    this.date = new Date(Instant.now().toEpochMilli());
  }
  
  public Postagem(int id, String conteudo,  int modalidadeID,  int usuarioID, String foto) {
	    this.id = id;
	    this.conteudo = conteudo;
	    this.foto = foto;
	    this.modalidadeID = modalidadeID;
	    this.date = new Date(Instant.now().toEpochMilli());
	    this.usuarioID = usuarioID;
	    
	  }

  // Gets
  public int getId() {
    return id;
  }

  public int getUsuarioID() {
    return usuarioID;
  }

  public int getModalidadeID() {
    return modalidadeID;
  }

  public String getConteudo() {
    return conteudo;
  }

  public String getFoto() {
    return foto;
  }

  public Date getDate() {
    return date;
  }

  // Sets
  public void setId(int id) {
    this.id = id;
  }

  public void setUsuarioID(int usuarioID) {
    this.usuarioID = usuarioID;
  }

  public void setModalidadeID(int modalidadeID) {
    this.modalidadeID = modalidadeID;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public void setDate(Date date) {
    this.date = date;
  }

}

