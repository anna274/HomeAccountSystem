package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.AccountMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Members {

    @FXML
    private TableColumn<AccountMember, Long> numberColumn;
    @FXML
    private TableColumn<AccountMember, String> nameColumn;
    @FXML
    private TableView<AccountMember> table;

    @FXML
    public void initialize() {

        table.setPlaceholder(new Label("Загрузка ..."));

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        updateTableContent();
    }

    @FXML
    public void addMember() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/modals/AddMember.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTableContent() {
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "account_member?command=getAllByAccountId&accountId=" + accountId;
        Map<String, String> params = Client.doRequest(query);

        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> membersParams = Client.getResponseArray(params.get("data"));
                ObservableList<AccountMember> members = FXCollections.observableArrayList();

                for(Map<String, String> memberParams : membersParams){
                    members.add(AccountMember.fromMap(memberParams));
                }
                table.setItems(members);
            } else {
                table.setPlaceholder(new Label("Добавьте пользователей в аккаунт. Для этого нажмите кнопку 'Добавить'."));
            }
        }
    }
}
