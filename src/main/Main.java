package main;

import classes.*;
import DAO.*;


public class Main {

	public static void main(String[] args) {
		Usuario user = new Usuario();
		//Usuario user = new Usuario("Paulo Sergio", "ps9@gmail.com", "123");
		
		//UsuarioDAO.inserirUsuario(user);
		
		//UsuarioDAO.deletarUsuario(2);
		
		//user = UsuarioDAO.consultarUsuario(1);
		
		//System.out.println(user.toString() + "\n");
		
		//user.setEmail("paidosanta@gmail.com");
		
		//UsuarioDAO.atualizarUsuario(user);
		
		user.setEmail("paidosanta@gmail.com");
		user.setSenha("123");
		
		Usuario user1 = new Usuario();
		
		user1 = Usuario.loginUsuario(user.getEmail(), user.getSenha());
		
		System.out.println(user1.toString());
		
	}
}

