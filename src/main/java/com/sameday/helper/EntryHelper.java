package com.sameday.helper;

import com.sameday.model.EntryItem;
import com.sameday.service.SearchService;
import com.sameday.view.EntryDetailView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class EntryHelper {

    public static void showPreviousEntries(JFrame parent, JLabel messageLabel, LocalDate date, int userId) {
        messageLabel.setText("");
        messageLabel.setForeground(Color.RED);

        List<EntryItem> entries;
        try {
            entries = SearchService.searchEntriesPreviousYears(userId, date);
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(parent, "No se pudieron cargar las entradas anteriores.", "Error");
            return;
        }

        if (entries.isEmpty()) {
            MessageHelper.showMessage(messageLabel, "No hay entradas de este día en años anteriores.");
            return;
        }

        DefaultListModel<EntryItem> model = new DefaultListModel<>();
        entries.forEach(model::addElement);

        JDialog dialog = new JDialog(parent, "SameDay – Entradas de años anteriores", true);
        dialog.setSize(650, 400);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(parent);

        JList<EntryItem> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton openButton = new JButton("Abrir entrada seleccionada");
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && list.getSelectedValue() != null) {
                    openButton.doClick();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBounds(20, 20, 600, 250);
        dialog.add(scroll);

        openButton.setBounds(200, 290, 220, 30);
        dialog.add(openButton);

        openButton.addActionListener(e -> {
            EntryItem selected = list.getSelectedValue();
            if (selected != null) {
                new EntryDetailView(selected, true).setVisible(true);
                dialog.dispose();
            } else {
                MessageHelper.showMessage(messageLabel, "Selecciona una entrada.");

            }
        });

        dialog.setVisible(true);
    }
}
