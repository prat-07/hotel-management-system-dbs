package com.pratham.controller;

import com.pratham.dao.LoginDAO;
import com.pratham.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class LoginController {

    @FXML
    private TextField unameTextField;
    @FXML
    private PasswordField pwdPasswordField;
    @FXML
    private Button loginButton;


    public void gotoDashboard(ActionEvent event) throws IOException {

        final String uname = unameTextField.getText().trim();
        final String pwd = pwdPasswordField.getText().trim();

        if( uname.isEmpty() || pwd.isEmpty() ){
            AlertUtil.showWarning("Field(s) cannot be empty.");
            return;
        }

        if(LoginDAO.validateLogin(uname, pwd)){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/dashboard.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }

    }
}
