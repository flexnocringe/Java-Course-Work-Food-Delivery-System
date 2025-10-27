package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import com.example.javacoursework.hibernatecontrol.CustomHibernate;
import com.example.javacoursework.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    @FXML
    public Tab userManagementTab;
    @FXML
    public Tab ordersManagementTab;
    @FXML
    public Tab foodManagementTab;
    @FXML
    public Tab altUserManagement; //uzkistukas
    @FXML
    public ListView<User> userListView;
    @FXML
    public TabPane managementTabsPane;
    @FXML
    public TableColumn<User, Integer> idColumn;
    @FXML
    public TableView<UserTableParameters> userTable;
    @FXML
    public TableColumn<UserTableParameters, String> userTypeColumn;
    @FXML
    public TableColumn<UserTableParameters, String> usernameColumn;
    @FXML
    public TableColumn<UserTableParameters, String> passwordColumn;
    @FXML
    public TableColumn<UserTableParameters, String> nameColumn;
    @FXML
    public TableColumn<UserTableParameters, String> surnameColumn;
    @FXML
    public TableColumn dummyColumn;

    private ObservableList<UserTableParameters> userObservableList = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;

    private CustomHibernate customHibernate;

    private User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTable.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType")); //Kaip type paimt
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTablePosition().getRow());
            user.setPassword(event.getNewValue());
            customHibernate.edit(user);
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTablePosition().getRow());
            user.setPassword(event.getNewValue());
            customHibernate.edit(user);
        });

    }
    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        setUserFormVisibility();
        this.customHibernate = new CustomHibernate(this.entityManagerFactory);
        reloadTableData();
    }

    private void setUserFormVisibility() {
        if(currentUser instanceof BasicUser) {
            managementTabsPane.getTabs().remove(altUserManagement);
        } else if (currentUser instanceof Restaurant) {
            managementTabsPane.getTabs().remove(altUserManagement);
        } else if (currentUser instanceof Driver) {
            managementTabsPane.getTabs().remove(altUserManagement);
        } else if(currentUser instanceof User){

        }
    }

    public void reloadTableData() {
        if(userManagementTab.isSelected()){
            List<User> users = customHibernate.getAllRecords(User.class);
            for(User user : users){
                UserTableParameters userTableParameters = new UserTableParameters();
                userTableParameters.setUserType(user.getClass().getSimpleName());
                userTableParameters.setUsername(user.getUsername());
                userTableParameters.setPassword(user.getPassword());
                userTableParameters.setName(user.getName());
                userTableParameters.setSurname(user.getSurname());
                userTableParameters.setId(user.getId());

                userObservableList.add(userTableParameters);

            }
            userTable.setItems(userObservableList);

        } else if(ordersManagementTab.isSelected()){
            List<FoodOrder> foodOrders = getFoodOrders();


        } else if(foodManagementTab.isSelected()){

        } else if(altUserManagement.isSelected()){
            List<User> userList = customHibernate.getAllRecords(User.class);
            userListView.getItems().clear();
            userListView.getItems().addAll(userList);
        }
    }

    private Object getUserType(User user) {
        if(user instanceof BasicUser) {
            return (BasicUser) user;
        } else if (user instanceof Restaurant) {
            return (Restaurant) user;
        } else if (user instanceof Driver) {
            return  (Driver) user;
        } else{
            return (User) user;
        }
    }

    private List<FoodOrder> getFoodOrders() {
        if(currentUser instanceof Restaurant) {
            return  customHibernate.getRestaurantOrders((Restaurant) currentUser);
        } else {
            return customHibernate.getAllRecords(FoodOrder.class);
        }
    }


    //<editor-fold desc="Alt Tab Functionality">

    public void addNewUser() throws IOException {
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

    public void updateExistingUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("user-form.fxml"));
        Parent parent = fxmlLoader.load();

        User selectedUser = userListView.getSelectionModel().getSelectedItem();

        UserForm userForm = fxmlLoader.getController();
        userForm.setData(entityManagerFactory, selectedUser, true);

        Stage stage = new Stage();
        stage.setTitle("Create new user");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        reloadTableData();
    }

    public void deleteSelectedUser(ActionEvent actionEvent) {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        customHibernate.delete(User.class, selectedUser.getId());
        reloadTableData();
    }

    //</editor-fold>
}
