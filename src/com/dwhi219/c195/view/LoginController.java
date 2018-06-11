package com.dwhi219.c195.view;

import com.dwhi219.c195.MainApp;
import com.dwhi219.c195.dao.UserDAO;
import com.dwhi219.c195.dao.exceptions.InvalidCredentialsException;
import com.dwhi219.c195.dao.impl.UserDAOImpl;
import com.dwhi219.c195.model.User;
import com.dwhi219.c195.util.AccessLogger;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for logging in to the system. Fulfills REQUIREMENT A
 *
 * @author Daniel White
 */
public class LoginController {

    @FXML
    private Label applicationLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private MainApp mainApp;
    private UserDAO dao;
    private ResourceBundle rb;

    public LoginController() {
    }

    @FXML
    public void initialize() {
        // REQUIREMENT A
        Locale locale = new Locale("en", "US"); // swap the commmenting on this line
        //Locale locale = new Locale("jp"); // and this line to change locale
        rb = ResourceBundle.getBundle("com.dwhi219.c195.util/lang", locale);
        applicationLabel.setText(rb.getString("title"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("loginButton"));
        dao = new UserDAOImpl();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void handleLoginButton() {
        if (isInputValid()) {
            try {
                User candidate = dao.getUser(usernameField.getText(), passwordField.getText());
                mainApp.setSessionUser(candidate);
                mainApp.setInitialView(true);
                AccessLogger.logAccessAttempt(candidate.getUserName(), true);
                mainApp.showAppointmentScreen();
            } catch (InvalidCredentialsException ex) {
                mainApp.throwAlert(rb.getString("invalidCredentials"));
            }
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += rb.getString("missingUsername");
        }
        if (passwordField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += rb.getString("missingPassword");
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            mainApp.throwAlert(errorMessage);
            return false;
        }
    }

}
