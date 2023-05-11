package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

/**
 * UsuarioDAO: herda DAO e utiliza model Usuario.
 * Realiza insercao, atualizacao, exclusao e recuperacao
 * de usuarios do banco de dados com consultas SQL.
 * 
 * @author Henrique Pato Magalhães
 * @version 1 20/04/23
 */
public class UsuarioDAO extends DAO {
  /**
   * Construtor da classe: faz conexao com <code>DAO</code>
   */
  public UsuarioDAO() {
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
   * Insere usuario no banco de dados
   * 
   * @param usuario <code>Usuario</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir inserir
   *         <code>false</code> se nao conseguir
   */
  public boolean create(Usuario usuario) {
    boolean status = false;

    try {
      String sql = "INSERT INTO Usuario (ID nome, senha, email) VALUES (?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setObject(1, usuario.getId());
      st.setString(2, usuario.getNome());
      st.setBytes(3, usuario.getHashedPassword());
      st.setString(4, usuario.getEmail());
      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Recupera usuario do banco de dados pelo id
   * 
   * @param username <code>int</code> identificador do usuario
   * @return <code>Usuario</code> recuperada
   */
  public Usuario get(int id) {
    Usuario usuario = null;

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Usuario WHERE ID= '"+id+"'";
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {
        
        usuario = new Usuario(rs.getInt("ID"), rs.getString("nome"), rs.getString("email"), rs.getBytes("senha"));
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return usuario;
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando orderBy nulo
   * 
   * @return <code>List<Usuario></code> lista de usuarios nao ordenada
   */
  public List<Usuario> get() {
    return getList("");
  }

  /**
   * Chama funcao <code>get(String orderBy)</code>
   * passando nome como orderBy
   * 
   * @return <code>List<nome></code> lista de usuarios ordenada pelo nome
   */
  public List<Usuario> getOrderBynome() {
    return getList("nome");
  }

  /**
   * Ordena lista de usuarios pelo orderBy solicitado
   * 
   * @param orderBy <code>String</code> chave de ordenacao
   * @return <code>List<Usuario></code> lista de usuarios
   */
  private List<Usuario> getList(String orderBy) {
    List<Usuario> usuarios = new ArrayList<Usuario>();

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {
        
        Usuario u = new Usuario(rs.getInt("ID"), rs.getString("nome"), rs.getString("email"), rs.getBytes("senha"));

        usuarios.add(u);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return usuarios;
  }

  public Usuario[] list(int id) {
    Usuario[] usuarios = new Usuario[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Usuario WHERE ID=" + id;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        
        usuarios[i] = new Usuario(rs.getInt("ID"), rs.getString("nome"), rs.getString("email"), rs.getBytes("senha"));

        i++;
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return usuarios;
  }

  /**
   * Atualiza usuario no banco de dados
   * 
   * @param usuario <code>Usuario</code> a ser inserida
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir atualizar
   *         <code>false</code> se nao conseguir
   */
  public boolean update(Usuario usuario) {
    boolean status = false;

    try {
      String sql = "UPDATE Usuario SET email=?, senha=" + usuario.getHashedPassword()
          + " , sobrenome=?, descrição=?, nascimento=?, idade=?, foto=? WHERE nome =" + usuario.getNome();

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, usuario.getEmail());
      st.setString(2, usuario.getSobrenome());
      st.setString(3, usuario.getDescricao());
      st.setObject(4, usuario.getNascimento());
      st.setInt(5, usuario.getIdade());
      st.setObject(6, usuario.getFoto());
      st.executeUpdate();
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Deleta usuario com nome passado do banco de dados
   * 
   * @param nome <code>String</code> identificador do usuario
   * @return <code>boolean</code> status
   *         <code>true</code> se conseguir deletar
   *         <code>false</code> se nao conseguir
   */
  public boolean delete(int id) {
    boolean status = false;

    try {
      Statement st = conexao.createStatement();
      st.executeUpdate("DELETE FROM Usuario WHERE ID = " + id);
      st.close();

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }

  /**
   * Busca usuario a partir de um nome
   * 
   * @param nome <code>String</code> identificador do usuario
   * @return <code>Usuario</code> status
   */
  public Usuario[] search(String query) {
    Usuario[] usuarios = new Usuario[100];

    try {
      Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT * FROM Usuario WHERE nome=" + query;
      ResultSet rs = st.executeQuery(sql);

      int i = 0;
      while (rs.next()) {
        usuarios[i] = new Usuario(rs.getInt("ID"), rs.getString("nome"), rs.getString("email"), rs.getBytes("senha"));

        i++;
      }
      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return usuarios;
  }
}