package DAO;

import classes.Doacao;
import exceptions.CampoObrigatorioException;
import exceptions.ProjetoNaoEncontradoException;
import exceptions.UsuarioNaoEncontradoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO {

    
    public static void registrarDoacao(Doacao doacao) throws CampoObrigatorioException, UsuarioNaoEncontradoException, ProjetoNaoEncontradoException {
        
        if (doacao.getValor() <= 0) {
            throw new CampoObrigatorioException("O valor da doação deve ser maior que zero.");
        }
        if (doacao.getIdUsuario() <= 0) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório.");
        }
        if (doacao.getIdProjeto() <= 0) {
            throw new CampoObrigatorioException("ID do projeto é obrigatório.");
        }

        
        if (!UsuarioDAO.existeUsuario(doacao.getIdUsuario())) {
            throw new UsuarioNaoEncontradoException("Usuário com ID " + doacao.getIdUsuario() + " não encontrado.");
        }
        if (!ProjetoDAO.existeProjeto(doacao.getIdProjeto())) {
            throw new ProjetoNaoEncontradoException("Projeto com ID " + doacao.getIdProjeto() + " não encontrado.");
        }

        String sql = "INSERT INTO Doacao (valor, id_usuario, id_projeto) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, doacao.getValor());
            stmt.setInt(2, doacao.getIdUsuario());
            stmt.setInt(3, doacao.getIdProjeto());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Doação registrada com sucesso!");
            } else {
                throw new RuntimeException("Falha ao registrar doação.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar doação no banco de dados: " + e.getMessage(), e);
        }
    }

    
    public static List<Doacao> listarDoacoes() {
        List<Doacao> doacoes = new ArrayList<>();
        String sql = "SELECT * FROM Doacao";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
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
            throw new RuntimeException("Erro ao listar doações: " + e.getMessage(), e);
        }
        return doacoes;
    }

    public static List<Doacao> listarDoacoesPorProjeto(int idProjeto) throws ProjetoNaoEncontradoException {
        if (!ProjetoDAO.existeProjeto(idProjeto)) {
            throw new ProjetoNaoEncontradoException("Projeto com ID " + idProjeto + " não encontrado.");
        }

        List<Doacao> doacoesProjeto = new ArrayList<>();
        String sql = "SELECT * FROM Doacao WHERE id_projeto = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar doações por projeto: " + e.getMessage(), e);
        }
        return doacoesProjeto;
    }

    public static double getTotalArrecadado(int idProjeto) throws ProjetoNaoEncontradoException {
        if (!ProjetoDAO.existeProjeto(idProjeto)) {
            throw new ProjetoNaoEncontradoException("Projeto com ID " + idProjeto + " não encontrado.");
        }

        double total = 0;
        String sql = "SELECT SUM(valor) AS total FROM Doacao WHERE id_projeto = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter total arrecadado: " + e.getMessage(), e);
        }
        return total;
    }

    public static boolean existeDoacao(int idDoacao) {
        String sql = "SELECT COUNT(*) FROM Doacao WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idDoacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência da doação: " + e.getMessage(), e);
        }
        return false;
    }
}