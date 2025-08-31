package com.sameday.view;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JLabel messageLabel;
    private JButton backToLoginButton;


    public RegisterView() {
        setTitle("SameDay – Registro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setBounds(250, 170, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setToolTipText("Mínimo 4 caracteres. Solo letras y/o números.");
        usernameField.setBounds(350, 170, 200, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(250, 210, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setToolTipText("Mínimo 8 caracteres. Debe incluir letras y números.");
        passwordField.setBounds(350, 210, 200, 25);
        add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Repetir contraseña:");
        confirmPasswordLabel.setBounds(200, 250, 150, 25);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setToolTipText("Debe coincidir con la anterior.");
        confirmPasswordField.setBounds(350, 250, 200, 25);
        add(confirmPasswordField);

        registerButton = new JButton("Registrarse");
        registerButton.setBounds(325, 290, 150, 30);
        add(registerButton);
        getRootPane().setDefaultButton(registerButton);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(200, 380, 400, 40);
        messageLabel.setForeground(Color.RED);

        add(messageLabel);

        backToLoginButton = new JButton("Volver a inicio de sesión");
        backToLoginButton.setBounds(300, 340, 200, 30);
        add(backToLoginButton);


    }
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public JButton getBackToLoginButton() {
            return backToLoginButton;
    }

    }
