package DAO;

import classes.Projeto;
import exceptions.CampoObrigatorioException;
import exceptions.ProjetoNaoEncontradoException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    
    public static void inserirProjeto(Projeto projeto) throws CampoObrigatorioException {
      
        if (projeto.getNome() == null || projeto.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'nome' é obrigatório.");
        }
        if (projeto.getMetaFinanceira() == null || projeto.getMetaFinanceira().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CampoObrigatorioException("O campo 'metaFinanceira' é obrigatório e deve ser maior que zero.");
        }

        String sql = "INSERT INTO projeto (nome, descricao, meta_financeira, arrecadacao, data_criacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setBigDecimal(3, projeto.getMetaFinanceira());
            stmt.setBigDecimal(4, projeto.getArrecadacao());
            stmt.setTimestamp(5, Timestamp.valueOf(projeto.getDataCriacao()));

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projeto.setId(generatedKeys.getInt(1));
                }
            }

            System.out.println("Projeto inserido com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir projeto: " + e.getMessage(), e);
        }
    }

    
    public static Projeto consultarProjeto(int id) throws ProjetoNaoEncontradoException {
        String sql = "SELECT * FROM projeto WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
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
                        rs.getTimestamp("data_criacao").toLocalDateTime()
                    );
                } else {
                    throw new ProjetoNaoEncontradoException("Projeto com ID " + id + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar projeto: " + e.getMessage(), e);
        }
    }

   
    public static List<Projeto> listaProjeto() {
        List<Projeto> listaProjetos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, meta_financeira, arrecadacao, data_criacao FROM projeto";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaProjetos.add(new Projeto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("meta_financeira"),
                    rs.getBigDecimal("arrecadacao"),
                    rs.getTimestamp("data_criacao").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar projetos: " + e.getMessage(), e);
        }
        return listaProjetos;
    }

    
    public static List<Projeto> filtrarProjetos(String nome, String descricao, String meta) {
        List<Projeto> listaProjetos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, nome, descricao, meta_financeira, arrecadacao, data_criacao FROM projeto WHERE 1=1");

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
        }
        if (descricao != null && !descricao.isEmpty()) {
            sql.append(" AND descricao LIKE ?");
        }
        if (meta != null && !meta.isEmpty()) {
            sql.append(" AND meta_financeira >= ?");
        }

        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql.toString())) {

            int index = 1;

            if (nome != null && !nome.isEmpty()) {
                stmt.setString(index++, "%" + nome + "%");
            }
            if (descricao != null && !descricao.isEmpty()) {
                stmt.setString(index++, "%" + descricao + "%");
            }
            if (meta != null && !meta.isEmpty()) {
                stmt.setBigDecimal(index++, new BigDecimal(meta));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaProjetos.add(new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("meta_financeira"),
                        rs.getBigDecimal("arrecadacao"),
                        rs.getTimestamp("data_criacao").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao filtrar projetos: " + e.getMessage(), e);
        }

        return listaProjetos;
    }

    
    public static void atualizarProjeto(Projeto projeto) throws CampoObrigatorioException, ProjetoNaoEncontradoException {
        
        if (projeto.getNome() == null || projeto.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("O campo 'nome' é obrigatório.");
        }
        if (projeto.getMetaFinanceira() == null || projeto.getMetaFinanceira().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CampoObrigatorioException("O campo 'metaFinanceira' é obrigatório e deve ser maior que zero.");
        }

        String sql = "UPDATE projeto SET nome = ?, descricao = ?, meta_financeira = ?, arrecadacao = ?, data_criacao = ? WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setBigDecimal(3, projeto.getMetaFinanceira());
            stmt.setBigDecimal(4, projeto.getArrecadacao());
            stmt.setTimestamp(5, Timestamp.valueOf(projeto.getDataCriacao()));
            stmt.setInt(6, projeto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Projeto atualizado com sucesso!");
            } else {
                throw new ProjetoNaoEncontradoException("Projeto com ID " + projeto.getId() + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar projeto: " + e.getMessage(), e);
        }
    }

    public static void deletarProjeto(int id) throws ProjetoNaoEncontradoException {
        deletarDoacoesDoProjeto(id);

        String sql = "DELETE FROM projeto WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Projeto deletado com sucesso!");
            } else {
                throw new ProjetoNaoEncontradoException("Projeto com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar projeto: " + e.getMessage(), e);
        }
    }

    public static boolean existeProjeto(int id_projeto) {
        String sql = "SELECT COUNT(*) FROM projeto WHERE id = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
    
            stmt.setInt(1, id_projeto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do projeto: " + e.getMessage(), e);
        }
        return false;
    }

    private static void deletarDoacoesDoProjeto(int idProjeto) {
        String sql = "DELETE FROM doacao WHERE id_projeto = ?";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            stmt.executeUpdate();
            System.out.println("Doações do projeto deletadas com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar doações do projeto: " + e.getMessage(), e);
        }
    }

    public static BigDecimal calculaTotalArrecadado(Projeto proj, int meses) {
        String sql = "SELECT SUM(valor) FROM doacao WHERE id_projeto = ? AND data_criacao BETWEEN DATE_SUB(NOW(), INTERVAL ? MONTH) AND NOW()";
        try (Connection conexao = Conexao.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, proj.getId());
            stmt.setInt(2, meses);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : BigDecimal.ZERO;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total arrecadado: " + e.getMessage(), e);
        }
        return BigDecimal.ZERO;
    }
}