package com.sameday.view;

import com.sameday.helper.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.awt.Font;

public class MainView extends JFrame {
    private JTextArea entryTextArea;
    private JComboBox<String> moodComboBox;
    private JButton saveButton;
    private JButton openSearchButton;
    private JButton showPreviousButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField keywordField;
    private JButton searchKeywordButton;
    private JButton accountButton;
    private JLabel welcomePopupLabel;
    private JLabel entryMessageLabel;
    private JLabel showPreviousMessageLabel;
    private JLabel messageLabel;
    private JLabel keywordSearchMessageLabel;


    public MainView() {
        setTitle("SameDay – Un día, muchos años");
        setSize(800, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        welcomePopupLabel = new JLabel("", SwingConstants.CENTER);
        welcomePopupLabel.setBounds(150, 10, 500, 25);
        add(welcomePopupLabel);

        JLabel moodLabel = new JLabel("Estado emocional:");
        moodLabel.setBounds(50, 50, 130, 25);
        add(moodLabel);

        moodComboBox = new JComboBox<>();
        moodComboBox.setBounds(180, 50, 200, 25);
        add(moodComboBox);

        accountButton = new JButton("Mi cuenta");
        accountButton.setBounds(600, 50, 130, 25);
        add(accountButton);

        JLabel entryLabel = new JLabel("Tu entrada de hoy:");
        entryLabel.setBounds(50, 90, 200, 25);
        add(entryLabel);

        LocalDate today = LocalDate.now();
        String formattedDate = today.getDayOfMonth() + " de " +
                today.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) +
                " de " + today.getYear();

        JLabel dateLabel = new JLabel(formattedDate);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setBounds(170, 90, 300, 25);
        add(dateLabel);


        entryTextArea = new JTextArea();
        entryTextArea.setLineWrap(true);
        entryTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(entryTextArea);
        scrollPane.setBounds(50, 120, 700, 300);
        add(scrollPane);

        entryMessageLabel = new JLabel("");
        entryMessageLabel.setBounds(50, 485, 700, 25);
        entryMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        entryMessageLabel.setForeground(Color.RED);
        add(entryMessageLabel);

        saveButton = new JButton("Guardar");
        saveButton.setBounds(330, 440, 120, 30);
        add(saveButton);

        editButton = new JButton("Editar");
        editButton.setBounds(100, 440, 100, 30);
        editButton.setEnabled(false);
        add(editButton);

        deleteButton = new JButton("Borrar");
        deleteButton.setBounds(580, 440, 100, 30);
        deleteButton.setEnabled(false);
        add(deleteButton);

        showPreviousButton = new JButton("Ver años anteriores");
        showPreviousButton.setBounds(280, 525, 240, 40);
        showPreviousButton.setBackground(new Color(230, 225, 215));
        showPreviousButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        add(showPreviousButton);

        showPreviousMessageLabel = new JLabel("");
        showPreviousMessageLabel.setForeground(Color.RED);
        showPreviousMessageLabel.setBounds(265, 580, 300, 25);
        add(showPreviousMessageLabel);

        keywordField = new JTextField();
        keywordField.setBounds(50, 630, 200, 25);
        add(keywordField);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 700, 370, 25);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        add(messageLabel);

        searchKeywordButton = new JButton("Buscar palabra");
        searchKeywordButton.setBounds(270, 630, 150, 25);
        add(searchKeywordButton);

        UIHelper.enterTriggersClick(keywordField, searchKeywordButton);

        keywordSearchMessageLabel = new JLabel("");
        keywordSearchMessageLabel.setBounds(50, 660, 370, 25); // debajo del buscador
        keywordSearchMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        keywordSearchMessageLabel.setForeground(Color.RED);
        add(keywordSearchMessageLabel);


        openSearchButton = new JButton("Buscador por fecha");
        openSearchButton.setBounds(580, 630, 150, 30);
        add(openSearchButton);
    }

    public JTextArea getEntryTextArea() {
        return entryTextArea;
    }

    public JComboBox<String> getMoodComboBox() {
        return moodComboBox;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getOpenSearchButton() {
        return openSearchButton;
    }

    public JButton getShowPreviousButton() {
        return showPreviousButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getKeywordField() {
        return keywordField;
    }

    public JButton getSearchKeywordButton() {
        return searchKeywordButton;
    }

    public JButton getAccountButton() {
        return accountButton;
    }

    public JLabel getWelcomePopupLabel() {
        return welcomePopupLabel;
    }

    public JLabel getEntryMessageLabel() {
        return entryMessageLabel;
    }

    public JLabel getShowPreviousMessageLabel() {
        return showPreviousMessageLabel;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public JLabel getKeywordSearchMessageLabel() {
        return keywordSearchMessageLabel;
    }

}
