package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import model.Comentario;

/**
 * ComentarioDAO: herda DAO e utiliza model Comentario.
 * Realiza insercao, atualizacao, exclusao e recuperacao
 * de comentarios do banco de dados com consultas SQL.
 * 
 * @author Henrique Pato Magalhães
 * @version 1 05/04/23
 */
public class ComentarioDAO extends DAO {
  /**
   * Construtor da classe: faz conexao com <code>DAO</code>
   */
  public ComentarioDAO() {
    super();
    conectar();
  }

  /**
   * Encerra conexao com <code>DAO</code>
   */
  public void finalize() {
    close();
  }

  /**
   * Insere comentario no banco de dados
   * 
   * @param comentario <code>Comentario</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir inserir
   *         <code>false</code> se nao conseguir
   */
  public boolean create(Comentario comentario) {
    boolean status = false;

    try {
      String sql = "INSERT INTO Comentario (ID postagemID usuarioID date conteudo resposta) VALUES (?, ?, ?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setObject(1, comentario.getId());
      st.setObject(2, comentario.getPostagemID());
      st.setObject(3, comentario.getUsuarioID());
      st.setDate(4, comentario.getDate());
      st.setString(5, comentario.getConteudo());
      st.setString(6, comentario.getResposta());


      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Recupera comentario do banco de dados pelo id
   * 
   * @param username <code>int</code> identificador do comentario
   * @return <code>Comentario</code> recuperada
   */
  public Comentario get(int id) {
    Comentario comentario = null;

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Comentario WHERE ID= '"+id+"'";
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {

        comentario = new Comentario(rs.getInt("ID"), rs.getInt("postagemID"), rs.getInt("usuarioID"));
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return comentario;
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando orderBy nulo
   * 
   * @return <code>List<Comentario></code> lista de comentarios nao ordenada
   */
  public List<Comentario> get() {
    return getList("");
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando ID como orderBy
   * 
   * @return <code>List<Id></code> lista de comentarios ordenada pelo ID
   */
  public List<Comentario> getOrderByID() {
    return getList("ID");
  }

  /**
   * Ordena lista de comentarios pelo orderBy solicitado
   * 
   * @param orderBy <code>String</code> chave de ordenacao
   * @return <code>List<Comentario></code> lista de comentarios
   */
  private List<Comentario> getList(String orderBy) {
    List<Comentario> comentarios = new ArrayList<Comentario>();

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Comentario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {

        Comentario c = new Comentario(rs.getInt("ID"), rs.getInt("postagemID"), rs.getInt("usuarioID"));

        comentarios.add(c);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return comentarios;
  }

  public Comentario[] list(int id) {
    Comentario[] comentarios = new Comentario[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Comentario WHERE ID=" + id;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {

        comentarios[i] = new Comentario(rs.getInt("ID"), rs.getInt("postagemID"), rs.getInt("usuarioID"));

    
        i++;
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return comentarios;
  }

  /**
   * Atualiza comentario no banco de dados
   * 
   * @param comentario <code>comentario</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir atualizar
   *         <code>false</code> se nao conseguir
   */
  public boolean update(Comentario comentario) {
    boolean status = false;

    try {
      String sql = "UPDATE Comentario SET usuarioID=?, postagemID=? , data=?, conteudo=?, resposta=? WHERE ID =" + comentario.getId();

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setObject(1, comentario.getId());
      st.setObject(2, comentario.getPostagemID());
      st.setObject(3, comentario.getUsuarioID());
      st.setDate(4, comentario.getDate());
      st.setString(5, comentario.getConteudo());
      st.setString(6, comentario.getResposta());

      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Deleta comentario com id passado do banco de dados
   * 
   * @param id <code>UUID</code> identificador do comentario
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir deletar
   *         <code>false</code> se nao conseguir
   */
  public boolean delete(int id) {
    boolean status = false;

    try {
      Statement st = conexao.createStatement();
      st.executeUpdate("DELETE FROM Comentario WHERE ID = " + id);
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Busca comentario a partir de um ID
   * 
   * @param id <code>UUID</code> identificador do comentario
   * @return <code>Comentario</code> status
   */
  public Comentario[] search(UUID query) {
    Comentario[] comentarios = new Comentario[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Comenterio WHERE ID=" + query;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {

        comentarios[i] = new Comentario(rs.getInt("ID"), rs.getInt("postagemID"), rs.getInt("usuarioID"));

        i++;
      }
      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return comentarios;
  }
}