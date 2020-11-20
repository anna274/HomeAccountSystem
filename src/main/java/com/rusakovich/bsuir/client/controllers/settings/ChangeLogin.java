package com.rusakovich.bsuir.client.controllers.settings;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.server.entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Map;

public class ChangeLogin extends ApplicationPane {
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField loginField;
    @FXML
    private Label error;

    @FXML
    public void initialize() {
        String currentLogin = ApplicationContext.getInstance().getCurrentAccount().getLogin();
        loginField.setText(currentLogin);
    }

    @FXML
    private void edit(ActionEvent event) {
        String login = loginField.getText();
        if(login.equals("")) {
            error.setText("Введите логин");
            return;
        }
        editBtn.setText("Обновление...");
        Account currentAccount = ApplicationContext.getInstance().getCurrentAccount();
        String query = "account?command=update" +
                "&login=" + login +
                "&password=" + currentAccount.getPassword() +
                "&roleId=" + currentAccount.getRoleId() +
                "&id=" + currentAccount.getId();
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
