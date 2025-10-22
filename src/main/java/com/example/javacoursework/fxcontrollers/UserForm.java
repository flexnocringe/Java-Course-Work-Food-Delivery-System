package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import com.example.javacoursework.hibernatecontrol.CustomHibernate;
import com.example.javacoursework.hibernatecontrol.GenericHibernate;
import com.example.javacoursework.model.BasicUser;
import com.example.javacoursework.model.Driver;
import com.example.javacoursework.model.Restaurant;
import com.example.javacoursework.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    @FXML
    public Button redirectToLogIn;
    @FXML
    public RadioButton userRadio;
    @FXML
    public RadioButton restaurantRadio;
    @FXML
    public RadioButton driverRadio;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField usernameField;
    @FXML
    public AnchorPane inputFields;
    @FXML
    public RadioButton adminRadio;
    @FXML
    public Button updateUserButton;

    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;
    private User userForUpdate;
    private boolean isForUpdate;

    public void setData(EntityManagerFactory entityManagerFactory, User user, boolean isForUpdate) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.userForUpdate = user;
        this.isForUpdate = isForUpdate;
        fillUserDataForUpdate();
    }

    private void fillUserDataForUpdate() {
        if(userForUpdate != null && isForUpdate) {
           if(userForUpdate instanceof User) {
               usernameField.setText(userForUpdate.getUsername());
               passwordField.setText(userForUpdate.getPassword());
               nameField.setText(userForUpdate.getName());
               surnameField.setText(userForUpdate.getSurname());
               phoneNumberField.setText(userForUpdate.getPhoneNumber());
           }
        } else {
            updateUserButton.setDisable(true);
            updateUserButton.setVisible(false);
        }
    }

    public void goToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("login-form.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) redirectToLogIn.getScene().getWindow();
        stage.setTitle("Login page");
        stage.setScene(scene);
        stage.show();
    }

    public void disableFields() {
        //Group group = new Group();
        //group.getChildren().add();
        if(adminRadio.isSelected())
        {
            phoneNumberField.setDisable(true);
            phoneNumberField.setVisible(false);
            addressField.setDisable(true);
            addressField.setVisible(false);
        }
        else if(userRadio.isSelected()){
            phoneNumberField.setDisable(false);
            phoneNumberField.setVisible(true);
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

    public void createNewUser() {
        if(userRadio.isSelected()) {
            BasicUser basicUser = new BasicUser(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText());
            genericHibernate.create(basicUser);
        } else if (adminRadio.isSelected()) {
            User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), true);
            genericHibernate.create(user);
        } else if (restaurantRadio.isSelected()) {

        } else if (driverRadio.isSelected()) {
            
        }
    }

    public void updateUser() {
        if(userForUpdate instanceof BasicUser) {
            userForUpdate.setUsername(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
            userForUpdate.setPhoneNumber(phoneNumberField.getText());
        } else if(userForUpdate instanceof User) {
            userForUpdate.setUsername(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
        } else if(userForUpdate instanceof Restaurant) {
            
        } else if (userForUpdate instanceof Driver) {
            
        }
        genericHibernate.edit(userForUpdate);
    }
}
