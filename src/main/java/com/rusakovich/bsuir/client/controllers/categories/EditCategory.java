package com.rusakovich.bsuir.client.controllers.categories;

import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class EditCategory extends CategoryModal{
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private Label error;
    private Category editedCategory;

    public Category getEditedCategory() {
        return editedCategory;
    }

    public void setEditedCategory(Category editedCategory) {
        this.editedCategory = editedCategory;
        nameField.setText(editedCategory.getName());
    }

    @FXML
    private void edit(ActionEvent event) {
        String name = nameField.getText();
        if(name.equals("")) {
            error.setText("Введите название");
            return;
        }
        editBtn.setText("Обновление...");
        String query = getServerControllerName() +"?command=update&name=" + name + "&id=" + editedCategory.getId();
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
