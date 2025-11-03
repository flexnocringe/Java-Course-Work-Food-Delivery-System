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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    @FXML
    public TabPane managementTabsPane;
    @FXML
    public Tab altUserManagement; //uzkistukas
    @FXML
    public ListView<User> userListView; // uzskistukas

    //<editor-fold desc="User Tab, Table And Columns">
    @FXML
    public Tab userManagementTab;
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
    public TableColumn<UserTableParameters, String> phoneNumberColumn;
    @FXML
    public TableColumn<UserTableParameters, String> addressColumn;
    @FXML
    public TableColumn<UserTableParameters, String> drivingLicenceColumn;
    @FXML
    public TableColumn<UserTableParameters, String> vechicleTypeColumn;
    @FXML
    public TableColumn<UserTableParameters, String> dateCreatedColumn;
    @FXML
    public TableColumn<UserTableParameters, String> dateUpdatedColumn;
    @FXML
    public TableColumn<UserTableParameters, String> birthDateColumn;
    @FXML
    public TableColumn<UserTableParameters, String> workHoursColumn;
    @FXML
    public TableColumn dummyColumn;
    //</editor-fold>

    @FXML
    public Tab foodManagementTab;

    //<editor-fold desc="FoodOrder Tab, Table And Columns">
    @FXML
    public Tab ordersManagementTab;
    @FXML
    public TableView<FoodOrder> foodOrderTable;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderIdColumn;
    public TableColumn<FoodOrder, String> foodOrderNameColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderRestaurantColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderPriceColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderStatusColumn;
    @FXML
    public TextField orderNameField;
    @FXML
    public TextField orderPriceField;
    @FXML
    public ComboBox<Restaurant> restaurantOrderBox;
    @FXML
    public ComboBox<BasicUser> clientOrderBox;
    @FXML
    public ComboBox<OrderStatus> statusOrderBox;
    //</editor-fold>


    private ObservableList<UserTableParameters> userObservableList = FXCollections.observableArrayList();
    private ObservableList<FoodOrder> foodOrderObservableList = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;

    private CustomHibernate customHibernate;

    private User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //<editor-fold desc="User Management Table Initialize">
        userTable.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType")); //Kaip type paimt
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTablePosition().getRow()+1);
            user.setPassword(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTablePosition().getRow()+1);
            user.setPassword(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTablePosition().getRow()+1);
            user.setSurname(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTablePosition().getRow()+1);
            user.setPhoneNumber(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        drivingLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("license"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        dateUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateUpdated"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("bDate"));
        vechicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vechicleType"));
        workHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workHours"));
        //</editor-fold>

        //<editor-fold desc="Food Order Management Table Initialize">
        statusOrderBox.getItems().addAll(OrderStatus.values());
        foodOrderTable.setEditable(true);
        foodOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        foodOrderNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodOrderRestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant"));
        foodOrderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        foodOrderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //</editor-fold>

    }
    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.customHibernate = new CustomHibernate(this.entityManagerFactory);
        setUserFormVisibility();
        reloadTableData();
    }

    private void setUserFormVisibility() {
        if(currentUser instanceof BasicUser) {
            managementTabsPane.getTabs().remove(altUserManagement);
            managementTabsPane.getTabs().remove(userManagementTab);
        } else if (currentUser instanceof Restaurant) {
            managementTabsPane.getTabs().remove(altUserManagement);
            managementTabsPane.getTabs().remove(userManagementTab);
        } else if (currentUser instanceof Driver) {
            managementTabsPane.getTabs().remove(altUserManagement);
            managementTabsPane.getTabs().remove(userManagementTab);
        } else if(currentUser instanceof User){
            managementTabsPane.getTabs().remove(altUserManagement);
        }
    }

    public void reloadTableData() {
        if(userManagementTab.isSelected()){

            //<editor-fold desc="User Management Tab Table Reload">
            userObservableList.clear();
            List<User> users = customHibernate.getAllRecords(User.class);
            for(User user : users){
                UserTableParameters userTableParameters = new UserTableParameters();
                if(user instanceof User) {
                    userTableParameters.setUserType(user.getClass().getSimpleName());
                    userTableParameters.setUsername(user.getUsername());
                    userTableParameters.setPassword(user.getPassword());
                    userTableParameters.setName(user.getName());
                    userTableParameters.setSurname(user.getSurname());
                    userTableParameters.setId(user.getId());
                    userTableParameters.setPhoneNumber(user.getPhoneNumber());
                    userTableParameters.setDateCreated(user.getDateCreated().toString());
                    userTableParameters.setDateUpdated(user.getDateUpdated().toString());
                }
                if(user instanceof BasicUser) {
                    userTableParameters.setAddress(((BasicUser)user).getAddress());
                }
                if(user instanceof Restaurant) {
                    userTableParameters.setWorkHours(((Restaurant)user).getWorkHours());
                }
                if(user instanceof Driver) {
                    userTableParameters.setbDate(String.valueOf(((Driver) user).getBDate()));
                    userTableParameters.setLicense(((Driver) user).getDriverLicence());
                    userTableParameters.setVechicleType(String.valueOf(((Driver) user).getVechicleType()));
                }

                userObservableList.add(userTableParameters);

            }
            userTable.setItems(userObservableList);
            //</editor-fold>

        } else if(ordersManagementTab.isSelected()){
            clearOrderInputFields();
            restaurantOrderBox.getItems().addAll(customHibernate.getAllRecords(Restaurant.class));
            clientOrderBox.getItems().addAll(customHibernate.getOnlyBasicUsers());
            List<FoodOrder> foodOrders = getFoodOrders();


        } else if(foodManagementTab.isSelected()){

        } else if(altUserManagement.isSelected()){
            List<User> userList = customHibernate.getAllRecords(User.class);
            userListView.getItems().clear();
            userListView.getItems().addAll(userList);
        }
    }

    private void clearOrderInputFields() {
        orderNameField.clear();
        orderPriceField.clear();
        restaurantOrderBox.setValue(null);
        clientOrderBox.setValue(null);
        statusOrderBox.setValue(null);
    }

    private List<FoodOrder> getFoodOrders() {
        if(currentUser instanceof Restaurant) {
            return  customHibernate.getRestaurantOrders((Restaurant) currentUser);
        } else {
            return customHibernate.getAllRecords(FoodOrder.class);
        }
    }

    //<editor-fold desc="User Management Tab Functionality">

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
        reloadTableData();
    }

    public void updateExistingUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("user-form.fxml"));
        Parent parent = fxmlLoader.load();
        UserForm userForm = fxmlLoader.getController();
        
        UserTableParameters selectedUser = userTable.getSelectionModel().getSelectedItem();

        userForm.setData(entityManagerFactory, convertUserTableParamsToUserClass(selectedUser), true);

        Stage stage = new Stage();
        stage.setTitle("Create new user");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        reloadTableData();
    }

    private User convertUserTableParamsToUserClass(UserTableParameters selectedUser) {
        if(selectedUser.getUserType().equals("Driver")) {
            return new Driver(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getName(), selectedUser.getSurname(), selectedUser.getPhoneNumber(), LocalDateTime.parse(selectedUser.getDateCreated()), selectedUser.getAddress(), selectedUser.getLicense(), LocalDate.parse(selectedUser.getbDate()), VechicleType.valueOf(selectedUser.getVechicleType()));
        } else if (selectedUser.getUserType().equals("Restaurant")) {
            return new Restaurant(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getName(), selectedUser.getSurname(), selectedUser.getPhoneNumber(), LocalDateTime.parse(selectedUser.getDateCreated()), selectedUser.getAddress(), selectedUser.getWorkHours());
        } else if(selectedUser.getUserType().equals("BasicUser")) {
            return new BasicUser(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getName(), selectedUser.getSurname(), selectedUser.getPhoneNumber(), LocalDateTime.parse(selectedUser.getDateCreated()), selectedUser.getAddress());
        } else {
            return new User(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getName(), selectedUser.getSurname(), selectedUser.getPhoneNumber(), LocalDateTime.parse(selectedUser.getDateCreated()));
        }
    }

    public void deleteSelectedUser(ActionEvent actionEvent) {
        UserTableParameters selectedUser = userTable.getSelectionModel().getSelectedItem();
        customHibernate.delete(User.class, selectedUser.getId());
        reloadTableData();
    }
    //</editor-fold>

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void createOrder(ActionEvent actionEvent) {
        if(isNumeric(orderPriceField.getText())) {
            List<FoodItem> dummyList = new ArrayList<>();
            FoodOrder foodOrder = new FoodOrder(orderNameField.getText(), Double.parseDouble(orderPriceField.getText()), dummyList, clientOrderBox.getValue(), restaurantOrderBox.getValue(), statusOrderBox.getValue(), LocalDateTime.now());
            customHibernate.create(foodOrder);
            reloadTableData();
        }
    }

    public void updateOrder(ActionEvent actionEvent) {
    }

    public void deleteOrder(ActionEvent actionEvent) {
    }
}
