package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import com.example.javacoursework.hibernatecontrol.CustomHibernate;
import com.example.javacoursework.hibernatecontrol.GenericHibernate;
import com.example.javacoursework.model.*;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public RadioButton adminRadio;
    @FXML
    public Button updateUserButton;
    @FXML
    public ComboBox<VechicleType> vechicleTypeBox;
    @FXML
    public TextField drivingLicenceField;
    @FXML
    public TextField workHoursField;
    @FXML
    public ToggleGroup userTypeSelecion;
    @FXML
    public VBox userSelection;
    @FXML
    public DatePicker bDateSelector;
    @FXML
    public Button userCreationButton;

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
            userCreationButton.setDisable(true);
            if(userForUpdate instanceof Driver) {
               usernameField.setText(userForUpdate.getUsername());
               passwordField.setText(userForUpdate.getPassword());
               nameField.setText(userForUpdate.getName());
               surnameField.setText(userForUpdate.getSurname());
               phoneNumberField.setText(userForUpdate.getPhoneNumber());
               addressField.setText(((Driver) userForUpdate).getAddress());
               drivingLicenceField.setText(((Driver) userForUpdate).getDriverLicence());
               bDateSelector.setValue(((Driver) userForUpdate).getBDate());
               vechicleTypeBox.getSelectionModel().select(((Driver) userForUpdate).getVechicleType());
           } else if(userForUpdate instanceof Restaurant) {
               usernameField.setText(userForUpdate.getUsername());
               passwordField.setText(userForUpdate.getPassword());
               nameField.setText(userForUpdate.getName());
               surnameField.setText(userForUpdate.getSurname());
               phoneNumberField.setText(userForUpdate.getPhoneNumber());
               addressField.setText(((Restaurant) userForUpdate).getAddress());
               workHoursField.setText(((Restaurant) userForUpdate).getWorkHours());
           } else if(userForUpdate instanceof BasicUser) {
               usernameField.setText(userForUpdate.getUsername());
               passwordField.setText(userForUpdate.getPassword());
               nameField.setText(userForUpdate.getName());
               surnameField.setText(userForUpdate.getSurname());
               phoneNumberField.setText(userForUpdate.getPhoneNumber());
               addressField.setText(((BasicUser) userForUpdate).getAddress());
           } else if(userForUpdate instanceof User) {
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

    public void disableFields() {
        if(adminRadio.isSelected()) {
            clearInputFields();
            addressField.setDisable(true);
            addressField.setVisible(false);
            workHoursField.setDisable(true);
            workHoursField.setVisible(false);
            drivingLicenceField.setDisable(true);
            drivingLicenceField.setVisible(false);
            vechicleTypeBox.setDisable(true);
            vechicleTypeBox.setVisible(false);
            bDateSelector.setDisable(true);
            bDateSelector.setVisible(false);
        }
        else if(userRadio.isSelected()){
            clearInputFields();
            addressField.setDisable(false);
            addressField.setVisible(true);
            workHoursField.setDisable(true);
            workHoursField.setVisible(false);
            drivingLicenceField.setDisable(true);
            drivingLicenceField.setVisible(false);
            vechicleTypeBox.setDisable(true);
            vechicleTypeBox.setVisible(false);
            bDateSelector.setDisable(true);
            bDateSelector.setVisible(false);
        }
        else if(restaurantRadio.isSelected()){
            clearInputFields();
            addressField.setDisable(false);
            addressField.setVisible(true);
            workHoursField.setDisable(false);
            workHoursField.setVisible(true);
            drivingLicenceField.setDisable(true);
            drivingLicenceField.setVisible(false);
            vechicleTypeBox.setDisable(true);
            vechicleTypeBox.setVisible(false);
            bDateSelector.setDisable(true);
            bDateSelector.setVisible(false);
        } else if(driverRadio.isSelected()){
            clearInputFields();
            addressField.setDisable(false);
            addressField.setVisible(true);
            workHoursField.setDisable(true);
            workHoursField.setVisible(false);
            drivingLicenceField.setDisable(false);
            drivingLicenceField.setVisible(true);
            vechicleTypeBox.setDisable(false);
            vechicleTypeBox.setVisible(true);
            bDateSelector.setDisable(false);
            bDateSelector.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vechicleTypeBox.getItems().addAll(VechicleType.values());
        disableFields();
    }

    public void createNewUser() {
        if(userRadio.isSelected()) {
            BasicUser basicUser = new BasicUser(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), addressField.getText());
            genericHibernate.create(basicUser);
        } else if (restaurantRadio.isSelected()) {
            clearInputFields();
            Restaurant restaurant = new Restaurant(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), addressField.getText(), workHoursField.getText());
            genericHibernate.create(restaurant);
        } else if (driverRadio.isSelected()) {
            Driver driver = new Driver(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), addressField.getText(), drivingLicenceField.getText(), bDateSelector.getValue(), vechicleTypeBox.getValue());
            genericHibernate.create(driver);
        } else if (adminRadio.isSelected()) {
            User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), true);
            genericHibernate.create(user);
        }
    }

    public void clearInputFields(){
        usernameField.clear();
        passwordField.clear();
        nameField.clear();
        surnameField.clear();
        phoneNumberField.clear();
        addressField.clear();
        workHoursField.clear();
        bDateSelector.setValue(null);
        vechicleTypeBox.setValue(null);
        drivingLicenceField.clear();
    }

    public void updateUser() {
        if(userForUpdate instanceof Restaurant) {
            restaurantRadio.setSelected(true);
            userForUpdate.setUsername(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
            userForUpdate.setPhoneNumber(phoneNumberField.getText());
            ((Restaurant) userForUpdate).setAddress(addressField.getText());
            ((Restaurant) userForUpdate).setWorkHours(workHoursField.getText());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        } else if (userForUpdate instanceof Driver) {
            driverRadio.setSelected(true);
            userForUpdate.setUsername(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
            userForUpdate.setPhoneNumber(phoneNumberField.getText());
            ((Driver) userForUpdate).setAddress(addressField.getText());
            ((Driver) userForUpdate).setDriverLicence(drivingLicenceField.getText());
            ((Driver) userForUpdate).setVechicleType(vechicleTypeBox.getValue());
            ((Driver) userForUpdate).setBDate(bDateSelector.getValue());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        } else if(userForUpdate instanceof BasicUser) {
            userRadio.setSelected(true);
            userForUpdate.setUsername(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
            userForUpdate.setPhoneNumber(phoneNumberField.getText());
            ((BasicUser) userForUpdate).setAddress(addressField.getText());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        } else if(userForUpdate instanceof User) {
            adminRadio.setSelected(true);
            userForUpdate.setUsername(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
            userForUpdate.setPhoneNumber(phoneNumberField.getText());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        }
        genericHibernate.edit(userForUpdate);
    }
}
