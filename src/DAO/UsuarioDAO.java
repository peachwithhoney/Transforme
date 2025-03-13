package DAO;

import classes.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para inserir um novo usuário
    public static void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Falha ao inserir usuário.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    // Método para deletar um usuário
    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    // Método para atualizar um usuário
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
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    // Método para consultar um usuário por ID
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
            throw new RuntimeException("Erro ao consultar usuário: " + e.getMessage(), e);
        }
        return null;
    }

    // Método para autenticar um usuário
    public static Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                if (senhaHash.equals(hashSenha(senha))) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        email,
                        senhaHash
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }
        return null;
    }

    // Método para criptografar a senha
    private static String hashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }

    // Método para listar todos os usuários
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
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }
        return usuarios;
    }

    // Método para filtrar usuários por nome e email
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
            throw new RuntimeException("Erro ao filtrar usuários: " + e.getMessage(), e);
        }

        return listaUsuarios;
    }

    // Método para realizar uma doação
    public static boolean realizarDoacao(Usuario usuario, Projeto projeto, BigDecimal valor) {
        String sqlDoacao = "INSERT INTO doacao (id_usuario, id_projeto, valor, data) VALUES (?, ?, ?, ?)";
        String sqlAtualizacaoProjeto = "UPDATE projeto SET arrecadacao = arrecadacao + ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar()) {
            conexao.setAutoCommit(false);

            try (PreparedStatement stmtDoacao = conexao.prepareStatement(sqlDoacao);
                 PreparedStatement stmtAtualizacaoProjeto = conexao.prepareStatement(sqlAtualizacaoProjeto)) {

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
                    return true;
                } else {
                    conexao.rollback();
                    return false;
                }
            } catch (SQLException e) {
                conexao.rollback();
                System.err.println("Erro 500: Falha ao realizar doação e atualizar arrecadação - " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Falha na conexão ou transação - " + e.getMessage());
            return false;
        }
    }

    // Método para listar doações de um projeto
    public static List<String> listaDoacoesProjeto(int idProjeto) {
        List<String> listaDoacoes = new ArrayList<>();
        String sql = "SELECT u.nome AS usuario, u.email, d.valor, d.data, p.nome AS projeto " +
                     "FROM doacao d " +
                     "JOIN Usuario u ON d.id_usuario = u.id " +
                     "JOIN projeto p ON d.id_projeto = p.id " +
                     "WHERE p.id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nomeUsuario = rs.getString("usuario");
                String email = rs.getString("email");
                BigDecimal valor = rs.getBigDecimal("valor");
                Timestamp data = rs.getTimestamp("data");
                String nomeProjeto = rs.getString("projeto");

                String doacaoInfo = String.format(
                    "%s | %s | R$ %.2f | %s | %s",
                    nomeUsuario, email, valor, data, nomeProjeto
                );

                System.out.println(doacaoInfo);
                listaDoacoes.add(doacaoInfo);
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao listar doações - " + e.getMessage());
        }
        return listaDoacoes;
    }

    // Método para listar doações de um usuário
    public static List<String> listaDoacoesUsuario(int idUsuario) {
        List<String> listaDoacoes = new ArrayList<>();
        String sql = "SELECT u.nome AS usuario, u.email, d.valor, d.data, p.nome AS projeto " +
                     "FROM doacao d " +
                     "JOIN Usuario u ON d.id_usuario = u.id " +
                     "JOIN projeto p ON d.id_projeto = p.id " +
                     "WHERE u.id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nomeUsuario = rs.getString("usuario");
                String email = rs.getString("email");
                BigDecimal valor = rs.getBigDecimal("valor");
                Timestamp data = rs.getTimestamp("data");
                String nomeProjeto = rs.getString("projeto");

                String doacaoInfo = String.format(
                    "%s | %s | R$ %.2f | %s | %s",
                    nomeUsuario, email, valor, data, nomeProjeto
                );

                System.out.println(doacaoInfo);
                listaDoacoes.add(doacaoInfo);
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao listar doações - " + e.getMessage());
        }
        return listaDoacoes;
    }
}