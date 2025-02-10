package DAO;

import java.sql.*;

import classes.*;

public class UsuarioDAO {

    // ##### Requisito 1 - 10/02/2024 #####
    public static void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement resultado = conexao.prepareStatement(sql)) {

            resultado.setString(1, usuario.getNome());
            resultado.setString(2, usuario.getEmail());
            resultado.setString(3, usuario.getSenha());

            int index = resultado.executeUpdate();

            if (index > 0) {
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Falha ao inserir usuário.");
            }

        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao inserir usuário: " + e.getMessage());
        }
    }

    // ##### Requisito 1 - 10/02/2024 #####
    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement resultado = conexao.prepareStatement(sql)) {

            resultado.setInt(1, id);

            int index = resultado.executeUpdate();

            if (index > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado ou falha ao deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao deletar usuário: " + e.getMessage());
        }
    }

    // ##### Requisito 1 - 10/02/2024 #####
    public static void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement resultado = conexao.prepareStatement(sql)) {

            resultado.setString(1, usuario.getNome());
            resultado.setString(2, usuario.getEmail());
            resultado.setString(3, usuario.getSenha());
            resultado.setInt(4, usuario.getId());

            int index = resultado.executeUpdate();

            if (index > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado ou falha ao atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao atualizar usuário: " + e.getMessage());
        }
    }
    
    // ##### Requisito 1 - 10/02/2024 #####
    public static Usuario consultarUsuario(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement resultado = conexao.prepareStatement(sql)) {

            resultado.setInt(1, id);
            ResultSet rs = resultado.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                return new Usuario(id, nome, email, senha);
            } else {
                System.out.println("Usuário não encontrado.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao consultar usuário: " + e.getMessage());
            return null;
        }
    }
    
    
    //##### Requisito 2 - 10/02/2024 #####
    public static Usuario autenticarUsuario(Usuario user) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement resultado = conexao.prepareStatement(sql)) {

            resultado.setString(1, user.getEmail());
            resultado.setString(2, user.getSenha());
            ResultSet rs = resultado.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String emailDb = rs.getString("email");
                String senhaDb = rs.getString("senha");
                // Cria o usuário e marca como "logado"
                Usuario usuario = new Usuario(id, nome, emailDb, senhaDb);
                usuario.setLogado(true); // Definir o usuário como logado
                return usuario;
            } else {
                System.out.println("E-mail ou senha incorretos.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
            return null;
        }
    }
    
}
