package DAO;

import classes.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {
    
    public static void inserirProjeto(Projeto projeto) {
        String sql = "INSERT INTO projeto (nome, descricao, meta_financeira, arrecadacao, imagem, data_criacao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setBigDecimal(3, projeto.getMetaFinanceira());
            stmt.setBigDecimal(4, projeto.getArrecadacao());
            stmt.setString(5, projeto.getImagem());
            stmt.setTimestamp(6, new Timestamp(projeto.getDataCriacao().getTime()));
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projeto.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Projeto inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao inserir projeto: " + e.getMessage());
        }
    }
    
    public static Projeto consultarProjeto(int id) {
        String sql = "SELECT * FROM projeto WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("meta_financeira"),
                        rs.getBigDecimal("arrecadacao"),
                        rs.getString("imagem"),
                        rs.getTimestamp("data_criacao")
                    );
                }
                else {
                	System.out.println("Projeto não encontrado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao consultar projeto: " + e.getMessage());
        }
        return null;
    }
    
    public static List<Projeto> listaProjeto() {
        List<Projeto> listaProjetos = new ArrayList<>();
        String sql = "SELECT * FROM projeto";
        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaProjetos.add(new Projeto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("meta_financeira"),
                    rs.getBigDecimal("arrecadacao"),
                    rs.getString("imagem"),
                    rs.getTimestamp("data_criacao")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao listar projetos: " + e.getMessage());
        }
        return listaProjetos;
    }
    
    public static void atualizarProjeto(Projeto projeto) {
        String sql = "UPDATE projeto SET nome = ?, descricao = ?, meta_financeira = ?, arrecadacao = ?, imagem = ?, data_criacao = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setBigDecimal(3, projeto.getMetaFinanceira());
            stmt.setBigDecimal(4, projeto.getArrecadacao());
            stmt.setString(5, projeto.getImagem());
            stmt.setTimestamp(6, new Timestamp(projeto.getDataCriacao().getTime()));
            stmt.setInt(7, projeto.getId());
            
            int index = stmt.executeUpdate();
            if (index > 0) {
                System.out.println("Projeto atualizado com sucesso!");
            } else {
                System.out.println("Projeto não encontrado ou falha ao atualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao atualizar projeto: " + e.getMessage());
        }
    }
    
    public static void deletarProjeto(int id) {
        String sql = "DELETE FROM projeto WHERE id = ?";
        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int index = stmt.executeUpdate();
            if (index > 0) {
                System.out.println("Projeto deletado com sucesso!");
            } else {
                System.out.println("Projeto não encontrado ou falha ao deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro 500: Erro ao deletar projeto: " + e.getMessage());
        }
    }
}
