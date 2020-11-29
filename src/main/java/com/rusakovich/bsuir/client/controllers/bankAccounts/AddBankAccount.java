package com.rusakovich.bsuir.client.controllers.bankAccounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
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

public class AddBankAccount extends ApplicationPane {
    @FXML
    private Button addBtn;
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

    @FXML
    public void initialize() {
//        membersList.setCellFactory(new Callback<>() {
//            @Override
//            public ListCell<AccountMember> call(ListView<AccountMember> p) {
//                return new ListCell<>() {
//                    @Override
//                    protected void updateItem(AccountMember member, boolean bln) {
//                        super.updateItem(member, bln);
//
//                        if (member != null) {
//                            setText(member.getName());
//                        } else {
//                            setText(null);
//                        }
//                    }
//                };
//            }
//        });

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
    private void add(ActionEvent event) {
        String name = nameField.getText();
        AccountMember owner = membersList.getValue();
        Currency currency = currenciesList.getValue();
        System.out.println(owner);

        if(name.equals("")) {
            error.setText("Введите имя");
            return;
        }
        if(owner == null) {
            error.setText("Выберите владельца счёта");
            return;
        }
        if(currency == null) {
            error.setText("Выберите валюту счёта");
            return;
        }
        addBtn.setText("Сохранение...");
        String query = "bank_account?command=add" +
                "&name=" + name +
                "&memberId=" + owner.getId() +
                "&memberAccountId=" + owner.getAccountId() +
                "&currencyId=" + currency.getId();
        Map<String, String> params = Client.doRequest(query);
        addBtn.setText("Добавить");
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
