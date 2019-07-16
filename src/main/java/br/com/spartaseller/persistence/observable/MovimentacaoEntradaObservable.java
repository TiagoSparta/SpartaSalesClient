package br.com.spartaseller.persistence.observable;

import br.com.spartaseller.persistence.model.MovimentacaoEntrada;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MovimentacaoEntradaObservable {
    private SimpleLongProperty id;
    private SimpleLongProperty entrada;
    private SimpleStringProperty produto;
    private SimpleDoubleProperty valorUnitarioAtual;
    private SimpleIntegerProperty quantidade;
    private SimpleDoubleProperty valorTotal;

    public MovimentacaoEntradaObservable() {
    }

    public MovimentacaoEntradaObservable(MovimentacaoEntrada movimentacaoEntrada) {
        id = new SimpleLongProperty(movimentacaoEntrada.getId());
        entrada = new SimpleLongProperty(movimentacaoEntrada.getEntrada().getId());
        if (movimentacaoEntrada.getProduto() != null) {
            produto = new SimpleStringProperty(movimentacaoEntrada.getProduto().getNome());
        } else {
            produto = new SimpleStringProperty("Não Registrado");
        }
        valorUnitarioAtual = new SimpleDoubleProperty(movimentacaoEntrada.getValorUnitarioAtual());
        quantidade = new SimpleIntegerProperty(movimentacaoEntrada.getQuantidade());
        valorTotal = new SimpleDoubleProperty(movimentacaoEntrada.getValorUnitarioAtual() * movimentacaoEntrada.getQuantidade());
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getEntrada() {
        return entrada.get();
    }

    public SimpleLongProperty entradaProperty() {
        return entrada;
    }

    public void setEntrada(long entrada) {
        this.entrada.set(entrada);
    }

    public String getProduto() {
        return produto.get();
    }

    public SimpleStringProperty produtoProperty() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto.set(produto);
    }

    public double getValorUnitarioAtual() {
        return valorUnitarioAtual.get();
    }

    public SimpleDoubleProperty valorUnitarioAtualProperty() {
        return valorUnitarioAtual;
    }

    public void setValorUnitarioAtual(double valorUnitarioAtual) {
        this.valorUnitarioAtual.set(valorUnitarioAtual);
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public SimpleIntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public double getValorTotal() {
        return valorTotal.get();
    }

    public SimpleDoubleProperty valorTotalProperty() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal.set(valorTotal);
    }
}
