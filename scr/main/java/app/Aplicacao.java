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

    post("/usuario/cadastrar", (request, response) -> usuarioService.insert(request, response));
    post("/usuario/login", (request, response) -> usuarioService.login(request, response));
    post("/usuario/update", (request, response) -> usuarioService.update(request, response));
    post("/usuario/delete", (request, response) -> usuarioService.delete(request, response));
    get("/usuario/info/:username", (request, response) -> usuarioService.get(request, response));
    get("/usuario/pesquisar/:query", (request, response) -> usuarioService.search(request, response));
    get("/usuario/feed/:username", (request, response) -> usuarioService.feed(request, response));

    post("/postagem/criar", (request, response) -> postagemService.insert(request, response));
    post("/postagem/update/:id", (request, response) -> postagemService.update(request, response));
    post("/postagem/delete/:id", (request, response) -> postagemService.delete(request, response));
    get("/postagem/:id", (request, response) -> postagemService.get(request, response));
    get("/postagem/listar/categoria/:id", (request, response) -> postagemService.list(request, response));
    get("/postagem/usuario/:username", (request, response) -> postagemService.list(request, response));

    post("/modalidade/criar", (request, response) -> modalidadeService.insert(request, response));
    post("/modalidade/update/:id", (request, response) -> modalidadeService.update(request, response));
    post("/modalidade/delete/:id", (request, response) -> modalidadeService.delete(request, response));
    get("/modalidade/:id", (request, response) -> modalidadeService.get(request, response));
    get("/modalidade/listar/:id", (request, response) -> modalidadeService.list(request, response));


    post("/comentario/criar", (request, response) -> comentarioService.insert(request, response));
    post("/comentario/update/:id", (request, response) -> comentarioService.update(request, response));
    post("/comentario/delete/:id", (request, response) -> comentarioService.delete(request, response));
    get("/comentario/:id", (request, response) -> comentarioService.get(request, response));
    get("/comentario/listar/:id", (request, response) -> comentarioService.list(request, response));
    get("/comentario/usuario/:username", (request, response) -> comentarioService.list(request, response));

    post("/seguir/criar", (request, response) -> seguirService.insert(request, response));
    post("/seguir/update/:id", (request, response) -> seguirService.update(request, response));
    post("/seguir/delete/:id", (request, response) -> seguirService.delete(request, response));
    get("/seguir/:id", (request, response) -> seguirService.get(request, response));
    get("/seguir/listar/:id", (request, response) -> seguirService.list(request, response));

    post("/reacao/criar", (request, response) -> reacaoService.insert(request, response));
    post("/reacao/update/:id", (request, response) -> reacaoService.update(request, response));
    post("/reacao/delete/:id", (request, response) -> reacaoService.delete(request, response));
    get("/reacao/:id", (request, response) -> reacaoService.get(request, response));
    get("/reacao/listar/:id", (request, response) -> reacaoService.list(request, response));

  }
}