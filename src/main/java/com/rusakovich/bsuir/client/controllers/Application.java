package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.members.EditMember;
import com.rusakovich.bsuir.client.controllers.menus.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.io.IOException;

public class Application {
    @FXML
    private Pane mainPane;
    @FXML
    private Pane menuPane;

    @FXML
    public void initialize() {
        Integer roleId = ApplicationContext.getInstance().getCurrentAccount().getRoleId();
        if(roleId == 0) {
            switchMenuPane("../views/menus/UserMenu.fxml");
        } else {
            switchMenuPane("../views/menus/AdminMenu.fxml");
        }
        switchMainPane("../views/Home.fxml");
    }

    public void switchMenuPane(String url) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Pane menu = loader.load();
            menuPane.getChildren().add(menu);
            Menu menuController = loader.getController();
            menuController.setParentController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchMainPane(String url) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource(url));
            mainPane.getChildren().add(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        ApplicationContext.clearContext();
        Client.openScene("../views/Login.fxml");
    }
}
