package com.sameday.service;

import com.sameday.database.ConnectionDB;
import com.sameday.helper.SecurityHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    public static LoginResult validateLogin(String username, String password) throws Exception {
        String query = "SELECT id, username, password_hash FROM users WHERE username = ?";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String enteredHash = SecurityHelper.sha256(password);

                if (storedHash.equals(enteredHash)) {
                    return new LoginResult(true, rs.getInt("id"), rs.getString("username"));
                }
            }
        }

        return new LoginResult(false, 0, null);
    }

    public static class LoginResult {
        public final boolean valid;
        public final int userId;
        public final String username;

        public LoginResult(boolean valid, int userId, String username) {
            this.valid = valid;
            this.userId = userId;
            this.username = username;
        }
    }

    public static boolean registerUser(String username, String password) throws Exception {
        try (Connection conn = ConnectionDB.connect()) {
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // nombre ya existe
                }
            }

            String insertQuery = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, username);
                stmt.setString(2, SecurityHelper.sha256(password));
                stmt.executeUpdate();
            }

            return true;
        }
    }
    public static boolean updatePassword(int userId, String newPassword) {
        try {
            String hash = SecurityHelper.sha256(newPassword);
            String query = "UPDATE users SET password_hash = ? WHERE id = ?";

            try (Connection conn = ConnectionDB.connect();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, hash);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
