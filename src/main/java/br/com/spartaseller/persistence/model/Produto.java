package br.com.spartaseller.persistence.model;

public class Produto extends AbstractEntity {
    private String nome;
    private Double precoVenda;
    private Double precoCustoMedio;
    private int estoqueAtual;
    private boolean ativo;

    public Produto() {
    }

    public Produto(String nome, Double precoVenda, Double precoCustoMedio, int estoqueAtual, boolean ativo) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCustoMedio = precoCustoMedio;
        this.estoqueAtual = estoqueAtual;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getPrecoCustoMedio() {
        return precoCustoMedio;
    }

    public void setPrecoCustoMedio(Double precoCustoMedio) {
        this.precoCustoMedio = precoCustoMedio;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
