package com.sameday.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerLink;
    private JLabel messageLabel;

    public LoginView() {
        setTitle("SameDay – Iniciar sesión");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);


        initComponents();
    }

    private void initComponents() {

        JLabel titleLabel = new JLabel("SameDay", SwingConstants.CENTER);
        titleLabel.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 36));
        titleLabel.setBounds(250, 80, 300, 40); // (x, y, width, height)
        add(titleLabel);

        JLabel subtitleLabel = new JLabel("Un día, muchos años.", SwingConstants.CENTER);
        subtitleLabel.setFont(new java.awt.Font("Serif", java.awt.Font.ITALIC, 20));
        subtitleLabel.setBounds(250, 120, 300, 30);
        add(subtitleLabel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(250, 390, 300, 40);
        add(messageLabel);

        JLabel usernameLabel = new JLabel("Usuario:");
        messageLabel.setForeground(java.awt.Color.RED);
        usernameLabel.setBounds(250, 200, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(350, 200, 200, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(250, 240, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(350, 240, 200, 25);
        add(passwordField);

        loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(325, 290, 150, 30);
        add(loginButton);
        getRootPane().setDefaultButton(loginButton);

        JLabel noAccountLabel = new JLabel("¿Aún no tienes cuenta en SameDay?", SwingConstants.CENTER);
        noAccountLabel.setBounds(250, 330, 300, 20);
        noAccountLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        add(noAccountLabel);

        registerLink = new JButton("Regístrate ahora");
        registerLink.setBounds(325, 355, 150, 30);
        add(registerLink);

    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterLink() {
        return registerLink;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }
}
