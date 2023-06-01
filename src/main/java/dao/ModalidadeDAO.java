package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import model.Modalidade;

/**
 * ModalidadeDAO: herda DAO e utiliza model Modalidade.
 * Realiza insercao, atualizacao, exclusao e recuperacao
 * de Modalidades do banco de dados com consultas SQL.
 * 
 * @author Henrique Pato Magalhães
 * @version 1 26/04/23
 */
public class ModalidadeDAO extends DAO {
  /**
   * Construtor da classe: faz conexao com <code>DAO</code>
   */
  public ModalidadeDAO() {
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
   * Insere modalidade no banco de dados
   * 
   * @param modalidade <code>modalidade</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir inserir
   *         <code>false</code> se nao conseguir
   */
  public boolean create(Modalidade modalidade) {
    boolean status = false;

    try {
      String sql = "INSERT INTO Modalidade (ID, nome) VALUES (?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setObject(1, modalidade.getId());
      st.setString(2, modalidade.getNome());
      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Recupera modalidade do banco de dados pelo id
   * 
   * @param modalidade <code>int</code> identificador da modalidade
   * @return <code>Modalidade</code> recuperada
   */
  public Modalidade get(int id) {
    Modalidade modalidade = null;

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Modalidade WHERE ID= '"+id+"'";
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {
        
        modalidade = new Modalidade(rs.getInt("ID"), rs.getString("nome"));
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return modalidade;
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando orderBy nulo
   * 
   * @return <code>List<Modalidade></code> lista de modalidades nao ordenada
   */
  public List<Modalidade> get() {
    return getList("");
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando nome como orderBy
   * 
   * @return <code>List<nome></code> lista de modalidades ordenada pelo nome
   */
  public List<Modalidade> getOrderBynome() {
    return getList("nome");
  }

  /**
   * Ordena lista de modalidades pelo orderBy solicitado
   * 
   * @param orderBy <code>String</code> chave de ordenacao
   * @return <code>List<Modalidade></code> lista de modalidades
   */
  private List<Modalidade> getList(String orderBy) {
    List<Modalidade> modalidades = new ArrayList<Modalidade>();

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Modalidade" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {
        
        Modalidade m = new Modalidade(rs.getInt("ID"), rs.getString("nome"));

        modalidades.add(m);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return modalidades;
  }

  public Modalidade[] list(int id) {
    Modalidade[] modalidades = new Modalidade[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Modalidade WHERE ID=" + id;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
      
        modalidades[i] = new Modalidade(rs.getInt("ID"), rs.getString("nome"));

        i++;
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return modalidades;
  }

  /**
   * Atualiza modalidade no banco de dados
   * 
   * @param modalidade <code>Modalidade</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir atualizar
   *         <code>false</code> se nao conseguir
   */
  public boolean update(Modalidade modalidade) {
    boolean status = false;

    try {
      String sql = "UPDATE Modalidade nome=?, descrição=?, sigla=? WHERE ID =" + modalidade.getId();

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, modalidade.getNome());
      st.setString(2, modalidade.getDescricao());
      st.setString(3, modalidade.getSigla());


      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Deleta modalidade com nome passado do banco de dados
   * 
   * @param nome <code>String</code> identificador do modalidade
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir deletar
   *         <code>false</code> se nao conseguir
   */
  public boolean delete(int id) {
    boolean status = false;

    try {
      Statement st = conexao.createStatement();
      st.executeUpdate("DELETE FROM Modalidade WHERE ID = " + id);
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Busca modalidade a partir de um nome
   * 
   * @param nome <code>String</code> identificador do modalidade
   * @return <code>Modalidade</code> status
   */
  public Modalidade[] search(String query) {
    Modalidade[] modalidades = new Modalidade[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Modalidade WHERE ID=" + query;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        modalidades[i] = new Modalidade(rs.getInt("ID"), rs.getString("nome"));

        i++;
      }
      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return modalidades;
  }
}
