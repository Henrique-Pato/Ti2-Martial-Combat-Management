package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Reacao;

/**
 * ReacaoDAO: herda DAO e utiliza model Reacao.
 * Realiza insercao, atualizacao, exclusao e recuperacao
 * de Reacao do banco de dados com consultas SQL.
 * 
 * @author Henrique Pato Magalhães
 * @version 1 26/04/23
 */
public class ReacaoDAO extends DAO {
  /**
   * Construtor da classe: faz conexao com <code>DAO</code>
   */
  public ReacaoDAO() {
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
   * Insere reacao no banco de dados
   * 
   * @param reacao <code>reacao</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir inserir
   *         <code>false</code> se nao conseguir
   */
  public boolean create(Reacao reacao) {
    boolean status = false;

    try {
      String sql = "INSERT INTO Reacao (postagemID usuarioID reacao) VALUES (?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setObject(1, reacao.getPostagemID());
      st.setObject(2, reacao.getUsuarioID());
      st.setString(3, reacao.getReacao());
      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Recupera reacao do banco de dados pelo id
   * 
   * @param reacao <code>UUID</code> identificador da Reacao
   * @return <code>reacao</code> recuperada
   */
  public Reacao get(UUID id) {
    Reacao reacao = null;

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Reacao WHERE usuarioID= '"+id+"'";
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {
        UUID _Uid = (UUID) rs.getObject("usuarioID");
        UUID _Pid = (UUID) rs.getObject("postagemID");
        reacao = new Reacao(_Pid, _Uid);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return reacao;
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando orderBy nulo
   * 
   * @return <code>List<Reacao></code> lista de reacoes nao ordenada
   */
  public List<Reacao> get() {
    return getList("");
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando reacao como orderBy
   * 
   * @return <code>List<reacao></code> lista de reacoes ordenada pela reacao
   */
  public List<Reacao> getOrderByReacao() {
    return getList("reacao");
  }

  /**
   * Ordena lista de reacoes pelo orderBy solicitado
   * 
   * @param orderBy <code>String</code> chave de ordenacao
   * @return <code>List<Reacao></code> lista de reacoes
   */
  private List<Reacao> getList(String orderBy) {
    List<Reacao> reacao = new ArrayList<Reacao>();

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Reacao" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {
        UUID _Uid = (UUID) rs.getObject("usuarioID");
        UUID _Pid = (UUID) rs.getObject("postagemID");
        Reacao m = new Reacao(_Pid, _Uid);

        reacao.add(m);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return reacao;
  }

  public Reacao[] list(UUID id) {
    Reacao[] reacao = new Reacao[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Reacao WHERE usuarioID=" + id;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        UUID _Uid = (UUID) rs.getObject("usuarioID");
        UUID _Pid = (UUID) rs.getObject("postagemID");
        reacao[i] = new Reacao(_Pid, _Uid);

        i++;
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return reacao;
  }

  /**
   * Atualiza reacao no banco de dados
   * 
   * @param reacao <code>Reacao</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir atualizar
   *         <code>false</code> se nao conseguir
   */
  public boolean update(UUID id, Reacao reacao) {
    boolean status = false;

    try {
      String sql = "UPDATE Reacao reacao=? WHERE usuarioID =" + id;

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, reacao.getReacao());


      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Deleta reacao com usuarioID passado do banco de dados
   * 
   * @param nome <code>UUID</code> identificador do reacao
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir deletar
   *         <code>false</code> se nao conseguir
   */
  public boolean delete(UUID id) {
    boolean status = false;

    try {
      Statement st = conexao.createStatement();
      st.executeUpdate("DELETE FROM Reacao WHERE usuarioID = " + id);
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Busca reacao a partir de uma reacao
   * 
   * @param reacao <code>String</code> identificador do reacao
   * @return <code>Reacao</code> status
   */
  public Reacao[] search(String query) {
    Reacao[] reacoes = new Reacao[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Reacao WHERE reacao=" + query;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        UUID _Uid = (UUID) rs.getObject("usuarioID");
        UUID _Pid = (UUID) rs.getObject("postagemID");
        reacoes[i] = new Reacao(_Pid, _Uid);


        i++;
      }
      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return reacoes;
  }
}