package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.EntradaDAO;
import br.com.spartaseller.persistence.dao.MovimentacaoEntradaDAO;
import br.com.spartaseller.persistence.dao.ProdutoDAO;
import br.com.spartaseller.persistence.model.Entrada;
import br.com.spartaseller.persistence.model.MovimentacaoEntrada;
import br.com.spartaseller.persistence.model.Produto;
import br.com.spartaseller.persistence.observable.MovimentacaoEntradaObservable;
import br.com.spartaseller.util.Conversor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MovimentacaoEntradaController implements Initializable {
    RestTemplate restTemplate = new RestTemplate();
    MovimentacaoEntradaDAO movEntrDAO = new MovimentacaoEntradaDAO(restTemplate);
    EntradaDAO entradaDAO = new EntradaDAO(restTemplate);

    ProdutoDAO produtoDAO = new ProdutoDAO(restTemplate);

    @FXML
    private TableView<MovimentacaoEntradaObservable> tbEntrada;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Long> clId;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Entrada> clIdEntrada;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, String> clProduto;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Float> clValorUnit;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Integer> clQuantidade;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Float> clValorTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clIdEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        clValorUnit.setCellValueFactory(new PropertyValueFactory<>("valorUnitarioAtual"));
        clValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        setupColunaQuantidade();
        setupColunaProduto();
        listarMovimentacoesEntradas();
    }

    private void setupColunaQuantidade() {
        clQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        clQuantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    private void setupColunaProduto() {
        clProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        List<Produto> produtos = produtoDAO.listAll(Main.pegarToken());
        ObservableList<String> lista = FXCollections.observableArrayList(Conversor.converterProdutoString(produtos));
        clProduto.setCellFactory(ComboBoxTableCell.<MovimentacaoEntradaObservable, String>forTableColumn(lista));
    }

    private void preencherCombobox() {
        List<Produto> produtos = produtoDAO.listAll(Main.pegarToken());
        List<String> nomesProdutos = Conversor.converterProdutoString(produtos);
        ObservableList<String> lista = FXCollections.observableArrayList(nomesProdutos);
        cbProduto.setItems(lista);
    }


    private void listarMovimentacoesEntradas() {
        tbEntrada.getItems().clear();
        List<MovimentacaoEntrada> movimentacoes = movEntrDAO.listAll(Main.pegarToken());
        List<MovimentacaoEntradaObservable> movEntradaObsComum = Conversor.converterMovimentacaoEntrada(movimentacoes);
        ObservableList<MovimentacaoEntradaObservable> listaEntradas = FXCollections.observableArrayList(movEntradaObsComum);
        tbEntrada.setItems(listaEntradas);
    }

    private void adicionarMovimentacaoEntrada() {
        String token = Main.pegarToken();
        MovimentacaoEntrada movEntada = new MovimentacaoEntrada();
        movEntada.setEntrada(entradaDAO.findById(Main.pegarIdGeral(), token));
        movEntada.setProduto(produtoDAO.findByNome(cbProduto.getValue(), token));
        movEntrDAO.save(movEntada, token);
    }

    private void editarMovimentacaoEntrada() {
        String token = Main.pegarToken();
        ObservableList<MovimentacaoEntradaObservable> items = tbEntrada.getItems();
        List<MovimentacaoEntrada> bancoDeDados = movEntrDAO.listAll(token);
        List<MovimentacaoEntrada> alterados = new ArrayList<>();
        boolean modificado = false;
        for (MovimentacaoEntrada itemBanco : bancoDeDados) {
            for (MovimentacaoEntradaObservable itemTabela : items) {
                if (itemBanco.getId() != itemTabela.getId()) {
                    break;
                } else {
                    if (itemBanco.getQuantidade() != itemTabela.getQuantidade()) {
                        itemBanco.setQuantidade(itemTabela.getQuantidade());
                        modificado = true;
                    }
                    if (!itemBanco.getProduto().getNome().equals(itemTabela.getProduto())) {
                        itemBanco.setProduto(produtoDAO.findByNome(itemTabela.getProduto(), token));
                        modificado = true;
                    }
                    if (itemBanco.getValorUnitarioAtual() != itemTabela.getValorUnitarioAtual()) {
                        itemBanco.setValorUnitarioAtual(itemTabela.getValorUnitarioAtual());
                        modificado = true;
                    }
                }
                if (modificado) {
                    alterados.add(itemBanco);
                    modificado = false;
                }
            }
        }
        System.out.println(alterados);
        try {
            movEntrDAO.saveAll(alterados, token);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button btBuscar;

    @FXML
    private Button btAdicionar;


    @FXML
    void buscarTodos(ActionEvent event) {
        editarMovimentacaoEntrada();
    }

    @FXML
    void adicionarMovimentacaoEntrada(ActionEvent event) {
        adicionarMovimentacaoEntrada();
    }

    @FXML
    private ComboBox<String> cbProduto;

    @FXML
    void cbProdutoClick(MouseEvent event) {
        preencherCombobox();
    }

}
