package br.com.spartaseller;

import br.com.spartaseller.persistence.model.Token;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;
    private double x, y;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("LoginScreen"));
//        scene.getStylesheets().add(Main.class.getResource("styles/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getRoot().setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        scene.getRoot().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Token token;

    public static String pegarToken(){
        return token.getToken();
    }

    public static void gravarToken(Token tokenEnviado){
        token = tokenEnviado;
    }

    private static long idGeral;

    public static Long pegarIdGeral(){
        return idGeral;
    }

    public static void gravarIdGeral(long id){
        idGeral = id;
    }
}
