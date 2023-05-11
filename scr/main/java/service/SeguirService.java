package service;

import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.sql.Date;
import java.util.List;
import dao.SeguirDAO;
import model.Seguir;
import spark.Request;
import spark.Response;
import util.Path;

public class SeguirService {
  private SeguirDAO seguirDAO = new SeguirDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;
  private final int FORM_ORDERBY_NOME = 2;

  public SeguirService() {
    makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Seguir(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Seguir(), orderBy);
  }
  // Rever
  public void makeForm(int tipo, Seguir seguir, int orderBy) {
    String nomeArquivo = Path.toFile(Path.Web.MODALIDADE);
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
    List<Seguir> seguirs = SeguirDAO.get();
    // incorreto
    for (Seguir lgs : seguirs) {
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
    int id = request.params("usuarioID");
    
    String resp = "";

    Seguir seguir = new Seguir(id);

    if (SeguirDAO.create(seguir) == true) {
      resp = "Seguir (" + nome + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Seguir (" + nome + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    response.redirect(Path.Web.MODALIDADE);
    return form;
  }

  public Object get(Request request, Response response) {
    int id = request.params("usuarioID");
    Seguir seguir = SeguirDAO.get(id);

    if (seguir != null) {
      response.status(200); // success
      makeForm(FORM_DETAIL, seguir, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Seguir " + id + " não encontrado.";
      makeForm();
      form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
          "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    return form;
  }

  public Object getToUpdate(Request request, Response response) {
    int id = request.params("usuarioID");
    Seguir seguir = SeguirDAO.get(id);

    if (seguir != null) {
      response.status(200); // success
      makeForm(FORM_UPDATE, seguir, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Seguir " + id + " não encontrado.";
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
    int id = request.params("usuarioID");
    Seguir seguir = SeguirDAO.get(id);
    String resp = "";

    if (seguir != null) {
      seguir.setSeguidor(Integer.parseInt(request.queryParams("seguidor")));
      seguir.setSeguindo(Integer.parseInt(request.queryParams("seguindo")));

      SeguirDAO.update(seguir);
      response.status(200); // success
      resp = "Seguir (ID " + seguir.getUsuarioID() + ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Seguir (ID " + seguir.getUsuarioID() + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = request.params("usuarioID");
    Seguir seguir = SeguirDAO.get(id);
    String resp = "";

    if (seguir != null) {
      SeguirDAO.delete(id);
      response.status(200); // success
      resp = "Seguir (" + id + ") excluída!";
    } else {
      response.status(404); // 404 Not found
      resp = "Seguir (" + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }
}