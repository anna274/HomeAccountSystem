package com.rusakovich.bsuir.client.controllers.settings;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class Settings {
    @FXML
    private Label loginLabel;

    @FXML
    public void initialize() {
        Account currentAccount = ApplicationContext.getInstance().getCurrentAccount();
        loginLabel.setText(currentAccount.getLogin());
    }

    @FXML
    private void goToChangeLogin(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/settings/ChangeLogin.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.showAndWait();

            updateAccountInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToChangePassword(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/settings/ChangePassword.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.showAndWait();

            updateAccountInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAccountInfo(){
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "account?command=getById&id=" + accountId;
        Map<String, String> params = Client.doRequest(query);

        if(params.get("status").equals("ok")) {
            Map<String, String> accountParams = Client.getResponseObject(params.get("data"));
            Account account = Account.fromMap(accountParams);
            ApplicationContext.getInstance().setCurrentAccount(account);
            loginLabel.setText(account.getLogin());
        }
    }
}
