package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.TestApplication;
import com.example.javacoursework.hibernatecontrol.CustomHibernate;
import com.example.javacoursework.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
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

    private EntityManagerFactory entityManagerFactory;

    private CustomHibernate customHibernate;

    private User currentUser;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        setUserFormVisibility();
        this.customHibernate = new CustomHibernate(this.entityManagerFactory);
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

        } else if(ordersManagementTab.isSelected()){
            List<FoodOrder> foodOrders = getFoodOrders();

        } else if(foodManagementTab.isSelected()){

        } else if(altUserManagement.isSelected()){
            List<User> userList = customHibernate.getAllRecords(User.class);
            userListView.getItems().clear();
            userListView.getItems().addAll(userList);
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
