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
  private UsuarioDAO lingDAO = new UsuarioDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;
  private final int FORM_ORDERBY_NOME = 2;

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
    String nomeArquivo = Path.toFile(Path.Web.USUARIO);
    form = "";
    String list = "";
    try {
      Scanner entrada = new Scanner(new File(nomeArquivo));
      while (entrada.hasNext()) {
        form += (entrada.nextLine() + "\n");
      }
      entrada.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    List<Usuario> lings = lingDAO.get();
    // incorreto
    for (Usuario lgs : lings) {
      list += "<a href=\"/topicos/" + lgs.getId() + "\"><img src=\" " +
          lgs.getImg() + "\" alt=\"card "
          + lgs.getNome() + " \" class=\"cardsLinguagens\"></a>\r\n";
    }
    form = form.replaceFirst("<CARDS-LINGUAGEM>", list);

  }

  public Object mostra(Request request, Response response) {
    makeForm(1);
    response.header("Content-Type", "text/html");
    response.header("Content-Encoding", "UTF-8");
    return form;
  }

  public Object insert(Request request, Response response) {
    int id = request.params("ID");
    String nome = request.queryParams("nome");
    String email = request.queryParams("email");
    byte[] hashedPassword = request.queryParams("senha");

    String resp = "";

    Usuario usuario = new Usuario(id, nome, email, hashedPassword);

    if (UsuarioDAO.create(usuario) == true) {
      resp = "Usuario (" + nome + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Usuario (" + nome + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    response.redirect(Path.Web.PERFIL);
    return form;
  }

  public Object get(Request request, Response response) {
    int id = request.params("ID");
    Usuario usuario = UsuarioDAO.get(id);

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
    int id = request.params("ID");
    Usuario usuario = UsuarioDAO.get(id);

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
    int id = request.params("ID");
    Usuario usuario = UsuarioDAO.get(id);
    String resp = "";

    if (usuario != null) {
      usuario.setNome(request.queryParams("nome"));
      usuario.setSobrenome(request.queryParams("sobrenome"));
      usuario.setEmail(request.queryParams("email"));
      usuario.setDescricao(request.queryParams("descricao"));
      usuario.setFoto(request.queryParams("foto"));
      usuario.setIdade(Integer.parseInt(request.queryParams("idade")));
      usuario.setIdade(Date.parse(request.queryParams("nascimento")));
      usuario.setHashedPassword(Byte.parse(request.queryParams("senha")));

      UsuarioDAO.update(usuario);
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
    int id = request.params("ID");
    Usuario usuario = UsuarioDAO.get(id);
    String resp = "";

    if (usuario != null) {
      UsuarioDAO.delete(id);
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
