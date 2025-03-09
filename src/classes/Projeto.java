package classes;

import DAO.ProjetoDAO;
import java.math.BigDecimal;
import java.util.Date;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal metaFinanceira;
    private BigDecimal arrecadacao;
    private String imagem;
    private Date dataCriacao;

    public Projeto() {
    }

    public Projeto(int id, String nome, String descricao, BigDecimal metaFinanceira, Date dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.metaFinanceira = metaFinanceira;
        this.dataCriacao = dataCriacao;
    }
    
    public Projeto(int id, String nome, String descricao, BigDecimal metaFinanceira, String imagem, Date dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.metaFinanceira = metaFinanceira;
        this.imagem = imagem;
        this.dataCriacao = dataCriacao;
    }
    
    public Projeto(int id, String nome, String descricao, BigDecimal metaFinanceira, BigDecimal arrecadacao, String imagem, Date dataCriacao) {
    	this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.metaFinanceira = metaFinanceira;
        this.arrecadacao = arrecadacao;
        this.imagem = imagem;
        this.dataCriacao = dataCriacao;
    }
    
    public BigDecimal totalArrecadado(int meses) {
    	BigDecimal total_arrecadado = ProjetoDAO.calculaTotalArrecadado(this, meses);
    	
    	return total_arrecadado;
    }
    
    public String metaFinanceira() {
    	String status;
    	
    	
    	status = ("Projeto " + this.getNome() + "\nTotal Arrecadado/Meta\nR$" + this.getArrecadacao() +" / R$" + this.getMetaFinanceira());
    	
    	return status;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getMetaFinanceira() {
        return metaFinanceira;
    }

    public void setMetaFinanceira(BigDecimal metaFinanceira) {
        this.metaFinanceira = metaFinanceira;
    }

    public BigDecimal getArrecadacao() {
        return arrecadacao;
    }

    public void setArrecadacao(BigDecimal arrecadacao) {
        this.arrecadacao = arrecadacao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
