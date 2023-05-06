package model;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class Postagem {
  private UUID id;
  private UUID usuarioID;
  private UUID modalidadeID;
  private String conteudo;
  private String foto;
  private Date date;

  // Construtores
  public Postagem(){
    this.id = this.modalidadeID = this.usuarioID = null;
    this.conteudo = this.foto = null;
    this.date = null;
}

  public Postagem(UUID id, String conteudo, String foto, UUID modalidadeID, Date date, UUID usuarioID) {
    this.id = id;
    this.conteudo = conteudo;
    this.foto = foto;
    this.modalidadeID = modalidadeID;
    this.date = date;
    this.usuarioID = usuarioID;
  }

  public Postagem(String conteudo, String foto, UUID modalidadeID, UUID usuarioID) {
    this.conteudo = conteudo;
    this.foto = foto;
    this.modalidadeID = modalidadeID;
    this.usuarioID = usuarioID;
    this.id = UUID.randomUUID();
    this.date = new Date(Instant.now().toEpochMilli());
  }

  // Gets
  public UUID getId() {
    return id;
  }

  public UUID getUsuarioID() {
    return usuarioID;
  }

  public UUID getModalidadeID() {
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
  public void setId(UUID id) {
    this.id = id;
  }

  public void setUsuarioID(UUID usuarioID) {
    this.usuarioID = usuarioID;
  }

  public void setModalidadeID(UUID modalidadeID) {
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