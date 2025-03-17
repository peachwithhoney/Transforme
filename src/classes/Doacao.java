package classes;

import java.util.Date;

public class Doacao {
    private int id;
    private double valor;
    private Date data;
    private int id_usuario;
    private int id_projeto;

    public Doacao() {
    }

    public Doacao(int id, double valor, Date data, int id_usuario, int id_projeto) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.id_usuario = id_usuario;
        this.id_projeto = id_projeto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getIdProjeto() {
        return id_projeto;
    }

    public void setIdProjeto(int id_projeto) {
        this.id_projeto = id_projeto;
    }
}
