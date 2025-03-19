package DAO;

import classes.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.EmailJaCadastradoException;
import exceptions.SenhaInvalidaException;
import exceptions.UsuarioNaoEncontradoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    
    public static void inserirUsuario(Usuario usuario) throws CampoObrigatorioException, EmailJaCadastradoException {
       
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'nome' é obrigatório.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'email' é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'senha' é obrigatório.");
        }

        
        if (emailJaCadastrado(usuario.getEmail())) {
            throw new EmailJaCadastradoException("Este email já está cadastrado.");
        }

        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.getInstancia().getConexao();
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
            throw new RuntimeException("Erro ao inserir usuário no banco de dados.", e);
        }
    }

    
    public static Usuario consultarUsuario(int id) throws UsuarioNaoEncontradoException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
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
                    throw new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao consultar usuário: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao consultar usuário no banco de dados.", e);
        }
    }

   
    public static List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conexao = Conexao.getInstancia().getConexao();
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
            throw new RuntimeException("Erro ao listar usuários no banco de dados.", e);
        }
        return listaUsuarios;
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

        try (Connection conexao = Conexao.getInstancia().getConexao();
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
            throw new RuntimeException("Erro ao filtrar usuários no banco de dados.", e);
        }
        return listaUsuarios;
    }

    
    public static void atualizarUsuario(Usuario usuario) throws CampoObrigatorioException, UsuarioNaoEncontradoException {
        
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'nome' é obrigatório.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'email' é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'senha' é obrigatório.");
        }

        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                logger.info("Usuário atualizado com sucesso!");
            } else {
                throw new UsuarioNaoEncontradoException("Usuário com ID " + usuario.getId() + " não encontrado.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar usuário no banco de dados.", e);
        }
    }

    
    public static void deletarUsuario(int id) throws UsuarioNaoEncontradoException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                logger.info("Usuário deletado com sucesso!");
            } else {
                throw new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar usuário: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao deletar usuário no banco de dados.", e);
        }
    }

    
    public static Usuario autenticarUsuario(String email, String senha) throws UsuarioNaoEncontradoException, SenhaInvalidaException {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
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
                } else {
                    throw new UsuarioNaoEncontradoException("Usuário não encontrado com as credenciais fornecidas.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao autenticar usuário: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao autenticar usuário no banco de dados.", e);
        }
    }

    public static boolean existeUsuario(int id_usuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
    
            stmt.setInt(1, id_usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do usuário: " + e.getMessage(), e);
        }
        return false;
    }
    
    private static boolean emailJaCadastrado(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao verificar email: " + e.getMessage(), e);
        }
        return false;
    }
}
