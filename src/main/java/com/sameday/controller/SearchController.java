package com.sameday.controller;

import com.sameday.helper.EntryHelper;
import com.sameday.helper.MessageHelper;
import com.sameday.model.EntryItem;
import com.sameday.service.SearchService;
import com.sameday.session.UserSession;
import com.sameday.view.EntryDetailView;
import com.sameday.view.MainView;
import com.sameday.view.SearchView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class SearchController {
    private final SearchView view;
    private final MainView mainView;

    public SearchController(SearchView view, MainView mainView) {
        this.view = view;
        this.mainView = mainView;
        setupSearchByDateButton();
    }

    private void setupSearchByDateButton() {
        view.getSearchButton().addActionListener(e -> searchByDate());
    }

    private void searchByDate() {
        String text = view.getDateField().getText().trim();
        int userId = UserSession.getUserId();

        if (!text.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            MessageHelper.showMessage(view.getMessageLabel(), "Formato inválido. Usa DD-MM-YYYY.");
            return;
        }

        String[] parts = text.split("-");
        String day = String.format("%02d", Integer.parseInt(parts[0]));
        String month = String.format("%02d", Integer.parseInt(parts[1]));
        String year = parts[2];
        String sqlDate = year + "-" + month + "-" + day;

        try {
            EntryItem entry = SearchService.searchEntryByDate(userId, sqlDate);
            if (entry != null) {
                new EntryDetailView(entry, true).setVisible(true);
            } else {
                MessageHelper.showMessage(view.getMessageLabel(), "No hay entradas en esa fecha.");
            }
        } catch (Exception e) {
            MessageHelper.showMessage(view.getMessageLabel(), "Error al buscar la entrada.");
        }
    }

    public static void searchByWord(MainView view) {
        String word = view.getKeywordField().getText().trim();
        if (word.isEmpty()) {
            MessageHelper.showMessage(view.getKeywordSearchMessageLabel(), "Introduce una palabra para buscar.");
            return;
        }

        if (word.length() > 20) {
            MessageHelper.showMessage(view.getKeywordSearchMessageLabel(), "La búsqueda no puede superar los 20 caracteres.");
            return;
        }

        List<EntryItem> results;
        try {
            results = SearchService.searchEntriesByWord(UserSession.getUserId(), word);
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(view, "No se pudo realizar la búsqueda. Intenta más tarde.", "Error");
            return;
        }

        if (results.isEmpty()) {
            MessageHelper.showMessage(view.getKeywordSearchMessageLabel(), "No se encontraron entradas con esa palabra.");

            return;
        }

        showResults(results, view);
    }

    private static void showResults(List<EntryItem> resultados, MainView view) {
        DefaultListModel<EntryItem> model = new DefaultListModel<>();
        for (EntryItem item : resultados) {
            model.addElement(item);
        }

        JList<EntryItem> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JDialog dialog = new JDialog(view, "SameDay - Resultados de búsqueda", true);
        dialog.setSize(650, 400);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(view);

        JButton openButton = new JButton("Abrir entrada seleccionada");
        openButton.setBounds(200, 290, 220, 30);
        dialog.add(openButton);

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

        openButton.addActionListener(e -> {
            EntryItem selected = list.getSelectedValue();
            if (selected != null) {
                try {
                    EntryItem fullEntry = com.sameday.service.EntryService.getEntryById(selected.getId());
                    new EntryDetailView(fullEntry, false).setVisible(true);
                } catch (Exception ex) {
                    MessageHelper.showPopUpMessage(dialog, "Error al cargar la entrada.", "Error");
                }
                dialog.dispose();
            } else {
                MessageHelper.showPopUpMessage(dialog, "Selecciona una entrada de la lista.", "Aviso");
            }
        });


        dialog.setVisible(true);
    }

    public static void showPreviousEntries(MainView view) {
        EntryHelper.showPreviousEntries(
                view,
                view.getShowPreviousMessageLabel(),
                LocalDate.now(),
                UserSession.getUserId()
        );
    }
}
