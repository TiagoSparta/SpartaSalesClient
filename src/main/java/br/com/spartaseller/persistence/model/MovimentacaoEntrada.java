package br.com.spartaseller.persistence.model;

import br.com.spartaseller.persistence.observable.MovimentacaoEntradaObservable;

public class MovimentacaoEntrada extends AbstractEntity{
    private Entrada entrada;
    private Produto produto;
    private Double valorUnitarioAtual;
    private  int quantidade;

    public MovimentacaoEntrada() {
    }

    public MovimentacaoEntrada(Entrada entrada, Produto produto, Double valorUnitarioAtual, int quantidade) {
        this.entrada = entrada;
        this.produto = produto;
        this.valorUnitarioAtual = valorUnitarioAtual;
        this.quantidade = quantidade;
    }

    public MovimentacaoEntrada(MovimentacaoEntradaObservable observable){
        id = observable.getId();
        quantidade = observable.getQuantidade();
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getValorUnitarioAtual() {
        return valorUnitarioAtual;
    }

    public void setValorUnitarioAtual(Double valorUnitarioAtual) {
        this.valorUnitarioAtual = valorUnitarioAtual;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
