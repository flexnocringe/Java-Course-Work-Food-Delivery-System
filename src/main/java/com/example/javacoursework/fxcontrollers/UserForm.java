package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.hibernatecontrol.GenericHibernate;
import com.example.javacoursework.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.net.URL;
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
    @FXML
    public HBox userSelectionButtons;

    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;
    private User userForUpdate;
    private boolean isForUpdate;
    private final String salt = Salt.getSalt();
    private final TextEncryptor passwordEncryptor = Encryptors.text("whatdoyoumean", salt);

    public void setData(EntityManagerFactory entityManagerFactory, User user, boolean isForUpdate, boolean isAdmin) {
        if(!isAdmin){
            restaurantRadio.setSelected(true);
            disableFields();
            userSelectionButtons.setDisable(true);
            userSelectionButtons.setVisible(false);
        }
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
                driverRadio.setSelected(true);
                disableFields();
               fillBasicAttributesForUpdate();
               addressField.setText(((Driver) userForUpdate).getAddress());
               drivingLicenceField.setText(((Driver) userForUpdate).getDriverLicence());
               bDateSelector.setValue(((Driver) userForUpdate).getBirthDate());
               vechicleTypeBox.getSelectionModel().select(((Driver) userForUpdate).getVechicleType());
           } else if(userForUpdate instanceof Restaurant) {
                restaurantRadio.setSelected(true);
                disableFields();
                fillBasicAttributesForUpdate();
                addressField.setText(((Restaurant) userForUpdate).getAddress());
               workHoursField.setText(((Restaurant) userForUpdate).getWorkHours());
           } else if(userForUpdate instanceof BasicUser) {
                userRadio.setSelected(true);
                disableFields();
                fillBasicAttributesForUpdate();
                addressField.setText(((BasicUser) userForUpdate).getAddress());
           } else if(userForUpdate instanceof User) {
                adminRadio.setSelected(true);
                disableFields();
                fillBasicAttributesForUpdate();
            }
        } else {
            updateUserButton.setDisable(true);
            updateUserButton.setVisible(false);
        }
    }

    private void fillBasicAttributesForUpdate() {
        usernameField.setText(userForUpdate.getUsername());
        passwordField.setText(passwordEncryptor.decrypt(userForUpdate.getPassword()));
        nameField.setText(userForUpdate.getName());
        surnameField.setText(userForUpdate.getSurname());
        phoneNumberField.setText(userForUpdate.getPhoneNumber());
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
        String encryptedPassword = passwordEncryptor.encrypt(passwordField.getText());
        if(userRadio.isSelected()) {
            BasicUser basicUser = new BasicUser(usernameField.getText(), encryptedPassword, nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), addressField.getText());
            genericHibernate.create(basicUser);
            clearInputFields();
        } else if (restaurantRadio.isSelected()) {
            Restaurant restaurant = new Restaurant(usernameField.getText(), encryptedPassword, nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), addressField.getText(), workHoursField.getText());
            genericHibernate.create(restaurant);
            clearInputFields();
        } else if (driverRadio.isSelected()) {
            Driver driver = new Driver(usernameField.getText(), encryptedPassword, nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), addressField.getText(), drivingLicenceField.getText(), bDateSelector.getValue(), vechicleTypeBox.getValue());
            genericHibernate.create(driver);
            clearInputFields();
        } else if (adminRadio.isSelected()) {
            User user = new User(usernameField.getText(), encryptedPassword, nameField.getText(), surnameField.getText(), phoneNumberField.getText(), LocalDateTime.now(), true);
            genericHibernate.create(user);
            clearInputFields();
        }
        System.out.println(passwordEncryptor.decrypt(encryptedPassword));
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
            setBasicAttributesForUpdate();
            ((Restaurant) userForUpdate).setAddress(addressField.getText());
            ((Restaurant) userForUpdate).setWorkHours(workHoursField.getText());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        } else if (userForUpdate instanceof Driver) {
            setBasicAttributesForUpdate();
            ((Driver) userForUpdate).setAddress(addressField.getText());
            ((Driver) userForUpdate).setDriverLicence(drivingLicenceField.getText());
            ((Driver) userForUpdate).setVechicleType(vechicleTypeBox.getValue());
            ((Driver) userForUpdate).setBirthDate(bDateSelector.getValue());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        } else if(userForUpdate instanceof BasicUser) {
            setBasicAttributesForUpdate();
            ((BasicUser) userForUpdate).setAddress(addressField.getText());
            userForUpdate.setDateUpdated(LocalDateTime.now());
        } else if(userForUpdate instanceof User) {
            setBasicAttributesForUpdate();
            userForUpdate.setDateUpdated(LocalDateTime.now());
        }
        genericHibernate.edit(userForUpdate);
        Stage stage = (Stage) userCreationButton.getScene().getWindow();
        stage.close();
    }

    private void setBasicAttributesForUpdate() {
        String encryptedPassword = passwordEncryptor.encrypt(passwordField.getText());
        userForUpdate.setUsername(usernameField.getText());
        userForUpdate.setPassword(encryptedPassword);
        userForUpdate.setName(nameField.getText());
        userForUpdate.setSurname(surnameField.getText());
        userForUpdate.setPhoneNumber(phoneNumberField.getText());
    }
}
