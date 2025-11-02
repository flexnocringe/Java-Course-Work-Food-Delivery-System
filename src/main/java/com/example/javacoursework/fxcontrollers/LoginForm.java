package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import com.example.javacoursework.hibernatecontrol.CustomHibernate;
import com.example.javacoursework.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginForm {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javacoursework");

    public void validateUser() throws IOException {
        CustomHibernate customHibernate = new CustomHibernate(entityManagerFactory);
        User user = customHibernate.getUserByCredentials(usernameField.getText(), passwordField.getText());
        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("main-form.fxml"));
            Parent parent = fxmlLoader.load();

            MainForm mainForm = fxmlLoader.getController();
            mainForm.setData(entityManagerFactory, user);

            Scene scene = new Scene(parent);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setTitle("Managment Window");
            stage.setScene(scene);
            stage.show();
        } else{
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "Something went wrong during login", "No such user or wrong credentials");
        }
    }

    public void goToRegisterForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("user-form.fxml"));
        Parent parent = fxmlLoader.load();

        UserForm userForm = fxmlLoader.getController();
        userForm.setData(entityManagerFactory, null, false);

        Stage stage = new Stage();
        stage.setTitle("Create new user");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
