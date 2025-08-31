package com.sameday.controller;

import com.sameday.helper.MessageHelper;
import com.sameday.service.UserService;
import com.sameday.session.SessionManager;
import com.sameday.session.UserSession;
import com.sameday.view.*;

public class LoginController {
    private final LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        setupLoginButton();
        setupRegisterButton();
    }

    private void setupLoginButton() {
        view.getLoginButton().addActionListener(e -> login());
    }

    private void setupRegisterButton() {
        view.getRegisterLink().addActionListener(e -> {
            RegisterView registerView = new RegisterView();
            new RegisterController(registerView, view);
            registerView.setVisible(true);
            view.setVisible(false);
        });
    }


    private void login() {
        String username = view.getUsernameField().getText().trim();
        String password = new String(view.getPasswordField().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            MessageHelper.showMessage(view.getMessageLabel(), "Introduce el nombre de usuario y la contraseña.");
            return;
        }

        try {
            UserService.LoginResult result = UserService.validateLogin(username, password);

            if (result.valid) {
                UserSession.setUserId(result.userId);
                UserSession.setUsername(result.username);
                SessionManager.saveSession(result.username, result.userId);

                view.dispose();
                MainView mainView = new MainView();
                new MainController(mainView);
                mainView.setVisible(true);
            } else {
                MessageHelper.showMessage(view.getMessageLabel(), "Nombre de usuario o contraseña incorrectos.");
            }

        } catch (Exception e) {
            MessageHelper.showPopUpMessage(view, "No se pudo iniciar sesión. Intenta más tarde.", "Error");
        }
    }
}
