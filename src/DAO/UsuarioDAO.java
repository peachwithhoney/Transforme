package DAO;

import classes.Projeto;
import classes.Usuario;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    public static void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                logger.info("Usuário inserido com sucesso!");
            } else {
                logger.warning("Falha ao inserir usuário.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir usuário", e);
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                logger.info("Usuário deletado com sucesso!");
            } else {
                logger.warning("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar usuário", e);
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    public static void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                logger.info("Usuário atualizado com sucesso!");
            } else {
                logger.warning("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário", e);
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    public static Usuario consultarUsuario(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao consultar usuário", e);
            throw new RuntimeException("Erro ao consultar usuário: " + e.getMessage(), e);
        }
        return null;
    }

    public static Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao autenticar usuário", e);
        }
        return null;
    }

    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar usuários", e);
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }
        return usuarios;
    }

    public static List<Usuario> filtrarUsuarios(String nome, String email) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM usuario WHERE 1=1");

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
        }

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql.toString())) {

            int index = 1;
            if (nome != null && !nome.isEmpty()) {
                stmt.setString(index++, "%" + nome + "%");
            }
            if (email != null && !email.isEmpty()) {
                stmt.setString(index++, "%" + email + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao filtrar usuários", e);
            throw new RuntimeException("Erro ao filtrar usuários: " + e.getMessage(), e);
        }

        return listaUsuarios;
    }

    // Doação
    public static boolean realizarDoacao(Usuario usuario, Projeto projeto, BigDecimal valor) {
        String sqlDoacao = "INSERT INTO doacao (id_usuario, id_projeto, valor, data) VALUES (?, ?, ?, ?)";
        String sqlAtualizacaoProjeto = "UPDATE projeto SET arrecadacao = arrecadacao + ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar()) {
            conexao.setAutoCommit(false); 

            try (PreparedStatement stmtDoacao = conexao.prepareStatement(sqlDoacao);
                 PreparedStatement stmtAtualizacaoProjeto = conexao.prepareStatement(sqlAtualizacaoProjeto)) {

                // Insere a doação
                stmtDoacao.setInt(1, usuario.getId());
                stmtDoacao.setInt(2, projeto.getId());
                stmtDoacao.setBigDecimal(3, valor);
                stmtDoacao.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                int linhasDoacao = stmtDoacao.executeUpdate();

                
                stmtAtualizacaoProjeto.setBigDecimal(1, valor);
                stmtAtualizacaoProjeto.setInt(2, projeto.getId());
                int linhasProjeto = stmtAtualizacaoProjeto.executeUpdate();

                if (linhasDoacao > 0 && linhasProjeto > 0) {
                    conexao.commit(); 
                    logger.info("Doação realizada com sucesso!");
                    return true;
                } else {
                    conexao.rollback();
                    logger.warning("Falha ao realizar doação.");
                    return false;
                }
            } catch (SQLException e) {
                conexao.rollback(); 
                logger.log(Level.SEVERE, "Erro ao realizar doação", e);
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro na conexão ou transação", e);
            return false;
        }
    }
}