package br.com.spartaseller.util.ComponentsModels;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public class EditingDoubleCell<T> extends TableCell<T, Double> {

    private TextField textField;
    private TextFormatter<Double> textFormatter;

    private DecimalFormat df;

    public EditingDoubleCell(String... styleClasses) {
        Locale locale = new Locale("pt", "BR");
        String pattern = "#,##0.00";
        df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        df.applyPattern(pattern);
        getStyleClass().addAll(styleClasses);
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.requestFocus();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(df.format(getItem()));
        setGraphic(null);
    }


    @Override
    public void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());

                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : df.format(getItem());
    }

    private void createTextField() {

        textField = new TextField();

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public String toString(Double number) {
                return df.format(number);
            }

            @Override
            public Double fromString(String string) {
                try {
                    double value = df.parse(string).doubleValue();
                    return value;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0.00;
                }
            }

        };

        textFormatter = new TextFormatter<>(converter, 0.00, c ->
        {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = df.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        });

        // add filter to allow for typing only integer
        textField.setTextFormatter(textFormatter);

        textField.setText(getString());

        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // commit on Enter
        textFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            commitEdit(newValue);
        });
    }
}
