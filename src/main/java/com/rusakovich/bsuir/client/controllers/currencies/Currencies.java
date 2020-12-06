package com.rusakovich.bsuir.client.controllers.currencies;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.currencies.EditCurrency;
import com.rusakovich.bsuir.server.entity.Currency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Currencies extends ApplicationPane {

    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<Currency, String> nameColumn;
    @FXML
    private TableColumn<Currency, String> shortNameColumn;
    @FXML
    private TableColumn<Currency, String> codeColumn;
    @FXML
    private TableView<Currency> table;
    @FXML
    private Label message;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    public void initialize() {
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        updateTableContent();
    }

    @FXML
    public void addCurrency() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/currencies/AddCurrency.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editCurrency() {
        try {
            ObservableList<Currency> selectedRows = table.getSelectionModel().getSelectedItems();

            if(selectedRows.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedRows.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/currencies/EditCurrency.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditCurrency editCurrencyController = loader.getController();
            editCurrencyController.setEditedCurrency(selectedRows.get(0));

            stage.showAndWait();

            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSelectedCurrencies() {
        ObservableList<Currency> selectedRows = table.getSelectionModel().getSelectedItems();
        int selectedNumber = selectedRows.size();

        if(selectedNumber == 0) {
            message.setText("Выберите записи для удаления");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Удаление записей");
        alert.setContentText("Вы уверены, что хотите удалить " +  selectedNumber + " записей?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteBtn.setText("Удаление...");
            for(Currency currency : selectedRows) {
                String query = "currency?command=delete&id=" + currency.getId();
                Client.doRequest(query);
            }
            updateTableContent();
            deleteBtn.setText("Удалить");
        }
    }

    private void updateTableContent() {
        ArrayList<Currency> currencies = getCurrenciesFromDB();
        ApplicationContext.getInstance().setCurrencies(currencies);
        if(currencies.size() != 0) {
            table.setItems(FXCollections.observableArrayList(currencies));
        } else {
            table.setPlaceholder(new Label("Добавьте информацию о валютах. Для этого нажмите кнопку 'Добавить'."));
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        if(selectAllCheckbox.isSelected()) {
            table.getSelectionModel().selectAll();
        } else {
            table.getSelectionModel().clearSelection();
        }
    }

    public static ArrayList<Currency> getCurrenciesFromDB(){
        String query = "currency?command=getAll";
        Map<String, String> params = Client.doRequest(query);
        ArrayList<Currency> currencies = new ArrayList<>();

        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> currenciesParams = Client.getResponseArray(params.get("data"));

                for(Map<String, String> currencyParams : currenciesParams){
                    currencies.add(Currency.fromMap(currencyParams));
                }
            }
        }
        return currencies;
    }
}
