package classes;

import java.util.Date;

public class Doacao {
    private int id;
    private double valor;
    private Date data;
    private int idUsuario;
    private int idProjeto;

    public Doacao() {
    }

    public Doacao(int id, double valor, Date data, int idUsuario, int idProjeto) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
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
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }
}
