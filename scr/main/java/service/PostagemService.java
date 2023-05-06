// incompleto

package service;

// import java.security.acl.Owner;
// import java.sql.Date;
// import java.time.Instant;
import java.util.UUID;

import org.json.JSONObject;

import dao.PostagemDAO;
import model.Postagem;
import spark.Request;
import spark.Response;

public class PostagemService {
  private PostagemDAO postagemDAO = new PostagemDAO();
  private SessionService sessionService = new SessionService();

  public PostagemService() {

  }

  public Object insert(Request request, Response response) {
    response.type("application/json");
    JSONObject resp = new JSONObject();

    JSONObject body = new JSONObject(request.body());

    Session session = new Session(body.getJSONObject("session"));

    if (sessionService.isAuth(session)) {
      String usuarioID = request.params("UsuarioID");
      String foto = request.params("Foto");
      String modalidadeID = request.params("ModalidadeID");
      String conteudo = request.params("Conteudo");

      Postagem post = new Postagem(conteudo, foto, UUID.fromString(modalidadeID), UUID.fromString(usuarioID));

      if (postagemDAO.insert(post)) {
        resp.put("status", 0);
      } else {
        resp.put("status", 2);
      }
    } else {
      resp.put("status", 1);
    }

    return resp;
  }

  public Object update(Request request, Response response) {
    response.type("application/json");
    JSONObject resp = new JSONObject();

    JSONObject body = new JSONObject(request.body());

    Session session = new Session(body.getJSONObject("session"));

    if (sessionService.isAuth(session)) {
      Postagem post = postagemDAO.get(UUID.fromString(request.params("ID")));

      if (post != null && post.getUsuarioID().equals(session.getUsuarioID())) {
        String foto = request.params("Foto");
        String modalidadeId = request.params("ModalidadeID");
        String conteudo = request.params("Conteudo");

        post.setFoto(foto);
        post.setModalidadeID(UUID.fromString(modalidadeId));
        post.setConteudo(conteudo);
        if (postagemDAO.update(post)) {
          resp.put("status", 0);
        } else {
          resp.put("status", 1);
        }
      } else {
        resp.put("status", 2);
      }
    } else {
      resp.put("status", 3);
    }

    return resp;
  }

  public Object delete(Request request, Response response) {
    response.type("application/json");
    JSONObject resp = new JSONObject();

    JSONObject body = new JSONObject(request.body());

    Session session = new Session(body.getJSONObject("session"));

    if (sessionService.isAuth(session)) {

      Postagem post = postagemDAO.get(UUID.fromString(request.params("ID")));

      if (post != null && post.getUsuarioID().equals(session.getUsuarioID())) {
        if (postagemDAO.delete(post.getId())) {
          resp.put("status", 0);
        } else {
          resp.put("status", 1);
        }
      } else {
        resp.put("status", 2);
      }
    } else {
      resp.put("status", 3);
    }

    return resp;
  }

  public Object get(Request request, Response response) {
    response.type("application/json");
    String id = request.params("ID");
    Postagem post = postagemDAO.get(UUID.fromString(id));

    return post.toJSON();
  }

  public Object list(Request request, Response response) {
    Postagem[] postagens = postagemDAO.list(request.queryParams("Nome"));

    JSONObject resp = new JSONObject();

    for (Postagem postagem : postagens) {
      resp.append("postagens", postagem.toJSON());
    }

    return resp;
  }
}