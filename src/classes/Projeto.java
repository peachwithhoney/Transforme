package classes;

import DAO.ProjetoDAO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal metaFinanceira;
    private BigDecimal arrecadacao;
    private LocalDateTime dataCriacao;

    
    public Projeto() {
        this.arrecadacao = BigDecimal.ZERO; 
        this.dataCriacao = LocalDateTime.now(); 
    }

    
    public Projeto(int id, String nome, String descricao, BigDecimal metaFinanceira, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.metaFinanceira = metaFinanceira;
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDateTime.now(); 
        this.arrecadacao = BigDecimal.ZERO; 
    }

    
    public Projeto(int id, String nome, String descricao, BigDecimal metaFinanceira, BigDecimal arrecadacao, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.metaFinanceira = metaFinanceira;
        this.arrecadacao = arrecadacao;
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDateTime.now(); 
    }

    
    public BigDecimal totalArrecadado(int meses) {
        return ProjetoDAO.calculaTotalArrecadado(this, meses);
    }

    
    public String metaFinanceira() {
        return "Projeto " + this.getNome() + "\nTotal Arrecadado/Meta\nR$ " + this.getArrecadacao() + " / R$ " + this.getMetaFinanceira();
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", metaFinanceira=" + metaFinanceira +
                ", arrecadacao=" + arrecadacao +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}