package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.EntradaDAO;
import br.com.spartaseller.persistence.dao.MovimentacaoEntradaDAO;
import br.com.spartaseller.persistence.dao.ProdutoDAO;
import br.com.spartaseller.persistence.model.Entrada;
import br.com.spartaseller.persistence.model.MovimentacaoEntrada;
import br.com.spartaseller.persistence.model.Produto;
import br.com.spartaseller.persistence.observable.MovimendacaoEntradaObservable;
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


    private void listarMovimentacoesEntradas() {
        tbEntrada.getItems().clear();
        List<MovimentacaoEntrada> movimentacoes = movEntrDAO.listAll(Main.pegarToken());
        List<MovimendacaoEntradaObservable> movEntradaObsComum = Conversor.converterMovimentacaoEntrada(movimentacoes);
        ObservableList<MovimendacaoEntradaObservable> listaEntradas = FXCollections.observableArrayList(movEntradaObsComum);
        tbEntrada.setItems(listaEntradas);
    }

    private void preencherCombobox() {
        List<Produto> produtos = produtoDAO.listAll(Main.pegarToken());
        List<String> nomesProdutos = Conversor.converterProdutoString(produtos);
        ObservableList<String> lista = FXCollections.observableArrayList(nomesProdutos);
        cbProduto.setItems(lista);
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
        ObservableList<MovimendacaoEntradaObservable> items = tbEntrada.getItems();
        List<MovimendacaoEntradaObservable> collect = new ArrayList<>(items);
        List<MovimentacaoEntrada> originais = movEntrDAO.listAll(token);
        for(MovimentacaoEntrada movimentacaoEntrada : originais){
            for (MovimendacaoEntradaObservable moe : collect){
                if (movimentacaoEntrada.getId() == moe.getId()){
                    movimentacaoEntrada.setQuantidade(moe.getQuantidade());
                }
            }
        }
        movEntrDAO.saveAll(originais, token);
    }

    @FXML
    private TableView<MovimendacaoEntradaObservable> tbEntrada;

    @FXML
    private TableColumn<MovimendacaoEntradaObservable, Long> clId;

    @FXML
    private TableColumn<MovimendacaoEntradaObservable, Entrada> clIdEntrada;

    @FXML
    private TableColumn<MovimentacaoEntrada, Produto> clProduto;

    @FXML
    private TableColumn<MovimendacaoEntradaObservable, Float> clValorUnit;

    @FXML
    private TableColumn<MovimendacaoEntradaObservable, Integer> clQuantidade;

    @FXML
    private TableColumn<MovimendacaoEntradaObservable, Float> clValorTotal;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btAdicionar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clIdEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        clProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        clValorUnit.setCellValueFactory(new PropertyValueFactory<>("valorUnitarioAtual"));
        clQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        clQuantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        listarMovimentacoesEntradas();
    }

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
