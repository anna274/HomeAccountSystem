package com.rusakovich.bsuir.client.controllers.menus;

import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UserMenu extends ApplicationPane {
    @FXML
    private void logout(ActionEvent event) {
        parentController.logout();
    }

    @FXML
    private void goToMembers(ActionEvent event) {
        parentController.switchMainPane("../views/members/Members.fxml");
    }

    @FXML
    private void goToBankAccounts(ActionEvent event) {
        parentController.switchMainPane("../views/bankAccounts/BankAccounts.fxml");
    }

    @FXML
    private void goToIncomes(ActionEvent event) {
        parentController.switchMainPane("../views/income/Income.fxml");
    }

    @FXML
    private void goToExpenses(ActionEvent actionEvent) {
        parentController.switchMainPane("../views/expenses/Expenses.fxml");
    }

    @FXML
    private void goToSettings(ActionEvent event) {
        parentController.switchMainPane("../views/settings/Settings.fxml");
    }
}
