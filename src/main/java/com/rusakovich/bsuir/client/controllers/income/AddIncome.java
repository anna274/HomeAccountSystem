package com.rusakovich.bsuir.client.controllers.income;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.entity.Currency;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class AddIncome extends ApplicationPane {
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField noteField;
    @FXML
    private TextField sumField;
    @FXML
    private Label error;
    @FXML
    private ComboBox<BankAccount> bankAccountsList;
    @FXML
    private ComboBox<Category> categoriesList;
    @FXML
    private DatePicker dataPicker;
    @FXML
    public void initialize() {
        bankAccountsList.setConverter(new StringConverter<>() {
            @Override
            public String toString(BankAccount bankAccount) {
                if(bankAccount == null) {
                    return "";
                }
                return bankAccount.getName();
            }

            @Override
            public BankAccount fromString(final String string) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setName(string);
                return bankAccount;
            }
        });

        categoriesList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category category) {
                if(category == null) {
                    return "";
                }
                return category.getName();
            }

            @Override
            public Category fromString(final String string) {
                Category category = new Category();
                category.setName(string);
                return category;
            }
        });

        ArrayList<BankAccount> bankAccounts = ApplicationContext.getInstance().getBankAccounts();
        ArrayList<Category> categories = ApplicationContext.getInstance().getIncomeCategories();

        bankAccountsList.setItems(FXCollections.observableArrayList(bankAccounts));
        categoriesList.setItems(FXCollections.observableArrayList(categories));

        dataPicker.setValue(LocalDate.now());
    }

    @FXML
    private void add(ActionEvent event) {
        String sum = sumField.getText();
        String note = noteField.getText();
        BankAccount bankAccount = bankAccountsList.getValue();
        Category category = categoriesList.getValue();
        LocalDate date = dataPicker.getValue();

        if(sum.equals("")) {
            error.setText("Введите сумму");
            return;
        }
        if(bankAccount == null) {
            error.setText("Выберите счёт");
            return;
        }
        if(category == null) {
            error.setText("Выберите категории");
            return;
        }
        addBtn.setText("Сохранение...");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String query = "income?command=add" +
                "&sum=" + sum +
                "&memberId=" + bankAccount.getMemberId() +
                "&memberAccountId=" + bankAccount.getMemberAccountId() +
                "&bankAccountId=" + bankAccount.getId() +
                "&currencyId=" + bankAccount.getCurrencyId() +
                "&categoryId=" + category.getId() +
                "&date=" + date.format(formatter);
        if(!note.equals("")) {
            query += "&note=" + note;
        }
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
