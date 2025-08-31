package com.sameday.helper;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UIHelper {

    public static void enterTriggersClick(JTextField field, JButton button) {
        field.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "enter");
        field.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
    }

    public static void ctrlEnterTriggersClick(JTextArea area, JButton button) {
        area.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }
}
