package com.rusakovich.bsuir.client.controllers.menus;

import com.rusakovich.bsuir.client.controllers.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminMenu extends Menu{
    @FXML
    private void logout(ActionEvent event) {
        parentController.logout();
    }

    @FXML
    private void goToCurrencies(ActionEvent event) {
        parentController.switchMainPane("../views/currencies/Currencies.fxml");
    }

    @FXML
    private void goToAccounts(ActionEvent event) {
        parentController.switchMainPane("../views/accounts/Accounts.fxml");
    }

    @FXML
    private void goToSettings(ActionEvent event) {
        parentController.switchMainPane("../views/settings/Settings.fxml");
    }
}
