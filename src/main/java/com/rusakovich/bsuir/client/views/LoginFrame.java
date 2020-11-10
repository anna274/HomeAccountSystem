package com.rusakovich.bsuir.client.views;


import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountRole;

import javax.swing.*;
import java.util.Map;

public class LoginFrame extends LifeJFrame {
    private JTextField loginTextField;
    private JPanel mainPanel;
    private JTextField passwordTextField;
    private JButton singinButton;
    private JButton signupButton;

    public LoginFrame() {
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Login");
        setContentPane(mainPanel);
        initButtonsListeners();
        setVisible(true);
    }

    private void initButtonsListeners() {
        singinButton.addActionListener(e -> {
            String login = loginTextField.getText();
            String password = passwordTextField.getText();
            String query = "account?command=login&login=" + login + "&password=" + password;
            Map<String, String> params = Client.doRequest(query);
            if ("ok".equals(params.get("status"))) {
                Account account = new Account(null, params.get("login"), null, 0);
                ApplicationContext.getInstance().setCurrentAccount(account);
                this.setVisible(false);
                FrameHandler.displayFrame("home");
            }
        });
        signupButton.addActionListener(e -> {
            FrameHandler.displayFrame("registration");
        });
    }
}
