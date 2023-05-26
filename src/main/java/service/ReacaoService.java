package service;

import java.util.Scanner;
import java.io.File;
import dao.ReacaoDAO;
import model.Reacao;
import spark.Request;
import spark.Response;


public class ReacaoService {
  private ReacaoDAO reacaoDAO = new ReacaoDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;

  public ReacaoService() {
    //makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Reacao(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Reacao(), orderBy);
  }
  // Rever
  public void makeForm(int tipo, Reacao reacao, int orderBy) {
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

    String umReacao = "";
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
    int Pid = Integer.parseInt(request.params("postagemID"));
    int Uid = Integer.parseInt(request.params("usuarioID"));

    String resp = "";

    Reacao reacao = new Reacao(Pid, Uid);

    if (reacaoDAO.create(reacao) == true) {
      resp = "Reacao (" + Uid + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Reacao (" + Uid + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    return form;
  }

  public Object get(Request request, Response response) {
    int id = Integer.parseInt(request.params("usuarioID"));
    Reacao reacao = reacaoDAO.get(id);

    if (reacao != null) {
      response.status(200); // success
      makeForm(FORM_DETAIL, reacao, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Reacao " + id + " não encontrado.";
      makeForm();
      form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
          "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    return form;
  }

  public Object getToUpdate(Request request, Response response) {
    int id = Integer.parseInt(request.params("usuarioID"));
    Reacao reacao = reacaoDAO.get(id);

    if (reacao != null) {
      response.status(200); // success
      makeForm(FORM_UPDATE, reacao, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Reacao " + id + " não encontrado.";
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
    int id = Integer.parseInt(request.params("usuarioID"));
    Reacao reacao = reacaoDAO.get(id);
    String resp = "";

    if (reacao != null) {
      reacao.setReacao(request.queryParams("reacao"));

      reacaoDAO.update(reacao);
      response.status(200); // success
      resp = "Reacao (ID " + reacao.getUsuarioID()+ ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Reacao (ID " + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params("usuarioID"));
    Reacao reacao = reacaoDAO.get(id);
    String resp = "";

    if (reacao != null) {
      reacaoDAO.delete(id);
      response.status(200); // success
      resp = "Reacao (" + id + ") excluída!";
    } else {
      response.status(404); // 404 Not found
      resp = "Reacao (" + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }
}
