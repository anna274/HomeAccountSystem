package com.rusakovich.bsuir.client.controllers.currencies;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.AccountMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class AddCurrency {
    @FXML
    private Button addBtn;
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

    @FXML
    private void add(ActionEvent event) {
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
        addBtn.setText("Сохранение...");
        String query = "currency?command=add&name=" + name + "&shortName=" + shortName + "&code=" + code;
        Map<String, String> params = Client.doRequest(query);

        addBtn.setText("Добавить");
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
