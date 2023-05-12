// incompleto

package service;

import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.sql.Date;
import java.util.List;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;
import util.Path;

public class UsuarioService {
  private UsuarioDAO usuarioDAO = new UsuarioDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;

  public UsuarioService() {
    makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Usuario(), orderBy);
  }

  // Rever
  public void makeForm(int tipo, Usuario usuario, int orderBy) {
    String nomeArquivo = "form.html";
    form = "";
    try {
      Scanner entrada = new Scanner(new File(nomeArquivo));
      while (entrada.hasNext()) {
        form += (entrada.nextLine() + "\n");
      }
      entrada.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    String umUsuario = "";
    if (tipo != FORM_INSERT) {
      //
    }

    if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
      //
    } else if (tipo == FORM_DETAIL) {
      //
    } else {
      System.out.println("ERRO! Tipo não identificado " + tipo);
    }

  }

  public Object insert(Request request, Response response) {
    String nome = request.queryParams("nome_cad");
    String email = request.queryParams("email_cad");
    byte[] hashedPassword = request.queryParams("senha_cad");

    String resp = "";

    Usuario usuario = new Usuario(nome, email, hashedPassword);

    if (usuarioDAO.create(usuario) == true) {
      resp = "Usuario (" + nome + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Usuario (" + nome + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    return form;
  }

  public Object get(Request request, Response response) {
    int id = Integer.parseInt(request.params("ID"));
    Usuario usuario = usuarioDAO.get(id);

    if (usuario != null) {
      response.status(200); // success
      makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Usuario " + id + " não encontrado.";
      makeForm();
      form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
          "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    return form;
  }

  public Object getToUpdate(Request request, Response response) {
    int id = Integer.parseInt(request.params("ID"));
    Usuario usuario = usuarioDAO.get(id);

    if (usuario != null) {
      response.status(200); // success
      makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Usuario " + id + " não encontrado.";
      makeForm();
      form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
          "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    return form;
  }

  public Object getAll(Request request, Response response) {
    int orderBy = Integer.parseInt(request.params(":orderby"));
    makeForm(orderBy);
    response.header("Content-Type", "text/html");
    response.header("Content-Encoding", "UTF-8");
    return form;
  }

  public Object update(Request request, Response response) {
    int id = Integer.parseInt(request.params("ID"));
    Usuario usuario = usuarioDAO.get(id);
    String resp = "";

    if (usuario != null) {
      usuario.setNome(request.queryParams("nome_cad"));
      usuario.setSobrenome(request.queryParams("sobrenome"));
      usuario.setEmail(request.queryParams("email_cad"));
      usuario.setDescricao(request.queryParams("descricao"));
      usuario.setFoto(request.queryParams("foto"));
      usuario.setIdade(Integer.parseInt(request.queryParams("idade")));
      usuario.setIdade(Date.parse(request.queryParams("nascimento")));
      usuario.setHashedPassword(Byte.parse(request.queryParams("senha_cad")));

      usuarioDAO.update(usuario);
      response.status(200); // success
      resp = "Usuario (ID " + usuario.getId() + ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Usuario (ID " + usuario.getId() + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.queryParams("ID"));
    Usuario usuario = usuarioDAO.get(id);
    String resp = "";

    if (usuario != null) {
      usuarioDAO.delete(id);
      response.status(200); // success
      resp = "Usuario (" + id + ") excluída!";
    } else {
      response.status(404); // 404 Not found
      resp = "Usuario (" + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }
}
