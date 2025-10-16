package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import com.example.javacoursework.hibernatecontrol.GenericHibernate;
import com.example.javacoursework.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
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

    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;
    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
    }

    public void goToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("login-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = (Stage) redirectToLogIn.getScene().getWindow();
        stage.setTitle("Main page");
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
//            User user = new User(usernameField.getText(), passwordField.getText(), surnameField.getText(), phoneNumberField.getText(), addressField.getText());
//            genericHibernate.create(user);

//            User testUser = genericHibernate.getEntityById(User.class, 10);
//            System.out.println(testUser);

            //genericHibernate.delete(User.class, 1);
        }
    }
}
