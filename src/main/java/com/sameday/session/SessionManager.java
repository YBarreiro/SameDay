package com.sameday.session;

import java.io.*;

public class SessionManager {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String SESSION_FILE = TEMP_DIR + File.separator + "sameday_sesion.txt";

    public static void saveSession(String username, int userId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SESSION_FILE))) {
            writer.write(username + ";" + userId);
        } catch (IOException e) {
            System.out.println("No se pudo guardar la sesión.");
        }
    }

    public static String[] loadSession() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SESSION_FILE))) {
            String line = reader.readLine();
            if (line != null && line.contains(";")) {
                return line.split(";");
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static void deleteSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
