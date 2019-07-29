package br.com.spartaseller.util.ComponentsModels;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DoubleTextField extends TextField {

    public DoubleTextField() {
        this.setPromptText("Valor...");
        mascaraNumero(this);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (!text.matches("[a-z]")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        if (!replacement.matches("[a-z]")) {
            super.replaceSelection(replacement);
        }
    }

    public static void mascaraNumero(TextField textField) {
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",", ".");
            if (newValue.length() > 0) {
                try {
                    Double.parseDouble(newValue);
                    textField.setText(newValue.replaceAll(",", "."));
                } catch (Exception e) {
                    textField.setText(oldValue);
                }
            }
        });

    }
}