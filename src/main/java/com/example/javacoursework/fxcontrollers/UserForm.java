package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserForm implements Initializable {

    public Button redirectToLogIn;
    public RadioButton userRadio;
    public RadioButton restaurantRadio;
    public RadioButton driverRadio;
    public TextField nameField;
    public TextField surnameField;
    public TextField passwordField;
    public TextField phoneNumberField;
    public TextField addressField;
    public TextField usernameField;
    public AnchorPane inputFields;

    public void goToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("username-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = (Stage) redirectToLogIn.getScene().getWindow();
        stage.setTitle("Main page");
        stage.setScene(scene);
        stage.show();
    }

    public void disableFields() {
        Group group = new Group();
        //group.getChildren().add();
        if(userRadio.isSelected()){
            addressField.setDisable(true);
            addressField.setVisible(false);
        }
        else{
            for(Node field : inputFields.getChildren()){
                field.setDisable(false);
                field.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableFields();
    }
}
