import com.sameday.controller.LoginController;
import com.sameday.controller.MainController;
import com.sameday.database.ConnectionDB;
import com.sameday.session.SessionManager;
import com.sameday.session.UserSession;
import com.sameday.view.LoginView;
import com.sameday.view.MainView;



public class Main {
    public static void main(String[] args) {
        ConnectionDB.createTables();
        String[] datosSesion = SessionManager.loadSession();

        if (datosSesion != null) {
            String username = datosSesion[0];
            int userId = Integer.parseInt(datosSesion[1]);

            UserSession.setUsername(username);
            UserSession.setUserId(userId);

            MainView mainView = new MainView();
            new MainController(mainView);
            mainView.setVisible(true);
        } else {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        }

    }

}
