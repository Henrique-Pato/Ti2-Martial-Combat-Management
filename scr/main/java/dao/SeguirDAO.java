package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Seguir;

/**
 * SeguirDAO: herda DAO e utiliza model Seguir.
 * Realiza insercao, atualizacao, exclusao e recuperacao
 * de Seguirs do banco de dados com consultas SQL.
 * 
 * @author Henrique Pato Magalhães
 * @version 1 01/05/23
 */
public class SeguirDAO extends DAO {
  /**
   * Construtor da classe: faz conexao com <code>DAO</code>
   */
  public SeguirDAO() {
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
   * Insere seguir no banco de dados
   * 
   * @param seguir <code>seguir</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir inserir
   *         <code>false</code> se nao conseguir
   */
  public boolean create(Seguir seguir) {
    boolean status = false;

    try {
      String sql = "INSERT INTO Seguir (usuarioID, seguidor, seguindo ) VALUES (?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setObject(1, seguir.getUsuarioID());
      st.setInt(2, seguir.getSeguidor());
      st.setInt(3, seguir.getSeguindo());
      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Recupera seguir do banco de dados pelo usuarioID
   * 
   * @param seguir <code>int</code> identificador da seguir
   * @return <code>Seguir</code> recuperada
   */
  public Seguir get(UUID id) {
    Seguir seguir = null;

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Seguir WHERE usuarioID= '"+id+"'";
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {
        UUID _id = (UUID) rs.getObject("ID");
        seguir = new Seguir(_id);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return seguir;
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando orderBy nulo
   * 
   * @return <code>List<Seguir></code> lista de seguir nao ordenada
   */
  public List<Seguir> get() {
    return getList("");
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando nome como orderBy
   * 
   * @return <code>List<nome></code> lista de Seguir ordenada pelo UsuarioID
   */
  public List<Seguir> getOrderBynome() {
    return getList("usuarioID");
  }

  /**
   * Ordena lista de seguir pelo orderBy solicitado
   * 
   * @param orderBy <code>String</code> chave de ordenacao
   * @return <code>List<Seguir></code> lista de seguir
   */
  private List<Seguir> getList(String orderBy) {
    List<Seguir> seguir = new ArrayList<Seguir>();

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Seguir" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {
        UUID _id = (UUID) rs.getObject("usuarioID");
        Seguir s = new Seguir(_id);

        seguir.add(s);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return seguir;
  }

  public Seguir[] list(UUID id) {
    Seguir[] seguir = new Seguir[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Seguir WHERE usuarioID=" + id;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        UUID _id = (UUID) rs.getObject("usuarioID");
        seguir[i] = new Seguir(_id);

        i++;
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return seguir;
  }

  /**
   * Atualiza seguir no banco de dados
   * 
   * @param seguir <code>Seguir</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir atualizar
   *         <code>false</code> se nao conseguir
   */
  public boolean update(UUID id, Seguir seguir) {
    boolean status = false;

    try {
      String sql = "UPDATE Seguir seguidores=?, seguindo=? WHERE usuarioID =" + id;

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setInt(1, seguir.getSeguidor());
      st.setInt(2, seguir.getSeguindo());


      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Deleta seguir com nome passado do banco de dados
   * 
   * @param id <code>UUID</code> identificador do seguir
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir deletar
   *         <code>false</code> se nao conseguir
   */
  public boolean delete(UUID id) {
    boolean status = false;

    try {
      Statement st = conexao.createStatement();
      st.executeUpdate("DELETE FROM Seguir WHERE usuarioID = " + id);
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Busca seguir a partir de um usuarioID
   * 
   * @param id <code>UUID</code> identificador do seguir
   * @return <code>Modalidade</code> status
   */
  public Seguir[] search(String query) {
    Seguir[] seguir = new Seguir[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Modalidade WHERE usuarioID=" + query;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        UUID _id = (UUID) rs.getObject("usuarioID");
        seguir[i] = new Seguir(_id);

        i++;
      }
      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return seguir;
  }
}