package DAO;

import classes.*;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;



public class UsuarioDAO {

    
    public static void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            int index = stmt.executeUpdate();
            if (index > 0) {
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Falha ao inserir usuário.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    
    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int index = stmt.executeUpdate();

            if (index > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
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

            int index = stmt.executeUpdate();
            if (index > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
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
                    id,
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

    
    public static Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                if (senhaHash.equals(hashSenha(senha))) {
                    Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        email,
                        senhaHash
                    );
                    usuario.setLogado(true);
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }
        return null;
    }

    
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
    
    public static boolean realizarDoacao(Usuario usuario, Projeto projeto, BigDecimal valor) {
        String sql = "INSERT INTO doacao (id_usuario, id_projeto, valor, data) VALUES (?, ?, ?, ?)";
        
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, projeto.getId());
            stmt.setBigDecimal(3, valor);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro 500: Falha ao realizar doação - " + e.getMessage());
            return false;
        }
    }

    public static List<String> listaDoacoes(int idProjeto) {
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
}

