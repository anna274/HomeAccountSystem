package com.rusakovich.bsuir.client.controllers.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UserMenu extends Menu{
    @FXML
    private void logout(ActionEvent event) {
        parentController.logout();
    }

    @FXML
    private void goToMembers(ActionEvent event) {
        parentController.switchMainPane("../views/members/Accounts.fxml");
    }

    @FXML
    private void goToBankAccounts(ActionEvent event) {
        parentController.switchMainPane("../views/bankAccounts/BankAccounts.fxml");
    }

    @FXML
    private void goToSettings(ActionEvent event) {
        parentController.switchMainPane("../views/settings/Settings.fxml");
    }

}
