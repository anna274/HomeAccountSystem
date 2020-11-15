package com.rusakovich.bsuir.client.controllers.currencies;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Currency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class EditCurrency {
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField shortNameField;
    @FXML
    private TextField codeField;
    @FXML
    private Label error;
    private Currency editedCurrency;

    public void setEditedCurrency(Currency editedCurrency) {
        this.editedCurrency = editedCurrency;
        nameField.setText(editedCurrency.getName());
        shortNameField.setText(editedCurrency.getShortName());
        codeField.setText(editedCurrency.getCode());
    }

    @FXML
    private void edit(ActionEvent event) {
        String name = nameField.getText();
        String shortName = shortNameField.getText();
        String code = codeField.getText();
        if(name.equals("")) {
            error.setText("Введите название");
            return;
        }
        if(shortName.equals("")) {
            error.setText("Введите короткое название");
            return;
        }
        if(code.equals("")) {
            error.setText("Введите код");
            return;
        }
        editBtn.setText("Обновление...");
        String query = "currency?command=update&id=" + editedCurrency.getId() +"&name=" + name + "&shortName=" + shortName + "&code=" + code;
        Map<String, String> params = Client.doRequest(query);

        editBtn.setText("Обновить");
        if ("ok".equals(params.get("status"))) {
            cancel(null);
        } else {
            error.setText(params.get("error"));
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
