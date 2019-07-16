package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.LoginDAO;
import br.com.spartaseller.persistence.model.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btCancelar;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Label txtFechar;

    @FXML
    void AcaoFechar(MouseEvent event) {
        Stage stage = (Stage) txtFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private TextArea txtConexao;

    @FXML
    void acaoCancelar(ActionEvent event) {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();

    }
    RestTemplate restTemplate = new RestTemplate();
    LoginDAO loginDAO = new LoginDAO(restTemplate);

    @FXML
    void acaoEntrar(ActionEvent event) throws IOException {
        try {
            txtConexao.setPromptText("Entrando...");
            Token token = loginDAO.loginReturningToken(txtUsuario.getText(), txtPassword.getText());
            if (token != null) {
                Main.gravarToken(token);
                Main.setRoot("PrincipalScreen");
            } else {
                txtUsuario.setText("Falha");
            }
        } catch (Exception e) {
            txtConexao.setPromptText(e.toString());
        }

    }
}
