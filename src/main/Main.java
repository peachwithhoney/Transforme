package main;

import classes.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import DAO.*;


public class Main {

	public static void main(String[] args) {
	/*	
		
		proj.setNome("Fisk");
		proj.setDescricao("A non fundable company");
		proj.setMetaFinanceira(new BigDecimal(25000000.00));
		proj.setArrecadacao(new BigDecimal(00));
		proj.setImagem("20231019_195057_2");
		proj.setDataCriacao(new Date(13, 2, 2023));
		ProjetoDAO.inserirProjeto(proj);
	
	
		*/
		
		
		
		Usuario u = new Usuario();
		
		u.setNome("teste1");
		u.setEmail("teste@gmail.com");
		u.setSenha("teste");
		
		UsuarioDAO.inserirUsuario(u);
		
		



	}
	
}

