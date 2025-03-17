package classes;

import DAO.UsuarioDAO;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean logado;

    private static Usuario usuarioLogado;

    public Usuario() {
        this.logado = false;  
    }
    
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.logado = false;  
    }

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.logado = false;  
    }

    public static Usuario loginUsuario(String email, String senha) {
        Usuario usuarioAutenticado = UsuarioDAO.autenticarUsuario(email, senha);

        if (usuarioAutenticado != null) {
            usuarioAutenticado.setLogado(true);
            usuarioLogado = usuarioAutenticado; // Define o usuário logado
            System.out.println("Usuário logado: " + usuarioAutenticado.getNome());
        } else {
            System.out.println("Falha na autenticação. Verifique suas credenciais.");
        }

        return usuarioAutenticado; 
    }

    public static void logoutUsuario() {
        if (usuarioLogado != null) {
            usuarioLogado.setLogado(false);  
            usuarioLogado = null; // Remove o usuário logado
            System.out.println("Usuário deslogado com sucesso!");
        } else {
            System.out.println("Nenhum usuário está logado.");
        }
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
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
        return "Informações do usuário:\nid: " + id + "\nnome: " + nome + "\nemail: " + email;
    }
}
