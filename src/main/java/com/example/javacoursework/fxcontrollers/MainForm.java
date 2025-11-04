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
import javafx.scene.input.MouseEvent;
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
    
    //<editor-fold desc="FoodOrder Tab, Table And Columns">
    @FXML
    public Tab ordersManagementTab;
    @FXML
    public TableView<FoodOrder> foodOrderTable;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderIdColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderNameColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderRestaurantColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderPriceColumn;
    @FXML
    public TableColumn<FoodOrder, String> foodOrderStatusColumn;
    @FXML
    public TableColumn<FoodOrder, String> clientOrderColumn;
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
    @FXML
    public ListView<FoodItem> foodItemForOrderListView;
    @FXML
    public Button createOrderButton;
    //</editor-fold>

    //<editor-fold desc="FoodItem Tab, Table And Columns">
    @FXML
    public Tab foodManagementTab;
    @FXML
    public TableView<FoodItem> foodItemTable;
    @FXML
    public TableColumn<FoodItem, String> foodItemNameColumn;
    @FXML
    public TableColumn<FoodItem, String> foodItemPriceColumn;
    @FXML
    public TableColumn<FoodItem, Boolean> spicyColumn;
    @FXML
    public TableColumn<FoodItem, Boolean> veganColumn;
    @FXML
    public TableColumn<FoodItem, Boolean> portionSizeColumn;
    @FXML
    public TextField foodItemTitleField;
    @FXML
    public TextArea foodItemIngridientsField;
    @FXML
    public ListView<Allergens> allergensListView;
    @FXML
    public ComboBox<Restaurant> restaurantForFoodItemBox;
    @FXML
    public CheckBox spicyCheckBox;
    @FXML
    public TextField foodItemPriceField;
    @FXML
    public ComboBox<PortionSize> portionSizeBox;
    @FXML
    public CheckBox veganCheckBox;
    //</editor-fold>

    private ObservableList<UserTableParameters> userObservableList = FXCollections.observableArrayList();
    private ObservableList<FoodOrder> foodOrderObservableList = FXCollections.observableArrayList();
    private ObservableList<FoodItem> foodItemObservableList = FXCollections.observableArrayList();

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
        foodItemForOrderListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        foodOrderTable.setEditable(true);
        foodOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        foodOrderNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodOrderRestaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant"));
        clientOrderColumn.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        foodOrderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        foodOrderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //</editor-fold>

        //<editor-fold desc="Food Item Management Table Initialize">
        portionSizeBox.getItems().addAll(PortionSize.values());
        allergensListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allergensListView.getItems().addAll(Allergens.values());
        foodItemTable.setEditable(true);
        foodItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        spicyColumn.setCellValueFactory(new PropertyValueFactory<>("spicy"));
        veganColumn.setCellValueFactory(new PropertyValueFactory<>("vegan"));
        portionSizeColumn.setCellValueFactory(new PropertyValueFactory<>("portionSize"));
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

            //<editor-fold desc="FoodOrder Management Tab Reload">
            clearOrderInputFields();
            restaurantOrderBox.getItems().clear();
            restaurantOrderBox.getItems().addAll(customHibernate.getAllRecords(Restaurant.class));
            clientOrderBox.getItems().clear();
            clientOrderBox.getItems().addAll(customHibernate.getOnlyBasicUsers());
            List<FoodOrder> foodOrders = getFoodOrders();
            foodOrderObservableList.clear();
            foodOrderObservableList.addAll(foodOrders);
            foodOrderTable.setItems(foodOrderObservableList);
            //</editor-fold>

        } else if(foodManagementTab.isSelected()){

            //<editor-fold desc="FoodItem Management Table Reload">
            clearFoodItemInputFields();
            restaurantForFoodItemBox.getItems().clear();
            restaurantForFoodItemBox.getItems().addAll(customHibernate.getAllRecords(Restaurant.class));
            if(currentUser instanceof Restaurant) {
                restaurantForFoodItemBox.setDisable(true);
                restaurantForFoodItemBox.setVisible(false);
                restaurantForFoodItemBox.setValue((Restaurant) currentUser);
                loadRestaurantMenu();
            }
            //</editor-fold>

        } else if(altUserManagement.isSelected()){
            List<User> userList = customHibernate.getAllRecords(User.class);
            userListView.getItems().clear();
            userListView.getItems().addAll(userList);
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

    //<editor-fold desc="Food Item Management Tab Functionality">
    public void createNewFoodItem(ActionEvent actionEvent) {
        try {
            FoodItem foodItem = new FoodItem(foodItemTitleField.getText(), Double.valueOf(foodItemPriceField.getText()), spicyCheckBox.isSelected(), veganCheckBox.isSelected(), restaurantForFoodItemBox.getValue(), foodItemIngridientsField.getText(), allergensListView.getSelectionModel().getSelectedItems(), portionSizeBox.getValue());
            customHibernate.create(foodItem);
            Restaurant restaurantConvenience = restaurantForFoodItemBox.getValue();
            reloadTableData();
            restaurantForFoodItemBox.setValue(restaurantConvenience);
        } catch (NumberFormatException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You want To set price to a text!", "Please insert a valid number");
        }
    }

    public void updateFoodItem(ActionEvent actionEvent) {
        try {
            FoodItem selectedFoodItem = foodItemTable.getSelectionModel().getSelectedItem();
            selectedFoodItem.setName(foodItemTitleField.getText());
            selectedFoodItem.setPrice(Double.valueOf(foodItemPriceField.getText()));
            selectedFoodItem.setIngredients(foodItemIngridientsField.getText());
            selectedFoodItem.setRestaurant(restaurantForFoodItemBox.getValue());
            selectedFoodItem.setPortionSize(portionSizeBox.getValue());
            selectedFoodItem.setSpicy(spicyCheckBox.isSelected());
            selectedFoodItem.setVegan(veganCheckBox.isSelected());
            selectedFoodItem.setAllergens(allergensListView.getSelectionModel().getSelectedItems());
            customHibernate.edit(selectedFoodItem);
            loadRestaurantMenu();
        } catch (NullPointerException e) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Warning!", "You haven't selected any FoodItem for Update", "Please select the item in order to make changes to it");
        }
    }

    public void deleteFoodItem(ActionEvent actionEvent) {
        FoodItem selectedFoodItem = foodItemTable.getSelectionModel().getSelectedItem();
        customHibernate.delete(FoodItem.class, selectedFoodItem.getId());
        Restaurant restaurantConvenience = restaurantForFoodItemBox.getValue();
        reloadTableData();
        restaurantForFoodItemBox.setValue(restaurantConvenience);
    }

    public void loadRestaurantMenu() {
        if(restaurantForFoodItemBox.getValue() != null) {
            foodItemObservableList.clear();
            foodItemObservableList.addAll(customHibernate.getRestaurantFoodMenu(restaurantForFoodItemBox.getValue()));
            foodItemTable.setItems(foodItemObservableList);
        }
    }

    private void clearFoodItemInputFields() {
        foodItemObservableList.clear();
        foodItemTable.setItems(foodItemObservableList);
        foodItemTitleField.clear();
        foodItemPriceField.clear();
        foodItemIngridientsField.clear();
        spicyCheckBox.setSelected(false);
        veganCheckBox.setSelected(false);
        portionSizeBox.setValue(null);
        allergensListView.getSelectionModel().clearSelection();
    }

    public void loadFoodItemInfo(MouseEvent mouseEvent) {
        FoodItem selectedFoodItem = foodItemTable.getSelectionModel().getSelectedItem();
        foodItemTitleField.setText(selectedFoodItem.getName());
        foodItemIngridientsField.setText(selectedFoodItem.getIngredients());
        foodItemPriceField.setText(String.valueOf(selectedFoodItem.getPrice()));
        portionSizeBox.setValue(selectedFoodItem.getPortionSize());
        spicyCheckBox.setSelected(selectedFoodItem.isSpicy());
        veganCheckBox.setSelected(selectedFoodItem.isVegan());
        allergensListView.getSelectionModel().clearSelection();
        for(Allergens allergen: selectedFoodItem.getAllergens()) {
            allergensListView.getSelectionModel().select(allergen);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Food Order Management Tab Functionality">
    private List<FoodOrder> getFoodOrders() {
        if(currentUser instanceof Restaurant) {
            return  customHibernate.getRestaurantOrders((Restaurant) currentUser);
        } else {
            return customHibernate.getAllRecords(FoodOrder.class);
        }
    }

    public void clearOrderInputFields() {
        orderNameField.clear();
        orderPriceField.clear();
        restaurantOrderBox.setValue(null);
        clientOrderBox.setValue(null);
        statusOrderBox.setValue(null);
        foodItemForOrderListView.getItems().clear();
        foodOrderTable.getSelectionModel().clearSelection();
        createOrderButton.setDisable(false);
    }

    public void createOrder(ActionEvent actionEvent) {
        try {
            FoodOrder foodOrder = new FoodOrder(orderNameField.getText(), Double.parseDouble(orderPriceField.getText()), foodItemForOrderListView.getSelectionModel().getSelectedItems(), clientOrderBox.getValue(), restaurantOrderBox.getValue(), statusOrderBox.getValue(), LocalDateTime.now());
            customHibernate.create(foodOrder);
            reloadTableData();
        } catch (NumberFormatException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You want To set price to a text!", "Please insert a valid number");
        }
    }

    public void updateOrder(ActionEvent actionEvent) {
        try {
        FoodOrder foodOrder = foodOrderTable.getSelectionModel().getSelectedItem();
        foodOrder.setName(orderNameField.getText());
        foodOrder.setPrice(Double.valueOf(orderPriceField.getText()));
        foodOrder.setRestaurant(restaurantOrderBox.getValue());
        foodOrder.setBuyer(clientOrderBox.getValue());
        foodOrder.setOrderStatus(statusOrderBox.getValue());
        foodOrder.setFoodItems(foodItemForOrderListView.getSelectionModel().getSelectedItems());
        foodOrder.setDateUpdated(LocalDateTime.now());
        customHibernate.edit(foodOrder);
        clearOrderInputFields();
        reloadTableData();
        } catch (Exception e) {
            if(e instanceof NumberFormatException) {
                FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You want To set price to a text!", "Please insert a valid number");
            } else if(e instanceof NullPointerException) {
                FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a Food Order To Update!", "Please choose an Order to proceed");
            }
        }
    }

    public void deleteOrder(ActionEvent actionEvent) {
        try {
            FoodOrder selectedOrder = foodOrderTable.getSelectionModel().getSelectedItem();
            customHibernate.delete(FoodOrder.class, selectedOrder.getId());
            reloadTableData();
        } catch (NullPointerException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a Food Order To Delete!", "Please choose an Order to proceed");
        }
    }

    public void loadRestaurantMenuForOrder() {
        if(restaurantOrderBox.getValue() != null) {
            foodItemForOrderListView.getItems().clear();
            foodItemForOrderListView.getItems().addAll(customHibernate.getRestaurantFoodMenu(restaurantOrderBox.getValue()));
        }
    }

    public void loadOrderForUpdate(MouseEvent mouseEvent) {
        FoodOrder selectedOrder = foodOrderTable.getSelectionModel().getSelectedItem();
        clientOrderBox.getItems().stream()
                .filter(c -> c.getId() == selectedOrder.getBuyer().getId())
                .findFirst()
                .ifPresent(u -> clientOrderBox.getSelectionModel().select(u));
        orderNameField.setText(selectedOrder.getName());
        orderPriceField.setText(selectedOrder.getPrice().toString());
        restaurantOrderBox.getItems().stream()
                .filter(r -> r.getId() == selectedOrder.getRestaurant().getId())
                .findFirst()
                .ifPresent(u -> restaurantOrderBox.getSelectionModel().select(u));
        statusOrderBox.getItems().stream()
                .filter(s -> s == selectedOrder.getOrderStatus())
                .findFirst()
                .ifPresent(u -> statusOrderBox.getSelectionModel().select(u));
        foodItemForOrderListView.getSelectionModel().clearSelection();
        foodItemForOrderListView.getItems().stream()
                .filter(f -> selectedOrder.getFoodItems().stream()
                        .anyMatch(item -> item.getId() == f.getId()))
                .forEach(u -> foodItemForOrderListView.getSelectionModel().select(u));
        disableFoodOrderFields();
    }

    private void disableFoodOrderFields() {
        if (statusOrderBox.getSelectionModel().getSelectedItem() == OrderStatus.COMPLETED) {
            clientOrderBox.setDisable(true);
            orderPriceField.setDisable(true);
        }
        createOrderButton.setDisable(true);
    }
    //</editor-fold>
}
