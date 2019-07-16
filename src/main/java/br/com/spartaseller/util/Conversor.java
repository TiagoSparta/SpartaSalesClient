package br.com.spartaseller.util;

import br.com.spartaseller.persistence.model.Entrada;
import br.com.spartaseller.persistence.model.MovimentacaoEntrada;
import br.com.spartaseller.persistence.model.Produto;
import br.com.spartaseller.persistence.observable.EntradaObservable;
import br.com.spartaseller.persistence.observable.MovimentacaoEntradaObservable;
import br.com.spartaseller.persistence.observable.ProdutoObservable;

import java.util.ArrayList;
import java.util.List;

public class Conversor {

    public static List<EntradaObservable> converterEntrada(List<Entrada> entradaList) {
        List<EntradaObservable> list = new ArrayList<>();
        for (Entrada entrada : entradaList) {
            EntradaObservable entradaObservable = new EntradaObservable(entrada);
            list.add(entradaObservable);
        }
        return list;
    }

    public static List<MovimentacaoEntradaObservable> converterMovimentacaoEntrada(List<MovimentacaoEntrada> movimentacaoEntradaList) {
        List<MovimentacaoEntradaObservable> list = new ArrayList<>();
        for (MovimentacaoEntrada movimentacao : movimentacaoEntradaList) {
            MovimentacaoEntradaObservable observable = new MovimentacaoEntradaObservable(movimentacao);
            list.add(observable);
        }
        return list;
    }

    public static List<ProdutoObservable> converterProduto(List<Produto> produtoList) {
        List<ProdutoObservable> list = new ArrayList<>();
        for (Produto produto : produtoList){
            ProdutoObservable observable = new ProdutoObservable(produto);
            list.add(observable);
        }
        return list;
    }

    public static List<String> converterProdutoString(List<Produto> produtoList) {
        List<String> list = new ArrayList<>();
        for (Produto produto : produtoList){
            list.add(produto.getNome());
        }
        return list;
    }
}
