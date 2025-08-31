package com.sameday.view;

import com.sameday.session.UserSession;
import com.sameday.helper.MessageHelper;
import com.sameday.service.UserService;

import javax.swing.*;

public class AccountView extends JDialog {
    private JPasswordField newPassField;
    private JPasswordField confirmPassField;
    private JLabel messageLabel;

    public AccountView(JFrame parent) {
        super(parent, "Cambiar contraseña", true);
        setTitle("SameDay – Cambiar contraseña");
        setSize(400, 280);
        setLayout(null);
        setLocationRelativeTo(parent);

        initComponents();
    }

    private void initComponents() {
        JLabel passLabel = new JLabel("Nueva contraseña:");
        passLabel.setBounds(30, 30, 150, 25);
        add(passLabel);

        newPassField = new JPasswordField();
        newPassField.setBounds(180, 30, 160, 25);
        add(newPassField);

        JLabel confirmLabel = new JLabel("Confirmar contraseña:");
        confirmLabel.setBounds(30, 70, 150, 25);
        add(confirmLabel);

        confirmPassField = new JPasswordField();
        confirmPassField.setBounds(180, 70, 160, 25);
        add(confirmPassField);

        messageLabel = new JLabel("");
        messageLabel.setForeground(java.awt.Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(30, 110, 320, 25);
        add(messageLabel);

        JButton saveButton = new JButton("Guardar");
        saveButton.setBounds(130, 160, 120, 30);
        add(saveButton);

        getRootPane().setDefaultButton(saveButton);
        saveButton.addActionListener(e -> changePassword());
    }

    private void changePassword() {
        String newPassword = new String(newPassField.getPassword()).trim();
        String confirm = new String(confirmPassField.getPassword()).trim();

        if (newPassword.isEmpty() || confirm.isEmpty()) {
            MessageHelper.showMessage(messageLabel, "Rellena ambos campos.");
            return;
        }

        if (!newPassword.equals(confirm)) {
            MessageHelper.showMessage(messageLabel, "Las contraseñas no coinciden.");
            return;
        }

        if (!newPassword.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$")) {
            MessageHelper.showMessage(messageLabel, "Contraseña inválida. Mínimo 8 caracteres, letras y números.");
            return;
        }

        boolean updated = UserService.updatePassword(UserSession.getUserId(), newPassword);
        if (updated) {
            MessageHelper.showPopUpMessage(this, "Contraseña actualizada correctamente.", "Éxito");
            dispose();
        } else {
            MessageHelper.showPopUpMessage(this, "No se pudo cambiar la contraseña.", "Error");
        }
    }
}
