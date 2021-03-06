package com.rusakovich.bsuir.client.controllers.categories;


import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CategoryModal extends ApplicationPane {
    private String serverControllerName;
    @FXML
    private Label sectionLabel;

    public String getServerControllerName() {
        return serverControllerName;
    }

    public void setServerControllerName(String serverControllerName) {
        this.serverControllerName = serverControllerName;
        sectionLabel.setText(serverControllerName.equals("income_category") ? "Раздел: доходы" : "Раздел: расходы");
    }
}
