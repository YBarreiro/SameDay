package com.sameday.service;

import com.sameday.database.ConnectionDB;
import com.sameday.model.EntryItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchService {

    public static EntryItem searchEntryByDate(int userId, String fechaSQL) throws Exception {
        String query = "SELECT id, date, content, mood_id FROM entries WHERE user_id = ? AND date = ?";
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, fechaSQL);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new EntryItem(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("content"),
                        rs.getInt("mood_id")
                );
            }
        }
        return null;
    }

    public static List<EntryItem> searchEntriesByWord(int userId, String word) throws Exception {
        String query = "SELECT id, date, content, mood_id FROM entries WHERE user_id = ? AND content LIKE ? ORDER BY date DESC";
        List<EntryItem> resultados = new ArrayList<>();
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, "%" + word + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultados.add(new EntryItem(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("content"),
                        rs.getInt("mood_id")
                ));
            }
        }
        return resultados;
    }

    public static List<EntryItem> searchEntriesPreviousYears(int userId, LocalDate date) throws Exception {
        List<EntryItem> list = new ArrayList<>();
        String query = "SELECT id, date, content, mood_id FROM entries " +
                "WHERE user_id = ? AND strftime('%d', date) = ? AND strftime('%m', date) = ? AND strftime('%Y', date) < ? " +
                "ORDER BY date DESC";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, String.format("%02d", date.getDayOfMonth()));
            stmt.setString(3, String.format("%02d", date.getMonthValue()));
            stmt.setString(4, String.valueOf(date.getYear()));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new EntryItem(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("content"),
                        rs.getInt("mood_id")
                ));
            }
        }
        return list;
    }

}
