package com.rusakovich.bsuir.client.controllers.menus;

import com.rusakovich.bsuir.client.controllers.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserMenu extends Menu{
    @FXML
    private Button membersBtn;
    @FXML
    private Button bankAccountsBtn;
    @FXML
    private Button incomeBtn;
    @FXML
    private Button expensesBtn;
    @FXML
    private Button reportBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    private void logout(ActionEvent event) {
        parentController.logout();
    }

    @FXML
    private void goToMembers(ActionEvent event) {
        parentController.switchMainPane("../views/members/Members.fxml");
    }

}
