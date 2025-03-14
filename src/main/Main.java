package main;

import classes.*;
import DAO.*;
import view.*;

import java.math.BigDecimal;
import java.sql.Date;

import javax.swing.SwingUtilities;



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

        */
        Projeto novoProjeto = new Projeto();
        novoProjeto.setNome("Daredevil");
        novoProjeto.setDescricao("A blind company");
        novoProjeto.setMetaFinanceira(new BigDecimal("400000.00"));
        novoProjeto.setArrecadacao(new BigDecimal("0.00"));
        novoProjeto.setDataCriacao(new Date(13, 2, 2023)); 
        ProjetoDAO.inserirProjeto(novoProjeto);
        
        /*
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