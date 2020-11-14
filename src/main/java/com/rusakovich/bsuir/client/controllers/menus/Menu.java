package com.rusakovich.bsuir.client.controllers.menus;

import com.rusakovich.bsuir.client.controllers.Application;

public class Menu {
    Application parentController;

    public Application getParentController() {
        return parentController;
    }

    public void setParentController(Application parentController) {
        this.parentController = parentController;
    }
}
