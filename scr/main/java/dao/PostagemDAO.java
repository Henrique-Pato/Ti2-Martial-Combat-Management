package dao;

import model.Postagem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * PostagemDAO: herda DAO e utiliza model Postagem.
 * Realiza insercao, atualizacao, exclusao e recuperacao
 * de postagens do banco de dados com consultas SQL.
 * 
 * @author Henrique Pato Magalhães
 * @version 1 26/04/23
 */
public class PostagemDAO extends DAO {
    /**
     * Construtor da classe: faz conexao com <code>DAO</code>
     */
    public PostagemDAO() {
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
     * Insere postagem no banco de dados
     * 
     * @param postagem <code>Postagem</code> a ser inserida
     * @return <code>boolean</code> status
     *         <code>true</code> se conseguir inserir
     *         <code>false</code> se nao conseguir
     */
    public boolean insert(Postagem postagem) {
        boolean status = false;

        try {
            String sql = "INSERT INTO Postagem (ID, conteudo, foto, modalidadeID, data, usuarioID) VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement st = conexao.prepareStatement(sql);
            st.setObject(1, postagem.getId());
            st.setString(2, postagem.getConteudo());
            st.setString(3, postagem.getFoto());
            st.setObject(4, postagem.getModalidadeID());
            st.setDate(5, postagem.getDate());
            st.setObject(6, postagem.getUsuarioID());
            st.executeUpdate();
            st.close();

            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    /**
     * Recupera postagem do banco de dados pelo id
     * 
     * @param id <code>UUID</code> identificador da postagem
     * @return <code>Postagem</code> recuperada
     */
    public Postagem get(UUID id) {
        Postagem postagem = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM Postagem WHERE ID=" + id;
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                UUID _id = (UUID) rs.getObject("ID");
                UUID _modalidadeID = (UUID) rs.getObject("modalidadeID");
                UUID _usuarioId = (UUID) rs.getObject("usuarioID");

                postagem = new Postagem(_id, rs.getString("conteudo"), rs.getString("foto"), _modalidadeID, rs.getDate("data"), _usuarioId);
            }

            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return postagem;
    }

    /**
     * Chama funcao <code>get(String orderBy)</code>
     * passando orderBy nulo
     * 
     * @return <code>List<Postagem></code> lista de postagens nao ordenada
     */
    public List<Postagem> get() {
        return get("");
    }

    /**
     * Chama funcao <code>get(String orderBy)</code>
     * passando id como orderBy
     * 
     * @return <code>List<Postagem></code> lista de postagens ordenada pelo id
     */
    public List<Postagem> getOrderByID() {
        return get("ID");
    }

    /**
     * Ordena lista de postagens pelo orderBy solicitado
     * 
     * @param orderBy <code>String</code> chave de ordenacao
     * @return <code>List<Postagem></code> lista de postagens
     */
    private List<Postagem> get(String orderBy) {
        List<Postagem> postagens = new ArrayList<Postagem>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM Postagem" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                UUID _id = (UUID) rs.getObject("ID");
                UUID _modalidadeID = (UUID) rs.getObject("modalidadeID");
                UUID _usuarioId = (UUID) rs.getObject("usuarioID");

                Postagem p = new Postagem(_id, rs.getString("conteudo"), rs.getString("foto"), _modalidadeID, rs.getDate("data"), _usuarioId);

                postagens.add(p);
            }

            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return postagens;
    }

    public Postagem[] list(UUID id) {
        Postagem[] postagens = new Postagem[100];

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM Postagem WHERE ID=" + id;
            ResultSet rs = st.executeQuery(sql);

            int i = 0;
            while (rs.next()) {
                UUID _id = (UUID) rs.getObject("ID");
                UUID _modalidadeID = (UUID) rs.getObject("modalidadeID");
                UUID _usuarioId = (UUID) rs.getObject("usuarioID");

                postagens[i] = new Postagem(_id, rs.getString("conteudo"), rs.getString("foto"), _modalidadeID, rs.getDate("data"), _usuarioId);

                i++;
            }

            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return postagens;
    }

    /**
     * Atualiza postagem no banco de dados
     * 
     * @param postagem <code>Postagem</code> a ser inserida
     * @return <code>boolean</code> status
     *         <code>true</code> se conseguir atualizar
     *         <code>false</code> se nao conseguir
     */
    public boolean update(Postagem postagem) {
        boolean status = false;

        try {
            String sql = "UPDATE Postagem SET conteudo=?, foto=?, modalidadeID=?, data=?, usuarioID=?";

            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, postagem.getConteudo());
            st.setString(2, postagem.getFoto());
            st.setObject(3, postagem.getModalidadeID());
            st.setDate(4, postagem.getDate());
            st.setObject(5, postagem.getUsuarioID());
            st.executeUpdate();
            st.close();

            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    /**
     * Deleta postagem com id passado do banco de dados
     * 
     * @param id <code>UUID</code> identificador da postagem
     * @return <code>boolean</code> status
     *         <code>true</code> se conseguir deletar
     *         <code>false</code> se nao conseguir
     */
    public boolean delete(UUID id) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM Postagem WHERE ID = " + id);
            st.close();

            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }
}