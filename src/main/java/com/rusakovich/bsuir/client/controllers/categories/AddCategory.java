package com.rusakovich.bsuir.client.controllers.categories;

import com.rusakovich.bsuir.client.app.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class AddCategory extends CategoryModal{
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private Label error;

    @FXML
    private void add(ActionEvent event) {
        String name = nameField.getText();
        if(name.equals("")) {
            error.setText("Введите название");
            return;
        }
        addBtn.setText("Сохранение...");
        String query = getServerControllerName() +"?command=add&name=" + name;
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
