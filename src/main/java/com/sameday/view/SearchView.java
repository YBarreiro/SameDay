package com.sameday.view;

import com.sameday.helper.UIHelper;

import javax.swing.*;
import java.awt.*;

public class SearchView extends JFrame {
    private JTextField dateField;
    private JButton searchButton;
    private JLabel messageLabel;

    public SearchView() {
        setTitle("SameDay - Buscar entrada por fecha");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JLabel label = new JLabel("Fecha (DD-MM-YYYY):");
        label.setBounds(30, 30, 150, 25);
        add(label);

        dateField = new JTextField();
        dateField.setBounds(180, 30, 150, 25);
        add(dateField);

        searchButton = new JButton("Buscar");
        searchButton.setBounds(350, 30, 100, 25);
        add(searchButton);

        UIHelper.enterTriggersClick(dateField, searchButton);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(75, 70, 400, 25);
        add(messageLabel);
    }

    public JTextField getDateField() {
        return dateField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }
}
