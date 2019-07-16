package br.com.spartaseller.persistence.observable;

import br.com.spartaseller.persistence.model.Produto;

public class ProdutoObservable {
    private long id;
    private String nome;
    private Double precoVenda;
    private Double precoCustoMedio;
    private int estoqueAtual;
    private boolean ativo;

    public ProdutoObservable() {
    }

    public ProdutoObservable(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        precoVenda = produto.getPrecoVenda();
        precoCustoMedio = produto.getPrecoCustoMedio();
        estoqueAtual = produto.getEstoqueAtual();
        ativo = produto.isAtivo();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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