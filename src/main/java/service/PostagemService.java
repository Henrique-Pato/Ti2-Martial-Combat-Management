package service;

import java.util.Scanner;
import java.io.File;
import dao.PostagemDAO;
import model.Postagem;
import spark.Request;
import spark.Response;

public class PostagemService {
  private PostagemDAO postagemDAO = new PostagemDAO();
  private String form;
  private final int FORM_INSERT = 1;
  private final int FORM_DETAIL = 2;
  private final int FORM_UPDATE = 3;
  private final int FORM_ORDERBY_ID = 1;

  public PostagemService() {
    //makeform();
  }

  public void makeForm() {
    makeForm(FORM_INSERT, new Postagem(), FORM_ORDERBY_ID);
  }

  public void makeForm(int orderBy) {
    makeForm(FORM_INSERT, new Postagem(), orderBy);
  }
  // Rever
  public void makeForm(int tipo, Postagem Postagem, int orderBy) {
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

    String umPostagem = "";
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
    String conteudo = request.queryParams("conteudo");
    int Uid = Integer.parseInt(request.params("usuarioID"));
    int Mid = Integer.parseInt(request.params("modalidadeID"));

    String resp = "";

    Postagem postagem = new Postagem(conteudo, Mid, Uid);

    if (postagemDAO.insert(postagem) == true) {
      resp = "Postagem (" + Uid + ") inserido!";
      response.status(201); // 201 Created
    } else {
      resp = "Postagem (" + Uid + ") não inserido!";
      response.status(404); // 404 Not found
    }

     makeForm();
    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");

    return form;
  }

  public Object get(Request request, Response response) {
    int id = Integer.parseInt(request.params("usuarioID"));
    Postagem postagem = postagemDAO.get(id);

    if (postagem != null) {
      response.status(200); // success
      makeForm(FORM_DETAIL, postagem, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Postagem " + id + " não encontrado.";
  
    }
    return null;
  }

  public Object getToUpdate(Request request, Response response) {
    int id = Integer.parseInt(request.params("ID"));
    Postagem postagem = postagemDAO.get(id);

    if (postagem != null) {
      response.status(200); // success
      makeForm(FORM_UPDATE, postagem, FORM_ORDERBY_ID);
    } else {
      response.status(404); // 404 Not found
      String resp = "Postagem " + id + " não encontrado.";
      
    }

    return null;
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
    Postagem postagem = postagemDAO.get(id);
    String resp = "";

    if (postagem != null) {
      postagem.setConteudo(request.queryParams("conteudo"));
      postagem.setFoto(request.queryParams("foto"));
      

      postagemDAO.update(postagem);
      response.status(200); // success
      resp = "Postagem (ID " + postagem.getId() + ") atualizada!";
    } else {
      response.status(404); // 404 Not found
      resp = "Postagem (ID " + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params("ID"));
    Postagem postagem = postagemDAO.get(id);
    String resp = "";

    if (postagem != null) {
      postagemDAO.delete(id);
      response.status(200); // success
      resp = "Postagem (" + id + ") excluída!";
    } else {
      response.status(404); // 404 Not found
      resp = "Postagem (" + id + ") não encontrada!";
    }
    makeForm();
    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
        "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
  }
}
