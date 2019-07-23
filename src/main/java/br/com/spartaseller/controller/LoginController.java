package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.LoginDAO;
import br.com.spartaseller.persistence.model.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private RestTemplate restTemplate = new RestTemplate();
    private LoginDAO loginDAO = new LoginDAO(restTemplate);

    private void entrar() {
        try {
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

    @FXML
    void acaoEntrar(ActionEvent event) throws IOException {
        entrar();
    }

    @FXML
    void OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            entrar();
        }
    }
}
