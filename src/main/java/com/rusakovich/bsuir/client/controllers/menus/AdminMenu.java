package com.rusakovich.bsuir.client.controllers.menus;

import com.rusakovich.bsuir.client.controllers.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminMenu extends Menu{
    @FXML
    private Button usersBtn;
    @FXML
    private Button currenciesBtn;
    @FXML
    private Button incomeCategoriesBtn;
    @FXML
    private Button expensesCategoriesBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    private void logout(ActionEvent event) {
        parentController.logout();
    }
}
