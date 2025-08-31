package com.sameday.controller;

import com.sameday.helper.MessageHelper;
import com.sameday.service.UserService;
import com.sameday.view.LoginView;
import com.sameday.view.RegisterView;

public class RegisterController {
    private final RegisterView view;
    private final LoginView loginView;


    public RegisterController(RegisterView view, LoginView loginView) {
        this.view = view;
        this.loginView = loginView;
        setupRegisterButton();
        setupBackToLoginButton();
    }

    private void setupRegisterButton() {
        view.getRegisterButton().addActionListener(e -> registerUser());
    }

    private void setupBackToLoginButton() {
        view.getBackToLoginButton().addActionListener(e -> {
            view.dispose(); // cerrar registro
            loginView.getMessageLabel().setText(""); // limpia mensajes anteriores
            loginView.setVisible(true); // vuelve a mostrar login
        });
    }


    private void registerUser() {
        String username = view.getUsernameField().getText().trim();
        String password = new String(view.getPasswordField().getPassword()).trim();
        String confirmPassword = new String(view.getConfirmPasswordField().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            MessageHelper.showMessage(view.getMessageLabel(), "Por favor, rellena todos los campos.");
            return;
        }

        if (!username.matches("^[a-zA-Z0-9]{4,20}$")) {
            MessageHelper.showMessage(view.getMessageLabel(), "El nombre de usuario debe tener entre 4 y 20 caracteres e incluir letras y/o números.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            MessageHelper.showMessage(view.getMessageLabel(), "Las contraseñas no coinciden.");
            return;
        }

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
            MessageHelper.showMessage(view.getMessageLabel(), "La contraseña debe tener entre 8 y 20 caracteres e incluir al menos una letra y al menos un número.");
            return;
        }

        try {
            boolean registered = UserService.registerUser(username, password);
            if (registered) {
                /*MessageHelper.showPopUpMessage(view, "Registro exitoso. Ya puedes iniciar sesión.", "Registro correcto");
                view.dispose();*/
                    MessageHelper.showPopUpMessage(view, "Registro exitoso. Ya puedes iniciar sesión.", "Registro correcto");
                    view.dispose();
                    loginView.getMessageLabel().setText(""); // limpia errores antiguos
                    loginView.setVisible(true);              // vuelve a mostrar el login
            } else {
                MessageHelper.showMessage(view.getMessageLabel(), "Ese nombre de usuario ya está en uso.");
            }

        } catch (Exception e) {
            MessageHelper.showPopUpMessage(view, "No se pudo registrar el usuario.", "Error");
        }
    }
}
