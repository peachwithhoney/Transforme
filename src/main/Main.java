package main;

import javax.swing.SwingUtilities;
import view.*;


public class Main {

	public static void main(String[] args) {
			
		// Usuario u = new Usuario();
		// u = UsuarioDAO.consultarUsuario(1);
		
		//Projeto proj = new Projeto();
		// proj = ProjetoDAO.consultarProjeto(2);
	
		
		// System.out.println(proj.metaFinanceira());
		
		SwingUtilities.invokeLater(() -> {
        	LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true); 
        });


		/*
		
		Projeto proj = new Projeto();
		
		
		proj = ProjetoDAO.consultarProjeto(2);
		
		
		
		
		proj.setNome("Teste3");
		proj.setDescricao("A fundable company");
		proj.setMetaFinanceira(new BigDecimal(400000.00));
		proj.setArrecadacao(new BigDecimal(00));
		proj.setDataCriacao(new Date(13, 2, 2023));
		ProjetoDAO.inserirProjeto(proj);
	
		
		ProjetoDAO.listaProjeto();
		*/
		
	
		
		
		/*
		Usuario u = new Usuario();
		
		u.setNome("teste1");
		u.setEmail("teste@gmail.com");
		u.setSenha("teste");
		
		UsuarioDAO.inserirUsuario(u);
		
		
		Usuario u = new Usuario();
		
		u = UsuarioDAO.consultarUsuario(2);
		
		
		UsuarioDAO.deletarUsuario(2);
		*/


	}
	
}

