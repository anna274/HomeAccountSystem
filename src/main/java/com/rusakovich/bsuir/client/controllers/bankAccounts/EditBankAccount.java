package com.rusakovich.bsuir.client.controllers.bankAccounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.Application;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Currency;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Map;

public class EditBankAccount extends ApplicationPane {
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<AccountMember> membersList;
    @FXML
    private ComboBox<Currency> currenciesList;
    @FXML
    private Label error;
    private BankAccount editedBankAccount;

    public void setEditedBankAccount(BankAccount editedBankAccount) {
        this.editedBankAccount = editedBankAccount;
        AccountMember currentOwner = ApplicationContext.getInstance().findMemberById(editedBankAccount.getMemberId());
        Currency currency = ApplicationContext.getInstance().findCurrencyById(editedBankAccount.getCurrencyId());
        nameField.setText(editedBankAccount.getName());
        membersList.setValue(currentOwner);
        currenciesList.setValue(currency);
    }

    @FXML
    public void initialize() {
        membersList.setConverter(new StringConverter<>() {
            @Override
            public String toString(AccountMember product) {
                if(product == null) {
                    return "";
                }
                return product.getName();
            }

            @Override
            public AccountMember fromString(final String string) {
                AccountMember accountMember = new AccountMember();
                accountMember.setName(string);
                return accountMember;
            }
        });

        currenciesList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Currency currency) {
                if(currency == null) {
                    return "";
                }
                return currency.getName();
            }

            @Override
            public Currency fromString(final String string) {
                Currency currency = new Currency();
                currency.setName(string);
                return currency;
            }
        });

        ArrayList<AccountMember> members = ApplicationContext.getInstance().getMembersList();
        ArrayList<Currency> currencies = ApplicationContext.getInstance().getCurrencies();

        membersList.setItems(FXCollections.observableArrayList(members));
        currenciesList.setItems(FXCollections.observableArrayList(currencies));
    }

    @FXML
    private void edit(ActionEvent event) {
        String name = nameField.getText();
        AccountMember owner = membersList.getValue();
        Currency currency = currenciesList.getValue();
        if(name.equals("")) {
            error.setText("Введите имя");
            return;
        }
        editBtn.setText("Обновление...");

        String query = "bank_account?command=update" +
                "&id=" + editedBankAccount.getId() +
                "&name=" + name +
                "&currencyId=" + currency.getId() +
                "&memberAccountId=" + owner.getAccountId() +
                "&memberId=" + owner.getId();
        Map<String, String> params = Client.doRequest(query);
        editBtn.setText("Обновить");
        if ("ok".equals(params.get("status"))) {
            cancel(null);
        } else {
            error.setText(params.get("error"));
        }
        cancel(null);
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
