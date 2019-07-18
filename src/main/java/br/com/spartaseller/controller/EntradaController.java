package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.EntradaDAO;
import br.com.spartaseller.persistence.model.ApplicationUser;
import br.com.spartaseller.persistence.model.Entrada;
import br.com.spartaseller.persistence.observable.EntradaObservable;
import br.com.spartaseller.util.Conversor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class EntradaController implements Initializable {
    @FXML
    private TableView<EntradaObservable> tbEntrada;

    @FXML
    private TableColumn<EntradaObservable, Long> clId;

    @FXML
    private TableColumn<EntradaObservable, ApplicationUser> clUser;

    @FXML
    private TableColumn<EntradaObservable, String> cdDataInicio;

    @FXML
    private TableColumn<EntradaObservable, String> clDataFinal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clId.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        clUser.setCellValueFactory(
                new PropertyValueFactory<>("usuario"));
        cdDataInicio.setCellValueFactory(
                new PropertyValueFactory<>("dataInicio"));
        clDataFinal.setCellValueFactory(
                new PropertyValueFactory<>("dataFim"));
    }

    @FXML
    private Button btBuscar;

    private RestTemplate restTemplate = new RestTemplate();
    private EntradaDAO entradaDAO = new EntradaDAO(restTemplate);

    private void buscarTodos() {
        List<Entrada> entradas = entradaDAO.listAll(Main.pegarToken());
        List<EntradaObservable> entradaObsComum = Conversor.converterEntrada(entradas);
        ObservableList<EntradaObservable> listaEntradas = FXCollections.observableArrayList(entradaObsComum);
        tbEntrada.setItems(listaEntradas);
    }

    @FXML
    void buscarTodos(ActionEvent event) {
        buscarTodos();
    }

    @FXML
    private Button btAdicionar;

    private void adicionarEntrada() {
        Entrada entrada = new Entrada();
        entrada.setDataHoraInicio(LocalDateTime.now());
        entrada.setTipo("COMPRA");
        entradaDAO.save(entrada, Main.pegarToken());
    }

    @FXML
    void adicionarEntrada(ActionEvent event) {
        adicionarEntrada();
        buscarTodos();
    }

    @FXML
    private Button btRemover;

    private void removerEntradaSelecionada() {
        EntradaObservable linha = tbEntrada.getSelectionModel().getSelectedItem();
        try {
            entradaDAO.delete(linha.getId(), Main.pegarToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removerEntrada(ActionEvent event) {
        removerEntradaSelecionada();
        buscarTodos();
    }

    @FXML
    void adicionarMovimentacao(ActionEvent event) {
        EntradaObservable linha = tbEntrada.getSelectionModel().getSelectedItem();
        Main.gravarIdGeral(linha.getId());
        try {
            Main.setRoot("MovimentacaoEntradaScreen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
