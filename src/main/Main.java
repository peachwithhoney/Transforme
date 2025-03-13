package main;

import javax.swing.SwingUtilities;
import view.LoginScreen;

public class Main {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });

        // 
        /*
        // 
        Usuario u = UsuarioDAO.consultarUsuario(1);
        if (u != null) {
            System.out.println("Usuário encontrado: " + u.getNome());
        } else {
            System.out.println("Usuário não encontrado.");
        }

        // 
        Projeto proj = ProjetoDAO.consultarProjeto(2);
        if (proj != null) {
            System.out.println("Projeto encontrado: " + proj.getNome());
            System.out.println("Meta financeira: " + proj.metaFinanceira());
        } else {
            System.out.println("Projeto não encontrado.");
        }

        // 
        Projeto novoProjeto = new Projeto();
        novoProjeto.setNome("Teste3");
        novoProjeto.setDescricao("A fundable company");
        novoProjeto.setMetaFinanceira(new BigDecimal("400000.00"));
        novoProjeto.setArrecadacao(new BigDecimal("0.00"));
        novoProjeto.setDataCriacao(new Date(13, 2, 2023)); // Ajuste a data conforme necessário
        ProjetoDAO.inserirProjeto(novoProjeto);

        // 
        ProjetoDAO.listaProjeto();

        // 
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("teste1");
        novoUsuario.setEmail("teste@gmail.com");
        novoUsuario.setSenha("teste");
        UsuarioDAO.inserirUsuario(novoUsuario);

        // 
        UsuarioDAO.deletarUsuario(2);
        */
    }
}