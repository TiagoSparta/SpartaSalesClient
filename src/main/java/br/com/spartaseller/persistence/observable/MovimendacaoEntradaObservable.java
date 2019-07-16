package br.com.spartaseller.persistence.observable;

import br.com.spartaseller.persistence.model.MovimentacaoEntrada;

public class MovimendacaoEntradaObservable {
    private long id;
    private long entrada;
    private String produto;
    private Double valorUnitarioAtual;
    private int quantidade;
    private Double valorTotal;

    public MovimendacaoEntradaObservable() {
    }

    public MovimendacaoEntradaObservable(MovimentacaoEntrada movimentacaoEntrada) {
        id = movimentacaoEntrada.getId();
        entrada = movimentacaoEntrada.getEntrada().getId();
        if (movimentacaoEntrada.getProduto() != null) {
            produto = movimentacaoEntrada.getProduto().getNome();
        } else {
            produto = "Não Registrado";
        }
        valorUnitarioAtual = movimentacaoEntrada.getValorUnitarioAtual();
        quantidade = movimentacaoEntrada.getQuantidade();
        valorTotal = movimentacaoEntrada.getValorUnitarioAtual() * movimentacaoEntrada.getQuantidade();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntrada() {
        return entrada;
    }

    public void setEntrada(long entrada) {
        this.entrada = entrada;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
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

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
