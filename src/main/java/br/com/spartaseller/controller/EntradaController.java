package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.EntradaDAO;
import br.com.spartaseller.persistence.dao.MovimentacaoEntradaDAO;
import br.com.spartaseller.persistence.model.ApplicationUser;
import br.com.spartaseller.persistence.model.Entrada;
import br.com.spartaseller.persistence.model.MovimentacaoEntrada;
import br.com.spartaseller.persistence.observable.EntradaObservable;
import br.com.spartaseller.util.Alertas;
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
    MovimentacaoEntradaDAO movimentacaoEntradaDAO = new MovimentacaoEntradaDAO(restTemplate);
    String token = Main.pegarToken();

    private void buscarTodos() {
        List<Entrada> entradas = entradaDAO.listAll(token);
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
        Boolean adicionar = Alertas.alertConfirmationSimCalcelar("Nova Entrada",
                "Adicionar nova Entrada.",
                "Tem certeza que deseja adicionar uma nova Entrada?");
        if (adicionar) {
            Entrada entrada = new Entrada();
            entrada.setDataHoraInicio(LocalDateTime.now());
            entrada.setTipo("COMPRA");
            entradaDAO.save(entrada, token);
        }
    }

    @FXML
    void adicionarEntrada(ActionEvent event) {
        adicionarEntrada();
        buscarTodos();
    }

    @FXML
    private Button btRemover;

    private void removerEntradaSelecionada() {
        if (tbEntrada.getSelectionModel().getSelectedItem() != null) {
            try {
                List<MovimentacaoEntrada> movimentEntrada = movimentacaoEntradaDAO.findByEntrada(
                        tbEntrada.getSelectionModel().getSelectedItem().getId(), token);
                if (movimentEntrada.size() > 0) {
                    Boolean excluirTudo = Alertas.alertConfirmationSimCalcelar(
                            "Excluir Entrada e Movimentações?",
                            "Foram encontradas movimentações para a entrada selecionada!",
                            "Tem certeza que deseja excluir a entrada selecionada e todas as suas movimentações?");
                    if (excluirTudo) {
                        movimentacaoEntradaDAO.deleteAll(movimentEntrada, token);
                        entradaDAO.delete(tbEntrada.getSelectionModel().getSelectedItem().getId(), token);
                    }
                } else {
                    Boolean excluirEntrada = Alertas.alertConfirmationSimCalcelar(
                            "Excluir Entrada?",
                            "Foram encontradas movimentações para a entrada selecionada!",
                            "Tem certeza que deseja excluir a entrada selecionada?");
                    if (excluirEntrada) {
                        entradaDAO.delete(tbEntrada.getSelectionModel().getSelectedItem().getId(), token);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alertas.alertWarning(
                        "Erro",
                        "Houve um erro",
                        e.getMessage());
            }
        } else {
            Alertas.alertInformation(
                    "Impossível excluir",
                    "Nenhum item foi selecionado.",
                    "Selecione um item para excluir.");
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
