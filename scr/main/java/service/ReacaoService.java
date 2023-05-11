package service;

import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.sql.Date;
import java.util.List;
import dao.ReacaoDAO;
import model.Reacao;
import spark.Request;
import spark.Response;
import util.Path;

public class ReacaoService {
  private ReacaoDAO lingDAO = new ReacaoDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;
  private final int FORM_ORDERBY_NOME = 2;

  public ReacaoService() {
    makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Reacao(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Reacao(), orderBy);
  }
  // Rever
  public void makeForm(int tipo, Reacao reacao, int orderBy) {
    String nomeArquivo = Path.toFile(Path.Web.Reacao);
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
    List<Reacao> lings = lingDAO.get();
    // incorreto
    for (Reacao lgs : lings) {
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
    int Pid = request.params("postagemID");
    int Uid = request.params("usuarioID");

    String resp = "";

    Reacao reacao = new Reacao(Pid, Uid);

    if (ReacaoDAO.create(reacao) == true) {
      resp = "Reacao (" + Uid + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Reacao (" + Uid + ") não inserido!";
      response.status(404); // 404 Not found
    }

    makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    response.redirect(Path.Web.PERFIL);
    return form;
  }

  public Object get(Request request, Response response) {
    int id = request.params("usuarioID");
    Reacao reacao = ReacaoDAO.get(id);

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
    int id = request.params("usuarioID");
    Reacao reacao = ReacaoDAO.get(id);

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
    int id = request.params("usuarioID");
    Reacao reacao = ReacaoDAO.get(id);
    String resp = "";

    if (reacao != null) {
      reacao.setReacao(request.queryParams("reacao"));

      ReacaoDAO.update(reacao);
      response.status(200); // success
      resp = "Reacao (ID " + reacao.getUsuarioID()+ ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Reacao (ID " + reacao.getUsuarioID() + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = request.params("usuarioID");
    Reacao reacao = ReacaoDAO.get(id);
    String resp = "";

    if (reacao != null) {
      ReacaoDAO.delete(id);
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
