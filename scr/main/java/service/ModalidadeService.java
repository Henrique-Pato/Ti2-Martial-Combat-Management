package service;

import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.sql.Date;
import java.util.List;
import dao.ModalidadeDAO;
import model.Modalidade;
import spark.Request;
import spark.Response;
import util.Path;

public class ModalidadeService {
  private ModalidadeDAO modalidadeDAO = new ModalidadeDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;

  public ModalidadeService() {
    makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Modalidade(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Modalidade(), orderBy);
  }
  // Rever
  public void makeForm(int tipo, Modalidade modalidade, int orderBy) {
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

    String umModalidade = "";
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
    String nome = request.queryParams("nome");
    
    String resp = "";

    Modalidade modalidade = new Modalidade(id, nome);

    if (modalidadeDAO.create(modalidade) == true) {
      resp = "Modalidade (" + nome + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Modalidade (" + nome + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    return form;
  }

  public Object get(Request request, Response response) {
    int id = request.params("ID");
    Modalidade modalidade = modalidadeDAO.get(id);

    if (modalidade != null) {
      response.status(200); // success
      makeForm(FORM_DETAIL, modalidade, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Modalidade " + id + " não encontrado.";
      makeForm();
      form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
          "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    return form;
  }

  public Object getToUpdate(Request request, Response response) {
    int id = request.params("ID");
    Modalidade modalidade = modalidadeDAO.get(id);

    if (modalidade != null) {
      response.status(200); // success
      makeForm(FORM_UPDATE, modalidade, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Modalidade " + id + " não encontrado.";
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
    Modalidade modalidade = modalidadeDAO.get(id);
    String resp = "";

    if (modalidade != null) {
      modalidade.setNome(request.queryParams("nome"));
      modalidade.setSigla(request.queryParams("sigla"));
      modalidade.setDescricao(request.queryParams("descricao"));

      modalidadeDAO.update(modalidade);
      response.status(200); // success
      resp = "Modalidade (ID " + modalidade.getId() + ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Modalidade (ID " + modalidade.getId() + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = request.params("ID");
    Modalidade modalidade = modalidadeDAO.get(id);
    String resp = "";

    if (modalidade != null) {
      modalidadeDAO.delete(id);
      response.status(200); // success
      resp = "Modalidade (" + id + ") excluída!";
    } else {
      response.status(404); // 404 Not found
      resp = "Modalidade (" + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }
}