package DAO;

import classes.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    // Inserir um novo usuário
    public static void inserirUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            logger.warning("Erro: O campo 'nome' é obrigatório.");
            return;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            logger.warning("Erro: O campo 'email' é obrigatório.");
            return;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            logger.warning("Erro: O campo 'senha' é obrigatório.");
            return;
        }

        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }

            logger.info("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    // Consultar um usuário por ID
    public static Usuario consultarUsuario(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                } else {
                    logger.info("Usuário não encontrado.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao consultar usuário: " + e.getMessage(), e);
        }
        return null;
    }

    // Listar todos os usuários
    public static List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar usuários: " + e.getMessage(), e);
        }
        return listaUsuarios;
    }

    // Filtrar usuários por nome e/ou email
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

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaUsuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao filtrar usuários: " + e.getMessage(), e);
        }
        return listaUsuarios;
    }

    // Atualizar um usuário
    public static void atualizarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            logger.warning("Erro: O campo 'nome' é obrigatório.");
            return;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            logger.warning("Erro: O campo 'email' é obrigatório.");
            return;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            logger.warning("Erro: O campo 'senha' é obrigatório.");
            return;
        }

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
                logger.warning("Usuário não encontrado ou falha ao atualizar.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    // Deletar um usuário por ID
    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                logger.info("Usuário deletado com sucesso!");
            } else {
                logger.warning("Usuário não encontrado ou falha ao deletar.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    // Autenticar um usuário por email e senha
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
            logger.log(Level.SEVERE, "Erro ao autenticar usuário: " + e.getMessage(), e);
        }
        return null;
    }
}