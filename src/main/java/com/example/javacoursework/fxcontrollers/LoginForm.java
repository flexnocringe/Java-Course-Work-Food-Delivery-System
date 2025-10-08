package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginForm {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    public void validateUser() throws IOException {
        System.out.println("username: "+usernameField.getCharacters()+" password: "+passwordField.getCharacters());
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setTitle("Prikolas");
        stage.setScene(scene);
        stage.show();
    }

    public void goToRegisterForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("user-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = new Stage();
        stage.setTitle("Prikolas");
        stage.setScene(scene);
        stage.show();
    }
}
