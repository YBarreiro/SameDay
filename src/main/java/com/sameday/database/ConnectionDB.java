package com.sameday.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

    private static final String URL = "jdbc:sqlite:" + getDatabasePath();

    private static String getDatabasePath() {
        try {
            // Carpeta donde está el .jar o .exe
            String jarDir = new java.io.File(ConnectionDB.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            return jarDir + java.io.File.separator + "sameday.db";
        } catch (Exception e) {
            e.printStackTrace();
            return "sameday.db";
        }
    }


    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL UNIQUE,"
                + "password_hash TEXT NOT NULL"
                + ");";

        String moodsTable = "CREATE TABLE IF NOT EXISTS moods ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL UNIQUE,"
                + "icon TEXT"
                + ");";

        String entriesTable = "CREATE TABLE IF NOT EXISTS entries ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER NOT NULL,"
                + "date TEXT NOT NULL,"
                + "content TEXT NOT NULL,"
                + "mood_id INTEGER,"
                + "FOREIGN KEY (user_id) REFERENCES users(id),"
                + "FOREIGN KEY (mood_id) REFERENCES moods(id)"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(moodsTable);
            stmt.execute(entriesTable);
            System.out.println("Tablas creadas o ya existentes.");
            insertDefaultMoods();

        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }


    public static void insertDefaultMoods() {
        String checkQuery = "SELECT COUNT(*) FROM moods;";
        String insertQuery = "INSERT INTO moods (name, icon) VALUES (?, ?);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(checkQuery)) {

            if (rs.next() && rs.getInt(1) == 0) {
                try (var pstmt = conn.prepareStatement(insertQuery)) {
                    // Insertar estados emocionales predeterminados
                    insertMood(pstmt, "Feliz", "🙂");
                    insertMood(pstmt, "Eufórico", "😄");
                    insertMood(pstmt, "Motivado", "🚀");
                    insertMood(pstmt, "Orgulloso", "🏆");
                    insertMood(pstmt, "Agradecido", "🙏");
                    insertMood(pstmt, "Tranquilo", "😌");
                    insertMood(pstmt, "Enamorado", "❤️");
                    insertMood(pstmt, "Emocionado", "🤩");
                    insertMood(pstmt, "Optimista", "🌟");
                    insertMood(pstmt, "Inspirado", "✨");
                    insertMood(pstmt, "Triste", "😢");
                    insertMood(pstmt, "Cansado", "😴");
                    insertMood(pstmt, "Enfadado", "😡");
                    insertMood(pstmt, "Ansioso", "😰");
                    insertMood(pstmt, "Preocupado", "😟");
                    insertMood(pstmt, "Nostálgico", "🥺");
                    insertMood(pstmt, "Sorprendido", "😮");

                    System.out.println("Estados emocionales predeterminados insertados.");
                }
            } else {
                System.out.println("Los estados emocionales ya existen.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar estados emocionales: " + e.getMessage());
        }
    }

    private static void insertMood(java.sql.PreparedStatement pstmt, String name, String icon) throws SQLException {
        pstmt.setString(1, name);
        pstmt.setString(2, icon);
        pstmt.executeUpdate();
    }

}
