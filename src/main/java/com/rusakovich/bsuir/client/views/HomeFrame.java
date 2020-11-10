package com.rusakovich.bsuir.client.views;

import com.rusakovich.bsuir.client.app.ApplicationContext;

import javax.swing.*;

public class HomeFrame extends LifeJFrame {
    private JPanel rootPanel;
    private JLabel loginInfo;
    private JPanel mainPanel;
    private JPanel membersPanel;

    public HomeFrame() {
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Home");
        setContentPane(rootPanel);
    }

    @Override
    public void onVisible() {
        ApplicationContext appContext = ApplicationContext.getInstance();
        loginInfo.setText(appContext.getCurrentAccount().getLogin());
    }
}
