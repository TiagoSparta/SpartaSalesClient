package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalController {

    @FXML
    private Button btnVendas;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnEntradas;

    @FXML
    private Button btnSaidas;

    @FXML
    private Button btnConfigurar;

    @FXML
    private Button btnFechar;

    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnVendas) {
        }
        if (actionEvent.getSource() == btnUsuarios) {
        }
        if (actionEvent.getSource() == btnEntradas) {
            Main.setRoot("EntradaScreen");

        }
        if (actionEvent.getSource() == btnSaidas) {
        }
        if (actionEvent.getSource() == btnConfigurar) {
            Main.setRoot("ProdutoScreen");
        }
        if (actionEvent.getSource() == btnFechar) {
            Stage stage = (Stage) btnFechar.getScene().getWindow();
            stage.close();
        }
    }
}
