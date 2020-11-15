package com.rusakovich.bsuir.client.controllers.currencies;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
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

public class Currencies {

    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<Currency, String> nameColumn;
    @FXML
    private TableColumn<Currency, String> shortNameColumn;
    @FXML
    private TableColumn<Currency, String> codeColumn;
    @FXML
    private TableColumn<Currency, String> selectionColumn;
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

        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        selectionColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));

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
            ArrayList<Currency> selectedRows = getSelectedRows();

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
        ArrayList<Currency> selectedRows = getSelectedRows();
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

    private ArrayList<Currency> getSelectedRows() {
        ArrayList<Currency> selectedRows = new ArrayList<>();
        ObservableList<Currency> currencies = table.getItems();
        for(Currency currency: currencies) {
            if(currency.getSelected().isSelected()) {
                selectedRows.add(currency);
            }
        }
        return selectedRows;
    }

    private void updateTableContent() {
        String query = "currency?command=getAll";
        Map<String, String> params = Client.doRequest(query);

        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> currenciesParams = Client.getResponseArray(params.get("data"));
                ArrayList<Currency> currencies = new ArrayList<>();

                for(Map<String, String> currencyParams : currenciesParams){
                    Currency currency = Currency.fromMap(currencyParams);
                    currency.setSelected(new CheckBox());
                    currencies.add(currency);
                }
                table.setItems(FXCollections.observableArrayList(currencies));
                ApplicationContext.getInstance().setCurrencies(currencies);
            } else {
                table.setPlaceholder(new Label("Добавьте информацию о валютах. Для этого нажмите кнопку 'Добавить'."));
            }
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<Currency> currencies = table.getItems();
        for(Currency currency: currencies) {
            currency.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(currencies);
    }
}
