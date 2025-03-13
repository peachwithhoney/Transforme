package DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Método para conectar ao banco de dados
    public static Connection conectar() {
        Connection conexao = null;
        try {
            
            String url = "jdbc:mysql://localhost:3306/db_transforme";
            String usuario = "root"; 
            String senha = "root"; 

            
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    
    public static void fecharConexao(Connection conexao) {
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
