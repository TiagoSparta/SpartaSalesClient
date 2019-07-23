package br.com.spartaseller.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Alertas {
    public static void alertWarning(String titulo, String header, String content) {
        Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
        dialogoAviso.setTitle(titulo);
        dialogoAviso.setHeaderText(header);
        dialogoAviso.setContentText(content);
        dialogoAviso.showAndWait();
    }

    public static void alertInformation(String titulo, String header, String content) {
        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setTitle(titulo);
        dialogoInfo.setHeaderText(header);
        dialogoInfo.setContentText(content);
        dialogoInfo.showAndWait();
    }

    private static boolean clicouEmSim = false;

    public static Boolean alertConfirmationSimCalcelar(String titulo, String header, String content) {
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
