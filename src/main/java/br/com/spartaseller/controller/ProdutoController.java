package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.ProdutoDAO;
import br.com.spartaseller.persistence.model.Produto;
import br.com.spartaseller.persistence.observable.ProdutoObservable;
import br.com.spartaseller.util.Conversor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoController implements Initializable {
    @FXML
    private TableView<ProdutoObservable> tbProduto;

    @FXML
    private TableColumn<ProdutoObservable, Long> clId;

    @FXML
    private TableColumn<ProdutoObservable, String> clNome;

    @FXML
    private TableColumn<ProdutoObservable, Double> clPrecoVenda;

    @FXML
    private TableColumn<ProdutoObservable, Double> clCustoMedio;

    @FXML
    private TableColumn<ProdutoObservable, Integer> Estoque;

    @FXML
    private TableColumn<ProdutoObservable, Boolean> clAtivo;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoVenda;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        clCustoMedio.setCellValueFactory(new PropertyValueFactory<>("precoCustoMedio"));
        Estoque.setCellValueFactory(new PropertyValueFactory<>("estoqueAtual"));
        clAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
    }

    @FXML
    private Button btBuscar;

    RestTemplate restTemplate = new RestTemplate();
    ProdutoDAO produtoDAO = new ProdutoDAO(restTemplate);

    private void buscarTodos() {
        List<Produto> produtos = produtoDAO.listAll(Main.pegarToken());
        List<ProdutoObservable> produtoObsComum = Conversor.converterProduto(produtos);
        ObservableList<ProdutoObservable> listaProdutos = FXCollections.observableArrayList(produtoObsComum);
        tbProduto.setItems(listaProdutos);
    }

    @FXML
    void buscarTodos(ActionEvent event) {
        buscarTodos();
    }

    @FXML
    private Button btAdicionar;

    private void adicionarProduto() {
        Produto produto = new Produto(
                txtNome.getText(),
                new Double(txtPrecoVenda.getText()),
                0.00,
                0,
                true);
        produtoDAO.save(produto, Main.pegarToken());
        buscarTodos();
    }

    @FXML
    void adicionarProduto(ActionEvent event) {
        adicionarProduto();
        buscarTodos();
    }

    @FXML
    private Button btRemover;

    private void removerProdutoSelecionado() {
        ProdutoObservable linha = tbProduto.getSelectionModel().getSelectedItem();
        try {
            produtoDAO.delete(linha.getId(), Main.pegarToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removerProduto(ActionEvent event) {
        removerProdutoSelecionado();
        buscarTodos();
    }

}
