package com.sameday.service;

import com.sameday.database.ConnectionDB;
import com.sameday.model.EntryItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntryService {

    public static boolean saveOrUpdateEntry(int userId, String date, String content, int moodId) throws Exception {
        try (Connection conn = ConnectionDB.connect()) {
            if (entryExistsForDate(userId, date, conn)) {
                saveEntryByDate(userId, date, content, moodId, conn);
            } else {
                saveEntry(userId, date, content, moodId, conn);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // opcional: registrar el error
            return false;
        }
    }


    private static boolean entryExistsForDate(int userId, String date, Connection conn) throws Exception {
        String query = "SELECT COUNT(*) FROM entries WHERE user_id = ? AND date = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private static void saveEntry(int userId, String date, String content, int moodId, Connection conn) throws Exception {
        String query = "INSERT INTO entries (user_id, date, content, mood_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            stmt.setString(3, content);
            stmt.setInt(4, moodId);
            stmt.executeUpdate();
        }
    }

    private static void saveEntryByDate(int userId, String date, String content, int moodId, Connection conn) throws Exception {
        String query = "UPDATE entries SET content = ?, mood_id = ? WHERE user_id = ? AND date = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, content);
            stmt.setInt(2, moodId);
            stmt.setInt(3, userId);
            stmt.setString(4, date);
            stmt.executeUpdate();
        }
    }

    public static boolean updateEntryById(int id, String content, int moodId) throws Exception {
        try (Connection conn = ConnectionDB.connect()) {
            String query = "UPDATE entries SET content = ?, mood_id = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, content);
                stmt.setInt(2, moodId);
                stmt.setInt(3, id);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        }
    }


    public static boolean deleteEntryById(int id) throws Exception {
        try (Connection conn = ConnectionDB.connect()) {
            String query = "DELETE FROM entries WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        }
    }


    public static EntryItem getEntryByDate(int userId, LocalDate date) throws Exception {
        String query = "SELECT id, date, content, mood_id FROM entries WHERE user_id = ? AND date = ?";
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, date.toString());
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

    public static List<String> getMoodStates() throws Exception {
        List<String> states = new ArrayList<>();
        String query = "SELECT icon, name FROM moods ORDER BY id ASC";
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String icon = rs.getString("icon");
                String name = rs.getString("name");
                states.add(icon + " " + name);
            }
        }
        return states;
    }

    public static boolean deleteEntryByDate(int userId, LocalDate date) {
        String query = "DELETE FROM entries WHERE user_id = ? AND date = ?";
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, date.toString());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EntryItem getEntryById(int id) throws Exception {
        String query = "SELECT id, date, content, mood_id FROM entries WHERE id = ?";
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
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


}
