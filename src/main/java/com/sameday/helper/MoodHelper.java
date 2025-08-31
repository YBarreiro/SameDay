package com.sameday.helper;

import com.sameday.database.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MoodHelper {

    public static String getEmoji(int moodId) {
        String query = "SELECT icon FROM moods WHERE id = ?";
        try (Connection conn = ConnectionDB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, moodId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("icon");
            }
        } catch (Exception e) {
        }
        return "";
    }
}
