package com.rusakovich.bsuir.client.views;

import javax.swing.*;

public class RegistrationFrame extends LifeJFrame {
    private JTextField loginField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
    private JButton submitButton;
    private JPanel mainPanel;
    private JButton backToLoginPageButton;
    private JLabel RegistrationLabel;
    private JLabel Registration;

    public RegistrationFrame() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Registration");
        setContentPane(mainPanel);
    }
}
