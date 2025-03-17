package DAO;

import classes.Doacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO {

    public static boolean registrarDoacao(Doacao doacao) {
        Connection conexao = Conexao.conectar();
        if (conexao == null) {
            return false;
        }
        
        String sql = "INSERT INTO Doacao (valor, id_usuario, id_projeto) VALUES (?, ?, ?)";
    
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDouble(1, doacao.getValor());
            stmt.setInt(2, doacao.getIdUsuario());
            stmt.setInt(3, doacao.getIdProjeto());
    
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
    
        } catch (SQLException e) {
            System.err.println("Erro ao registrar doação no banco: " + e.getMessage());
            e.printStackTrace(); 
            return false;
        } finally {
            Conexao.fecharConexao(conexao); 
        }
    }

    public static List<Doacao> listarDoacoes() {
        List<Doacao> doacoes = new ArrayList<>();
        Connection conexao = Conexao.conectar();
        if (conexao == null) {
            return doacoes;
        }

        String sql = "SELECT * FROM Doacao"; 
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doacao doacao = new Doacao(
                    rs.getInt("id"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data"), 
                    rs.getInt("id_usuario"),  
                    rs.getInt("id_projeto")   
                );
                doacoes.add(doacao);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar doações: " + e.getMessage());

        } finally {
            Conexao.fecharConexao(conexao);
        }
        return doacoes;
    }

    public static List<Doacao> listarDoacoesPorProjeto(int id_projeto) {
        List<Doacao> doacoesProjeto = new ArrayList<>();
        Connection conexao = Conexao.conectar();
        if (conexao == null) {
            return doacoesProjeto;
        }

        String sql = "SELECT * FROM Doacao WHERE id_projeto = ?"; 

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id_projeto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Doacao doacao = new Doacao(
                    rs.getInt("id"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data"), 
                    rs.getInt("id_usuario"),  
                    rs.getInt("id_projeto")   
                );
                doacoesProjeto.add(doacao);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar doações por projeto: " + e.getMessage());

        } finally {
            Conexao.fecharConexao(conexao);
        }
        return doacoesProjeto;
    }

    public static double getTotalArrecadado(int id_projeto) {
        double total = 0;
        Connection conexao = Conexao.conectar();
        if (conexao == null) {
            return total;
        }
    
        String sql = "SELECT SUM(valor) AS total FROM Doacao WHERE id_projeto = ?"; 
    
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id_projeto);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                total = rs.getDouble("total");
            }
    
        } catch (SQLException e) {
            System.err.println("Erro ao obter total arrecadado: " + e.getMessage());
    
        } finally {
            Conexao.fecharConexao(conexao);
        }
        return total;
    }
}