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
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovimentacaoEntradaController implements Initializable {
    private RestTemplate restTemplate = new RestTemplate();
    private MovimentacaoEntradaDAO movEntrDAO = new MovimentacaoEntradaDAO(restTemplate);
    private EntradaDAO entradaDAO = new EntradaDAO(restTemplate);
    private ProdutoDAO produtoDAO = new ProdutoDAO(restTemplate);
    private List<Produto> produtos = new ArrayList<>();
    private String token;
    private Long idGeral;

    private void carregarVariaveisGerais() {
        token = Main.pegarToken();
        produtos = produtoDAO.listAll(token);
        idGeral = Main.pegarIdGeral();
    }

    @FXML
    private TableView<MovimentacaoEntradaObservable> tbMovEntrada;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Long> clId;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Entrada> clIdEntrada;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, String> clProduto;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Double> clValorUnit;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Integer> clQuantidade;

    @FXML
    private TableColumn<MovimentacaoEntradaObservable, Double> clValorTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarVariaveisGerais();
        clId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clIdEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        clValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        setupColunaValorUnitario();
        setupColunaQuantidade();
        setupColunaProduto();

        // single cell selection mode
        tbMovEntrada.getSelectionModel().setCellSelectionEnabled(true);
        // allow multi selection
        tbMovEntrada.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // select first cell
        tbMovEntrada.getSelectionModel().selectFirst();

        listarMovimentacoesEntradas();
    }

    private void setupColunaValorUnitario() {
        clValorUnit.setCellValueFactory(new PropertyValueFactory<>("valorUnitarioAtual"));
        clValorUnit.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    }

    private void setupColunaQuantidade() {
        clQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        clQuantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

    }

    private void setupColunaProduto() {
        clProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        ObservableList<String> lista = FXCollections.observableArrayList(Conversor.converterProdutoString(produtos));
        clProduto.setCellFactory(ComboBoxTableCell.forTableColumn(lista));
    }

    @FXML
    void TabelaKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
//            event.consume(); // don't consume the event or else the values won't be updated;
            return;
        }
        // switch to edit mode on keypress, but only if we aren't already in edit mode
        if (tbMovEntrada.getEditingCell() == null) {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                TablePosition focusedCellPosition = tbMovEntrada.getFocusModel().getFocusedCell();
                tbMovEntrada.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
            }
        }
    }

    @FXML
    void TabelaKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.F2) {
            TablePosition pos = tbMovEntrada.getFocusModel().getFocusedCell();
            if (pos.getRow() == -1) {
                tbMovEntrada.getSelectionModel().select(0);
            } else if (pos.getRow() == tbMovEntrada.getItems().size() - 1) {
                addRow();
            } else if (pos.getRow() < tbMovEntrada.getItems().size() - 1) {
                tbMovEntrada.getSelectionModel().clearAndSelect(pos.getRow() + 1, pos.getTableColumn());
            }
        }
    }

    private void listarMovimentacoesEntradas() {
        try {
            tbMovEntrada.getItems().clear();
            tbMovEntrada.setItems(
                    FXCollections.observableArrayList(
                            Conversor.converterMovimentacaoEntrada(
                                    movEntrDAO.findByEntrada(idGeral, token))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRow() {
        TablePosition pos = tbMovEntrada.getFocusModel().getFocusedCell();
        tbMovEntrada.getSelectionModel().clearSelection();

        MovimentacaoEntradaObservable data = new MovimentacaoEntradaObservable(idGeral, idGeral);
        tbMovEntrada.getItems().add(data);

        int row = tbMovEntrada.getItems().size() - 1;
        tbMovEntrada.getSelectionModel().select(row, pos.getTableColumn());
        tbMovEntrada.scrollTo(data);
    }

    private Optional<Produto> pegarProdutoByNome(String nome) {
        return produtos.stream().filter(produto -> produto.getNome().equals(nome)).findFirst();
    }

    private List<MovimentacaoEntrada> verificarItensAlterados() {
        ObservableList<MovimentacaoEntradaObservable> items = tbMovEntrada.getItems();
        List<MovimentacaoEntrada> bancoDeDados = movEntrDAO.findByEntrada(idGeral, token);
        List<MovimentacaoEntrada> alterados = new ArrayList<>();
        boolean modificado = false;

        for (MovimentacaoEntradaObservable itemTabela : items) {
            for (MovimentacaoEntrada itemBanco : bancoDeDados) {
                if (itemTabela.getId() == idGeral) {
                    Optional<Produto> produtoOpt = pegarProdutoByNome(itemTabela.getProduto());
                    if (produtoOpt.isPresent()) {
                        Produto novoProd = produtoOpt.get();
                        alterados.add(new MovimentacaoEntrada(new Entrada(idGeral),
                                novoProd,
                                itemTabela.getValorUnitarioAtual(),
                                itemTabela.getQuantidade()));
                    } else {
                        alertWarning("Produto não encontrado",
                                "Nenhuma alteração foi salva",
                                "Verifique o produto de ID " + itemTabela.getId());
                        return null;
                    }
                }
                if (itemBanco.getId() == itemTabela.getId()) {
                    if (itemBanco.getQuantidade() != itemTabela.getQuantidade()) {
                        itemBanco.setQuantidade(itemTabela.getQuantidade());
                        modificado = true;
                    }
                    if (!itemBanco.getProduto().getNome().equals(itemTabela.getProduto())) {
                        Optional<Produto> produto = pegarProdutoByNome(itemTabela.getProduto());
                        if (produto.isPresent()) {
                            itemBanco.setProduto(produto.get());
                        } else {
                            alertWarning("Produto não encontrado",
                                    "Nenhuma alteração foi salva",
                                    "Verifique o produto de ID " + itemTabela.getId());
                            return null;
                        }
//                        itemBanco.setProduto(produtoDAO.findByNome(itemTabela.getProduto(), token));
                        modificado = true;
                    }
                    if (itemBanco.getValorUnitarioAtual() != itemTabela.getValorUnitarioAtual()) {
                        itemBanco.setValorUnitarioAtual(itemTabela.getValorUnitarioAtual());
                        modificado = true;
                    }
                    if (modificado) {
                        alterados.add(itemBanco);
                        modificado = false;
                    }
                    break;
                }
            }
        }
        return alterados;
    }

    private void aplicarAlteracoes(List<MovimentacaoEntrada> itensAlterados) {
        try {
            movEntrDAO.saveAll(itensAlterados, token);
            alertInformation("Banco atualizado",
                    "Alterações realizadas!",
                    "Todas as " + itensAlterados.size() + " alterações foram realizadas com sucesso.");
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public void removeSelectedRows() {
        List<MovimentacaoEntradaObservable> itensSelecionados = tbMovEntrada.getSelectionModel().getSelectedItems();
        List<MovimentacaoEntrada> itensParaDeletar = new ArrayList<>();
        for (MovimentacaoEntradaObservable item : itensSelecionados) {
            itensParaDeletar.add(new MovimentacaoEntrada(item.getId()));
        }

        Boolean isSim = alertConfirmationSimCalcelar("Excluir itens",
                "Foram excluídos " + itensParaDeletar.size() + " itens da tabela.",
                "Tem certeza que deseja excluí-los?");

        if (isSim) {
            excluirVarios(itensParaDeletar);
        }
    }

    private void excluirVarios(List<MovimentacaoEntrada> itensDeletados) {
        try {
            movEntrDAO.deleteAll(itensDeletados, token);
            alertInformation("Banco atualizado",
                    "Os itens foram deletados!",
                    "Todos os " + itensDeletados.size() + " itens foram deletados com sucesso.");
            tbMovEntrada.getItems().removeAll(tbMovEntrada.getSelectionModel().getSelectedItems());
            tbMovEntrada.getSelectionModel().clearSelection();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button btAtualizar;

    @FXML
    private Button btGravar;

    @FXML
    private Button btDeletarItem;

    @FXML
    private Button btAdicionar;

    @FXML
    private Label lbProdutoCB;

    @FXML
    private ComboBox<String> cbProduto;

    @FXML
    private Label lbFechar;

    @FXML
    void adicionarItem(ActionEvent event) {
//        adicionarMovimentacaoEntrada();
        listarMovimentacoesEntradas();
    }

    @FXML
    void atualizarTabela(ActionEvent event) {
        listarMovimentacoesEntradas();
    }

    @FXML
    void cbProdutoClick(MouseEvent event) {

    }

    @FXML
    void deletarItem(ActionEvent event) {

    }

    @FXML
    void gravarAlteracoes(ActionEvent event) {
        List<MovimentacaoEntrada> itensAlterados = verificarItensAlterados();
        Boolean isSim = alertConfirmationSimCalcelar("Gravar alterações",
                "Foram encontrados \" + itensAlterados.size() + \" itens alterados..",
                "Tem certeza que deseja gravar?");
        if (isSim) {
            aplicarAlteracoes(itensAlterados);
        }
    }

    private void alertWarning(String titulo, String header, String content) {
        Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
        dialogoAviso.setTitle(titulo);
        dialogoAviso.setHeaderText(header);
        dialogoAviso.setContentText(content);
        dialogoAviso.showAndWait();
    }

    private void alertInformation(String titulo, String header, String content) {
        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setTitle(titulo);
        dialogoInfo.setHeaderText(header);
        dialogoInfo.setContentText(content);
        dialogoInfo.showAndWait();
    }

    private static boolean clicouEmSim = false;

    private Boolean alertConfirmationSimCalcelar(String titulo, String header, String content) {
        clicouEmSim = false;
        Alert confirmacaoGravarExclusao = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacaoGravarExclusao.setTitle(titulo);
        confirmacaoGravarExclusao.setHeaderText(header);
        confirmacaoGravarExclusao.setContentText(content);
        confirmacaoGravarExclusao.getButtonTypes().setAll(btnSim, btnCancelar);
        confirmacaoGravarExclusao.showAndWait().ifPresent(b -> {
            if (b == btnSim) {
                clicouEmSim = true;
            }
        });
        return clicouEmSim;
    }
}
