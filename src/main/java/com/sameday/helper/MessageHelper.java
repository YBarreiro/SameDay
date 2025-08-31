package com.sameday.helper;

import javax.swing.*;
import java.awt.*;

public class MessageHelper {

    public static void showMessage(JLabel label, String message) {
        label.setText("<html><div style='text-align:center'>" + message + "</div></html>");
        label.setForeground(Color.RED);

        new javax.swing.Timer(5000, e -> label.setText("")) {{
            setRepeats(false);
            start();
        }};
    }

    public static void showPopUpMessage(Component parent, String message, String titulo) {
        JOptionPane.showMessageDialog(parent, message, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmDelete(JFrame parent, String message, String title) {
        int confirm = JOptionPane.showOptionDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Sí", "No"},
                "No"
        );
        return confirm == JOptionPane.YES_OPTION;
    }
}

