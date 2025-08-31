package com.sameday.view;

import com.sameday.helper.EntryHelper;
import com.sameday.service.EntryService;
import com.sameday.helper.MessageHelper;
import com.sameday.model.EntryItem;
import com.sameday.session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class EntryDetailView extends JFrame {
    private JTextArea textArea;
    private JComboBox<String> moodCombo;
    private JButton saveButton;
    private JButton deleteButton;
    private EntryItem entry;
    private boolean showComparison;
    private JLabel messageLabel;

    public EntryDetailView(EntryItem entry, boolean showComparison) {
        this.entry = entry;
        this.showComparison = showComparison;

        LocalDate date = LocalDate.parse(entry.getDate());
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        setTitle("SameDay – Entrada del " + formattedDate);

        setSize(600, 530);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initComponents();
        loadData();

        setAlwaysOnTop(true);
        toFront();
    }

    private void initComponents() {
        JLabel moodLabel = new JLabel("Estado emocional:");
        moodLabel.setBounds(30, 20, 150, 25);
        add(moodLabel);

        moodCombo = new JComboBox<>();
        moodCombo.setBounds(160, 20, 300, 25);
        add(moodCombo);

        JLabel contentLabel = new JLabel("Contenido:");
        contentLabel.setBounds(30, 60, 150, 25);
        add(contentLabel);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(30, 90, 520, 250);
        add(scroll);

        saveButton = new JButton("Guardar cambios");
        saveButton.setBounds(100, 370, 160, 30);
        saveButton.addActionListener(e -> saveChanges());
        add(saveButton);

        deleteButton = new JButton("Eliminar entrada");
        deleteButton.setBounds(320, 370, 160, 30);
        deleteButton.addActionListener(e -> deleteEntry());
        add(deleteButton);

        if (showComparison) {
            JButton compareButton = new JButton("Ver años anteriores");
            compareButton.setBounds(200, 420, 200, 30);
            compareButton.addActionListener(e -> EntryHelper.showPreviousEntries(this, messageLabel, LocalDate.parse(entry.getDate()), UserSession.getUserId()));
            add(compareButton);
        }

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(40, 460, 500, 25);
        add(messageLabel);
    }

    private void loadData() {
        try {
            List<String> emotions = EntryService.getMoodStates();
            for (String em : emotions) {
                moodCombo.addItem(em);
            }
        } catch (Exception e) {
            moodCombo.addItem("Error al cargar estados");
        }

        moodCombo.setSelectedIndex(entry.getMoodId() - 1);
        textArea.setText(entry.getContent());
    }


    private void saveChanges() {
        String newContent = textArea.getText().trim();
        int newMoodId = moodCombo.getSelectedIndex() + 1;

        if (newContent.isEmpty()) {
            MessageHelper.showMessage(messageLabel, "La entrada no puede estar vacía.");
            return;
        }

        if (newContent.length() > 2000) {
            MessageHelper.showMessage(messageLabel, "La entrada no puede tener más de 2000 caracteres.");
            return;
        }

        if (newMoodId <= 0) {
            MessageHelper.showMessage(messageLabel, "Selecciona un estado emocional.");
            return;
        }

        try {
            boolean updated = EntryService.updateEntryById(entry.getId(), newContent, newMoodId);
            if (updated) {
                MessageHelper.showMessage(messageLabel, "Entrada actualizada correctamente.");
            } else {
                MessageHelper.showPopUpMessage(this, "No se pudo actualizar la entrada.", "Error");
            }
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(this, "Error al actualizar la entrada.", "Error");
        }
    }


    private void deleteEntry() {
        if (!MessageHelper.confirmDelete(this, "¿Seguro que quieres eliminar esta entrada?", "Confirmar")) {
            return;
        }

        try {
            boolean deleted = EntryService.deleteEntryById(entry.getId());
            if (deleted) {
                MessageHelper.showPopUpMessage(this, "Entrada eliminada.", "Éxito");
                dispose();
            } else {
                MessageHelper.showPopUpMessage(this, "No se pudo eliminar la entrada.", "Error");
            }
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(this, "Error al eliminar la entrada.", "Error");
        }
    }


    public JLabel getMessageLabel() {
        return messageLabel;
    }
}
