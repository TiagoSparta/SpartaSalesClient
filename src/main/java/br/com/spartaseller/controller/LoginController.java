package br.com.spartaseller.controller;

import br.com.spartaseller.Main;
import br.com.spartaseller.persistence.dao.LoginDAO;
import br.com.spartaseller.persistence.model.Token;
import br.com.spartaseller.util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

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
        if (txtUsuario == null || txtUsuario.getText().isEmpty()) {
            Alertas.alertWarning("Impossível fazer login",
                    "O usuário deve ser preenchido para que o login seja efetuado.",
                    "Preencha o usuário para efetuar o login.");
        } else if (txtPassword == null || txtPassword.getText().isEmpty()) {
            Alertas.alertWarning("Impossível fazer login",
                    "A senha deve ser preenchida para que o login seja efetuado.",
                    "Preencha a senha para efetuar o login.");
        } else {
            try {
                Token token = loginDAO.loginReturningToken(txtUsuario.getText(), txtPassword.getText());
                if (token == null) {
                    Alertas.alertWarning("Retorno inválido",
                            "Retorno de login inválido.",
                            "O Token retornou nulo.");
                } else if (!token.getExpirationTime().isBefore(LocalDateTime.now())) {
                    Alertas.alertWarning("Token retornou expirado",
                            "Token retornou expirado!",
                            "Efetue o login novamente para gerar novo Token.");
                } else {
                    Main.gravarToken(token);
                    Main.setRoot("PrincipalScreen");
                }
            } catch (Exception e) {
                Alertas.alertWarning("Erro",
                        "Falha no login.",
                        e.getMessage());
                txtConexao.setPromptText(e.toString());
            }
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
