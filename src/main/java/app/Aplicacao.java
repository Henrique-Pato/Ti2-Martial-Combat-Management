package app;

import static spark.Spark.*;

// Importações relacionadas a renderização de páginas html
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

// Usar map e hashmap
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import service.ComentarioService;
import service.ModalidadeService;
import service.PostagemService;
import service.ReacaoService;
import service.SeguirService;
import service.UsuarioService;

import dao.UsuarioDAO;
import model.Usuario;

import dao.PostagemDAO;
import model.Postagem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;


public class Aplicacao {

  private static UsuarioService usuarioService = new UsuarioService();
  private static PostagemService postagemService = new PostagemService();
  private static ModalidadeService modalidadeService = new ModalidadeService();
  private static SeguirService seguirService = new SeguirService();
  private static ComentarioService comentarioService = new ComentarioService();
  private static ReacaoService reacaoService = new ReacaoService();

  public static void main(String[] args) {
    port(2223);

    staticFiles.location("/public");
        
    get("/cadastro", (request, response) -> {
    	  try {
    	    String filePath = Paths.get("src/main/resources/public/html/signup.html").toAbsolutePath().toString();
    	    InputStream fileInputStream = new FileInputStream(filePath);
    	    response.type("text/html");
    	    return fileInputStream;
    	  } catch (IOException e) {
    	    e.printStackTrace();
    	    return "Erro ao carregar a página de cadastro";
    	  }
    	});
    
    get("/login", (request, response) -> {
    	String authToken = request.cookie("authToken");
    	if(authToken != null && authToken.equals("logado")) {
    		response.redirect("/feed");
    	}    	
    	else {	
    		try {
        		String filePath = Paths.get("src/main/resources/public/html/login.html").toAbsolutePath().toString();
          	    InputStream fileInputStream = new FileInputStream(filePath);
          	    response.type("text/html");
          	    return fileInputStream;
        	}catch (IOException e) {
          		  e.printStackTrace();
          		  return "Erro ao carregar a página de login";
          	 }
    	}
    	return null;
    	});
    
    
    post("/cadastro", (request, response) -> usuarioService.insert(request, response));
    post("/login", (request, response) -> usuarioService.get(request, response));
    
    get("/feed", (request, response) -> {
        String authToken = request.cookie("authToken");

        if (!(authToken != null && authToken.equals("logado"))) {
        	 response.redirect("/login");
             return null;
        }
        
        Map<String, Object> model = new HashMap<>();
        
        int id = Integer.parseInt(request.cookie("id"));
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        Usuario usuario;
        usuario = usuarioDAO.get(id);
         
        
        PostagemDAO postagemDAO = new PostagemDAO();
        List<Postagem> postagens = new ArrayList<Postagem>();
        postagens = postagemDAO.get();
        
        // Informações pessoais do usuário
        model.put("nome",usuario.getNome());  
        model.put("sobrenome", usuario.getSobrenome());
        model.put("descricao",usuario.getDescricao()); 
        
        model.put("postagens", postagens);
        
        return new ModelAndView(model, "feed.mustache");
        
    }, new MustacheTemplateEngine());
  	
    get("/logout", (request, response) -> {
    	response.removeCookie("authToken");
    	response.removeCookie("id");
    	
    	response.redirect("/login");
    	return null;
    });
  
      
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
