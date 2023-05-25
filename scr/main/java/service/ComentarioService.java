package service;

import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.sql.Date;
import java.util.List;
import dao.ComentarioDAO;
import model.Comentario;
import spark.Request;
import spark.Response;
import util.Path;

public class ComentarioService {
  private ComentarioDAO comentarioDAO = new ComentarioDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;


  public ComentarioService() {
    makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Comentario(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Comentario(), orderBy);
  }
  // Rever
  public void makeForm(int tipo, Comentario comentario, int orderBy) {
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

    String umComentario = "";
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
    int id = request.params("ID");
    int Pid = request.params("postagemID");
    int Uid = request.params("usuarioID");

    String resp = "";

    Comentario comentario = new Comentario(id, Pid, Uid);

    if (comentarioDAO.create(comentario) == true) {
      resp = "Comentario (" + id + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Comentario (" + id + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    return form;
  }

  public Object get(Request request, Response response) {
    int id = request.params("ID");
    Comentario comentario = comentarioDAO.get(id);

    if (comentario != null) {
      response.status(200); // success
      makeForm(FORM_DETAIL, comentario, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Comentario " + id + " não encontrado.";
      makeForm();
      form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
          "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    return form;
  }

  public Object getToUpdate(Request request, Response response) {
    int id = request.params("ID");
    Comentario comentario = comentarioDAO.get(id);

    if (comentario != null) {
      response.status(200); // success
      makeForm(FORM_UPDATE, comentario, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Comentario " + id + " não encontrado.";
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
    Comentario comentario = comentarioDAO.get(id);
    String resp = "";

    if (comentario != null) {
      comentario.setPostagemID(Integer.parseInt(request.queryParams("postagemID")));
      comentario.setUsuarioID(Integer.parseInt(request.queryParams("usuarioID")));
      comentario.setDate(Date.parse(request.queryParams("date")));
      comentario.setConteudo(request.queryParams("conteudo"));
      comentario.setConteudo(request.queryParams("resposta"));

      comentarioDAO.update(comentario);
      response.status(200); // success
      resp = "Comentario (ID " + comentario.getId() + ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Comentario (ID " + comentario.getId() + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = request.params("ID");
    Comentario comentario = comentarioDAO.get(id);
    String resp = "";

    if (comentario != null) {
      comentarioDAO.delete(id);
      response.status(200); // success
      resp = "Comentario (" + id + ") excluída!";
    } else {
      response.status(404); // 404 Not found
      resp = "Comentario (" + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }
}
