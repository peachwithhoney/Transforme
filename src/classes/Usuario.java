package classes;

import DAO.UsuarioDAO;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    
    private boolean logado;

    
    public Usuario() {
    }
    
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.logado = false;
    }
    
    
 // ##### Requisito 2 - 10/02/2024 #####
    public static Usuario loginUsuario(String email,String senha) {
        Usuario temp_user = new Usuario();
        temp_user.setEmail(email);
        temp_user.setSenha(senha);
        
        return UsuarioDAO.autenticarUsuario(temp_user);
    }
    
    // ##### Requisito 2 - 10/02/2024 #####
    public static void logoutUsuario(Usuario usuario) {
        if (usuario != null) {
            usuario.setLogado(false); // Marca o usuário como "não logado"
            System.out.println("Usuário deslogado com sucesso!");
        } else {
            System.out.println("Nenhum usuário está logado.");
        }
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    @Override
    public String toString() {
        return "Informacoes do usuario:\nid: " + id + "\nnome: " + nome + "\nemail: " + email;
    }
}
