package com.sameday.controller;

import com.sameday.helper.UIHelper;
import com.sameday.service.EntryService;
import com.sameday.helper.MessageHelper;
import com.sameday.model.EntryItem;
import com.sameday.session.SessionManager;
import com.sameday.session.UserSession;
import com.sameday.view.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class MainController {
    private final MainView view;
    private boolean editMode = false;

    public MainController(MainView view) {
        this.view = view;

        try {
            loadMoodStates();
            loadTodayEntry();
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(view, "Error al cargar datos iniciales.", "Error");
        }

        setupSaveButton();
        setupEditButton();
        setupDeleteButton();
        setupSearchByWord();
        setupSeachPreviousYears();
        setupOpenSearchByDate();
        setUpAccountButton();

        UIHelper.enterTriggersClick(view.getKeywordField(), view.getSearchKeywordButton());
        UIHelper.ctrlEnterTriggersClick(view.getEntryTextArea(), view.getSaveButton());

        view.getAccountButton().setText("\uD83D\uDC64 " + UserSession.getUsername());

        new javax.swing.Timer(5000, e -> view.getWelcomePopupLabel().setText("")) {{
            setRepeats(false);
            start();
        }};
    }

    private void setupSaveButton() {
        view.getSaveButton().addActionListener(e -> saveEntry());
    }

    private void setupEditButton() {
        view.getEditButton().addActionListener(e -> {
            editMode = true;
            view.getSaveButton().setEnabled(true);
            view.getSaveButton().setText("Guardar cambios");
        });
    }

    private void setupDeleteButton() {
        view.getDeleteButton().addActionListener(e -> deleteTodayEntry());
    }

    private void setupSearchByWord() {
        view.getSearchKeywordButton().addActionListener(e -> SearchController.searchByWord(view));
    }

    private void setupSeachPreviousYears() {
        view.getShowPreviousButton().addActionListener(e -> SearchController.showPreviousEntries(view));
    }

    private void setupOpenSearchByDate() {
        view.getOpenSearchButton().addActionListener(e -> {
            SearchView searchView = new SearchView();
            new SearchController(searchView, view);
            searchView.setVisible(true);
        });
    }

    private void setUpAccountButton() {
        view.getAccountButton().addActionListener(e -> {
            JPopupMenu menu = new JPopupMenu();

            JMenuItem changePassword = new JMenuItem("Cambiar contraseña");
            changePassword.addActionListener(ev -> new AccountView(view).setVisible(true));

            JMenuItem logout = new JMenuItem("Cerrar sesión");
            logout.addActionListener(ev -> {
                SessionManager.deleteSession();
                UserSession.setUsername(null);
                UserSession.setUserId(0);
                view.dispose();
                LoginView login = new LoginView();
                new LoginController(login);
                login.setVisible(true);
            });

            menu.add(changePassword);
            menu.addSeparator();
            menu.add(logout);
            menu.show(view.getAccountButton(), 0, view.getAccountButton().getHeight());
        });
    }

    private void loadTodayEntry() throws Exception {
        EntryItem entry = EntryService.getEntryByDate(UserSession.getUserId(), LocalDate.now());
        if (entry != null) {
            view.getEntryTextArea().setText(entry.getContent());
            view.getMoodComboBox().setSelectedIndex(entry.getMoodId());
            view.getEditButton().setEnabled(true);
            view.getDeleteButton().setEnabled(true);
            view.getSaveButton().setEnabled(false);
        } else {
            clearView();
        }
    }

    private void loadMoodStates() throws Exception {
        List<String> states = EntryService.getMoodStates();
        view.getMoodComboBox().removeAllItems();
        view.getMoodComboBox().addItem("Selecciona un estado emocional...");
        for (String state : states) {
            view.getMoodComboBox().addItem(state);
        }
    }

    private void clearView() {
        view.getEntryTextArea().setText("");
        view.getMoodComboBox().setSelectedIndex(0);
        view.getEditButton().setEnabled(false);
        view.getDeleteButton().setEnabled(false);
        view.getSaveButton().setEnabled(true);
        editMode = false;
    }


    private void saveEntry() {
        String content = view.getEntryTextArea().getText().trim();
        int selectedIndex = view.getMoodComboBox().getSelectedIndex();
        int moodIndex = selectedIndex - 1;

        if (content.isBlank()) {
            MessageHelper.showMessage(view.getEntryMessageLabel(), "La entrada no puede estar vacía.");
            return;
        }

        if (content.length() > 2000) {
            MessageHelper.showMessage(view.getEntryMessageLabel(), "La entrada no puede tener más de 2000 caracteres.");
            return;
        }

        if (selectedIndex <= 0) {
            MessageHelper.showMessage(view.getEntryMessageLabel(), "Selecciona un estado emocional antes de guardar.");
            return;
        }

        try {
            boolean saved = EntryService.saveOrUpdateEntry(UserSession.getUserId(), LocalDate.now().toString(), content, moodIndex + 1);
            if (saved) {
                MessageHelper.showMessage(view.getEntryMessageLabel(), "Entrada guardada correctamente.");
                loadTodayEntry();
                view.getSaveButton().setEnabled(false);
                view.getSaveButton().setText("Guardar entrada");
                editMode = false;
            } else {
                MessageHelper.showPopUpMessage(view, "No se pudo guardar la entrada. Intenta más tarde.", "Error");
            }
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(view, "Error al guardar la entrada.", "Error");
        }
    }

    private void deleteTodayEntry() {
        if (!MessageHelper.confirmDelete(view, "¿Seguro que quieres borrar la entrada de hoy?", "Confirmar")) return;

        try {
            boolean deleted = EntryService.deleteEntryByDate(UserSession.getUserId(), LocalDate.now());
            if (deleted) {
                MessageHelper.showMessage(view.getEntryMessageLabel(), "Entrada eliminada correctamente.");
                clearView();
            } else {
                MessageHelper.showPopUpMessage(view, "No se pudo borrar la entrada. Intenta más tarde.", "Error");
            }
        } catch (Exception e) {
            MessageHelper.showPopUpMessage(view, "Error al intentar borrar la entrada.", "Error");
        }
    }
}
