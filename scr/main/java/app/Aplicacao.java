//Incompleto

package app;

import static spark.Spark.*;
import service.PostagemService;
import service.UsuarioService;
import service.ModalidadeService;
import service.ComentarioService;
import service.ReacaoService;
import service.SeguirService;

public class Aplicacao {

  private static UsuarioService usuarioService = new UsuarioService();
  private static PostagemService postagemService = new PostagemService();
  private static ModalidadeService modalidadeService = new ModalidadeService();
  private static SeguirService seguirService = new SeguirService();
  private static ComentarioService comentarioService = new ComentarioService();
  private static ReacaoService reacaoService = new ReacaoService();

  public static void main(String[] args) {
    port(6789);

    staticFiles.location("/");

    post("/usuario", (request, response) -> usuarioService.insert(request, response));
    get("/usuario/:ID", (request, response) -> usuarioService.get(request, response));
    get("/usuario/update/:ID", (request, response) -> usuarioService.update(request, response));
    get("/usuario/delete/:ID", (request, response) -> usuarioService.delete(request, response));
    get("/usuario", (request, response) -> usuarioService.getAll(request, response));

    post("/postagem", (request, response) -> postagemService.insert(request, response));
    get("/postagem/:ID", (request, response) -> postagemService.get(request, response));
    get("/postagem/update/:ID", (request, response) -> postagemService.update(request, response));
    get("/postagem/delete/:ID", (request, response) -> postagemService.delete(request, response));
    get("/postagem", (request, response) -> postagemService.getAll(request, response));

    post("/modalidade", (request, response) -> modalidadeService.insert(request, response));
    get("/modalidade/:ID", (request, response) -> modalidadeService.get(request, response));
    get("/modalidade/update/:ID", (request, response) -> modalidadeService.update(request, response));
    get("/modalidade/delete/:ID", (request, response) -> modalidadeService.delete(request, response));
    get("/modalidade", (request, response) -> modalidadeService.getAll(request, response));

    post("/comentario", (request, response) -> comentarioService.insert(request, response));
    get("/comentario/:ID", (request, response) -> comentarioService.get(request, response));
    get("/comentario/update/:ID", (request, response) -> comentarioService.update(request, response));
    get("/comentario/delete/:ID", (request, response) -> comentarioService.delete(request, response));
    get("/comentario", (request, response) -> comentarioService.getAll(request, response));

    post("/seguir", (request, response) -> seguirService.insert(request, response));
    get("/seguir/:usuarioID", (request, response) -> seguirService.get(request, response));
    get("/seguir/update/:usuarioID", (request, response) -> seguirService.update(request, response));
    get("/seguir/delete/:usuarioID", (request, response) -> seguirService.delete(request, response));
    get("/seguir", (request, response) -> seguirService.getAll(request, response));

    post("/reacao", (request, response) -> reacaoService.insert(request, response));
    get("/reacao/:usuarioID", (request, response) -> reacaoService.get(request, response));
    post("/reacao/update/:usuarioID", (request, response) -> reacaoService.update(request, response));
    post("/reacao/delete/:usuarioID", (request, response) -> reacaoService.delete(request, response));
    get("/reacao", (request, response) -> reacaoService.getAll(request, response));

  }
}