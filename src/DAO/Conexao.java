package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Conexao instancia;
    private Connection conexao;

    private Conexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                System.out.println("Conexão fechada. Reabrindo...");
            }
            String url = "jdbc:mysql://localhost:3306/db_transforme";
            String usuario = "root"; 
            String senha = "root"; 

            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static Conexao getInstancia() {
        if (instancia == null) {
            instancia = new Conexao();
        }
        return instancia;
    }

    public Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                System.out.println("Reconectando ao banco de dados...");
                String url = "jdbc:mysql://localhost:3306/db_transforme";
                String usuario = "root";
                String senha = "root";
    
                conexao = DriverManager.getConnection(url, usuario, senha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão: " + e.getMessage());
        }
        return conexao;
    }
    

    public void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}