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
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    @FXML
    public TabPane managementTabsPane;

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
    @FXML
    public ComboBox<String> dTypeComboBoxForFilter;
    @FXML
    public TextField usernameForFilterField;
    @FXML
    public TextField nameForFilterField;
    @FXML
    public TextField surnameForFilterField;
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
    @FXML
    public ComboBox<OrderStatus> orderStatusFilter;
    @FXML
    public Button updateOrderButton;
    @FXML
    public Button deleteOrderButton;
    @FXML
    public Button orderChatButton;
    @FXML
    public Button acceptOrderButton;
    @FXML
    public Button setForDelivery;
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
    @FXML
    public Button createFoodItemButton;
    @FXML
    public TextField priceFilterField;
    @FXML
    public CheckBox spicyFilterBox;
    @FXML
    public CheckBox veganFilterBox;
    //</editor-fold>

    //<editor-fold desc="Chat Tab, Table And Columns">
    @FXML
    public Tab chatTab;
    @FXML
    public TableView<Chat> chatTable;
    @FXML
    public TableColumn<Chat, Integer> chatIdColumn;
    @FXML
    public TableColumn<Chat, String> chatNameColumn;
    @FXML
    public TableColumn<Chat, String> chatDateCreatedColumn;
    @FXML
    public ListView<Message> chatMessagesListView;
    @FXML
    public TextArea chatMessageField;
    @FXML
    public DatePicker chatDateSelector;
    //</editor-fold>

    private ObservableList<UserTableParameters> userObservableList = FXCollections.observableArrayList();
    private ObservableList<FoodOrder> foodOrderObservableList = FXCollections.observableArrayList();
    private ObservableList<FoodItem> foodItemObservableList = FXCollections.observableArrayList();
    private ObservableList<Chat> chatObservableList = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;

    private CustomHibernate customHibernate;

    private User currentUser;

    private final TextEncryptor passwordEncryptor = Encryptors.text("whatdoyoumean", Salt.getSalt());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //<editor-fold desc="User Management Table Initialize">
        dTypeComboBoxForFilter.getItems().addAll(new String[] {"User", "BasicUser", "Restaurant", "Driver"});
        userTable.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType")); //Kaip type paimt
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, idColumn.getCellData(event.getTablePosition().getRow()));
            user.setPassword(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, idColumn.getCellData(event.getTablePosition().getRow()));
            user.setPassword(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSurname(event.getNewValue());
            System.out.println(idColumn.getCellData(event.getTablePosition().getRow()));
            User user = customHibernate.getEntityById(User.class, idColumn.getCellData(event.getTablePosition().getRow()));
            user.setSurname(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhoneNumber(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, idColumn.getCellData(event.getTablePosition().getRow()));
            user.setPhoneNumber(event.getNewValue());
            user.setDateUpdated(LocalDateTime.now());
            customHibernate.edit(user);
        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        drivingLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("license"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        dateUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateUpdated"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        vechicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vechicleType"));
        workHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workHours"));
        //</editor-fold>

        //<editor-fold desc="Food Order Management Table Initialize">
        orderChatButton.setDisable(true);
        statusOrderBox.getItems().addAll(OrderStatus.values());
        orderStatusFilter.getItems().addAll(OrderStatus.values());
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

        //<editor-fold desc="Chat Management Table Initialize">
        chatTable.setEditable(true);
        chatIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        chatNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        chatDateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
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
        if (currentUser instanceof Driver) {
            managementTabsPane.getTabs().remove(userManagementTab);
            managementTabsPane.getTabs().remove(ordersManagementTab);
            managementTabsPane.getTabs().remove(foodManagementTab);
            managementTabsPane.getTabs().remove(chatTab);
        } else if (currentUser instanceof Restaurant) {
            managementTabsPane.getTabs().remove(userManagementTab);
            managementTabsPane.getTabs().remove(chatTab);
        } else if(currentUser instanceof BasicUser) {
            managementTabsPane.getTabs().remove(userManagementTab);
            managementTabsPane.getTabs().remove(foodManagementTab);
            managementTabsPane.getTabs().remove(chatTab);
        } else if(currentUser instanceof User){
        }
    }

    public void reloadTableData() {
        if(userManagementTab.isSelected()){

            //<editor-fold desc="User Management Tab Table Reload">
            nameForFilterField.clear();
            surnameForFilterField.clear();
            usernameForFilterField.clear();
            dTypeComboBoxForFilter.setValue("");
            userObservableList.clear();
            List<User> users = customHibernate.getAllRecords(User.class);
            for(User user : users){
                UserTableParameters userTableParameters = new UserTableParameters();
                if(user instanceof User) {
                    userTableParameters.setUserType(user.getClass().getSimpleName());
                    userTableParameters.setUsername(user.getUsername());
                    userTableParameters.setPassword(passwordEncryptor.decrypt(user.getPassword()));
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
                    userTableParameters.setbDate(String.valueOf(((Driver) user).getBirthDate()));
                    userTableParameters.setLicense(((Driver) user).getDriverLicence());
                    userTableParameters.setVechicleType(String.valueOf(((Driver) user).getVechicleType()));
                }

                userObservableList.add(userTableParameters);

            }
            userTable.setItems(userObservableList);
            //</editor-fold>

        } else if(ordersManagementTab.isSelected()){

            //<editor-fold desc="FoodOrder Management Tab Reload">
            orderStatusFilter.setValue(null);
            clearOrderInputFields();
            acceptOrderButton.setDisable(true);
            acceptOrderButton.setVisible(false);
            setForDelivery.setDisable(true);
            setForDelivery.setVisible(false);
            restaurantOrderBox.getItems().clear();
            restaurantOrderBox.getItems().addAll(customHibernate.getAllRecords(Restaurant.class));
            clientOrderBox.getItems().clear();
            clientOrderBox.getItems().addAll(customHibernate.getOnlyBasicUsers());
            List<FoodOrder> foodOrders = getFoodOrders();
            foodOrderObservableList.clear();
            foodOrderObservableList.addAll(foodOrders);
            foodOrderTable.setItems(foodOrderObservableList);
            if(currentUser instanceof Restaurant) {
                restaurantOrderBox.setValue((Restaurant)currentUser);
                loadRestaurantMenuForOrder();
                restaurantOrderBox.setDisable(true);
                restaurantOrderBox.setVisible(false);
                statusOrderBox.setDisable(true);
                statusOrderBox.setVisible(false);
                setForDelivery.setVisible(true);
                acceptOrderButton.setVisible(true);
                createOrderButton.setDisable(true);
                createOrderButton.setVisible(false);
            } else if(currentUser instanceof BasicUser) {
                clientOrderBox.setValue((BasicUser)currentUser);
                clientOrderBox.setDisable(true);
                clientOrderBox.setVisible(false);
                statusOrderBox.setDisable(true);
                statusOrderBox.getSelectionModel().select(OrderStatus.OPEN);
                deleteOrderButton.setDisable(true);
            }
            //</editor-fold>

        } else if(foodManagementTab.isSelected()){

            //<editor-fold desc="FoodItem Management Table Reload">
            priceFilterField.clear();
            spicyFilterBox.setSelected(false);
            veganFilterBox.setSelected(false);
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

        } else if(chatTab.isSelected()){

            //<editor-fold desc="Chat Management Table Reload">
            chatDateSelector.setValue(null);
            chatMessagesListView.getItems().clear();
            chatTable.getSelectionModel().clearSelection();
            chatObservableList.clear();
            chatObservableList.addAll(customHibernate.getAllRecords(Chat.class));
            chatTable.setItems(chatObservableList);
            //</editor-fold>

        }
    }

    //<editor-fold desc="User Management Tab Functionality">

    public void addNewUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("user-form.fxml"));
        Parent parent = fxmlLoader.load();
        UserForm userForm = fxmlLoader.getController();
        userForm.setData(entityManagerFactory, null, false, currentUser.isAdmin());
        Stage stage = new Stage();
        stage.setTitle("Create new user");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        reloadTableData();
    }

    public void updateExistingUser(ActionEvent actionEvent) throws IOException {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("user-form.fxml"));
        Parent parent = fxmlLoader.load();
        UserForm userForm = fxmlLoader.getController();
        UserTableParameters selectedUser = userTable.getSelectionModel().getSelectedItem();
        userForm.setData(entityManagerFactory, customHibernate.getEntityById(User.class, selectedUser.getId()), true, currentUser.isAdmin());
        Stage stage = new Stage();
        stage.setTitle("Create new user");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        reloadTableData();
        } catch (NullPointerException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a User To Update!", "Please choose a User to proceed");
        }
    }

    public void deleteSelectedUser(ActionEvent actionEvent) {
        try{
        UserTableParameters selectedUser = userTable.getSelectionModel().getSelectedItem();
        customHibernate.delete(User.class, selectedUser.getId());
        reloadTableData();
        } catch (NullPointerException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a User To Delete!", "Please choose a User to proceed");
        }
    }

    public void filterUsers(ActionEvent actionEvent) {
        List<UserTableParameters> filteredUsers = new ArrayList<>();
        try {
            filteredUsers = customHibernate.filterUsers(dTypeComboBoxForFilter.getValue(), usernameForFilterField.getText(), nameForFilterField.getText(), surnameForFilterField.getText());
            userObservableList.clear();
            userObservableList.addAll(filteredUsers);
            userTable.setItems(userObservableList);
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Error!", "Error while filtering users.", e.getMessage());
        }
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
            Restaurant restaurantConvenience = restaurantForFoodItemBox.getValue();
            clearFoodItemInputFields();
            restaurantForFoodItemBox.setValue(restaurantConvenience);
            loadRestaurantMenu();
        } catch (Exception e) {
            if(e instanceof NumberFormatException) {
                FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You want To set price to a text!", "Please insert a valid number");
            } else if(e instanceof NullPointerException) {
                FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a Food Item To Update!", "Please choose an Item to proceed");
            }
        }
    }

    public void deleteFoodItem(ActionEvent actionEvent) {
        try{
        FoodItem selectedFoodItem = foodItemTable.getSelectionModel().getSelectedItem();
        customHibernate.delete(FoodItem.class, selectedFoodItem.getId());
        Restaurant restaurantConvenience = restaurantForFoodItemBox.getValue();
        reloadTableData();
        restaurantForFoodItemBox.setValue(restaurantConvenience);
        } catch (NullPointerException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a Food Item To Delete!", "Please choose an Item to proceed");
        }
    }

    public void loadRestaurantMenu() {
        if(restaurantForFoodItemBox.getValue() != null) {
            foodItemObservableList.clear();
            foodItemObservableList.addAll(customHibernate.getRestaurantFoodMenu(restaurantForFoodItemBox.getValue()));
            foodItemTable.setItems(foodItemObservableList);
        }
    }

    public void clearFoodItemInputFields() {
        foodItemObservableList.clear();
        foodItemTable.setItems(foodItemObservableList);
        foodItemTitleField.clear();
        foodItemPriceField.clear();
        foodItemIngridientsField.clear();
        if(!(currentUser instanceof Restaurant)){
            restaurantForFoodItemBox.getSelectionModel().clearSelection();
        } else{
            restaurantForFoodItemBox.getSelectionModel().select((Restaurant)currentUser);
            loadRestaurantMenu();
        }
        spicyCheckBox.setSelected(false);
        veganCheckBox.setSelected(false);
        portionSizeBox.setValue(null);
        createFoodItemButton.setDisable(false);
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
        createFoodItemButton.setDisable(true);
    }
    public void filterFoodItems(ActionEvent actionEvent) {
        List<FoodItem> filteredFoodItems = new ArrayList<>();
        try {
            filteredFoodItems = customHibernate.filterFoodItemsByCriteria(Double.valueOf(priceFilterField.getText()), spicyFilterBox.isSelected(), veganFilterBox.isSelected());
            foodItemObservableList.clear();
            foodItemObservableList.addAll(filteredFoodItems);
            foodItemTable.setItems(foodItemObservableList);
        } catch (NumberFormatException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You want To set price to a text!", "Please insert a valid number");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Food Order Management Tab Functionality">
    private List<FoodOrder> getFoodOrders() {
        if(currentUser instanceof Restaurant) {
            return customHibernate.getRestaurantOrders((Restaurant) currentUser);
        } else if(currentUser instanceof BasicUser){
            return customHibernate.getBuyerOrders((BasicUser) currentUser);
        }else {
            return customHibernate.getAllRecords(FoodOrder.class);
        }
    }

    public void clearOrderInputFields() {
        orderNameField.clear();
        orderPriceField.clear();
        restaurantOrderBox.setValue(null);
        clientOrderBox.setValue(null);
        statusOrderBox.setValue(null);
        if(currentUser instanceof  Restaurant) {
            restaurantOrderBox.setValue((Restaurant) currentUser);
            loadRestaurantMenuForOrder();
        } else if((currentUser instanceof  BasicUser) && (currentUser instanceof User)) {
            clientOrderBox.setValue((BasicUser) currentUser);
            statusOrderBox.setValue(OrderStatus.OPEN);
            createOrderButton.setDisable(false);
        }
        foodItemForOrderListView.getItems().clear();
        foodOrderTable.getSelectionModel().clearSelection();
        clientOrderBox.setDisable(false);
        orderPriceField.setDisable(false);
        orderNameField.setDisable(false);
        restaurantOrderBox.setDisable(false);
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
        if((currentUser instanceof  Restaurant) && (currentUser instanceof  BasicUser)) {
            orderChatButton.setDisable(false);
        }
        FoodOrder selectedOrder = foodOrderTable.getSelectionModel().getSelectedItem();
        if(selectedOrder.getOrderStatus() == OrderStatus.OPEN) {
            acceptOrderButton.setDisable(false);
        }
        else if(selectedOrder.getOrderStatus() == OrderStatus.ACCEPTED) {
            acceptOrderButton.setDisable(true);
            setForDelivery.setDisable(false);
        }
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

    public void acceptSelectedOrder(ActionEvent actionEvent) {
        FoodOrder selectedOrder = foodOrderTable.getSelectionModel().getSelectedItem();
        if(selectedOrder.getOrderStatus() == OrderStatus.OPEN) {
            selectedOrder.setOrderStatus(OrderStatus.ACCEPTED);
            customHibernate.edit(selectedOrder);
            reloadTableData();
        }
    }

    public void setForDeliverySelectedOrder(ActionEvent actionEvent) {
        FoodOrder selectedOrder = foodOrderTable.getSelectionModel().getSelectedItem();
        if(selectedOrder.getOrderStatus() == OrderStatus.ACCEPTED) {
            selectedOrder.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
            customHibernate.edit(selectedOrder);
            reloadTableData();
        }
    }

    private void disableFoodOrderFields() {
        if((currentUser instanceof BasicUser) && !(currentUser instanceof Restaurant)){
            orderNameField.setDisable(true);
            restaurantOrderBox.setDisable(true);
        }
        if (statusOrderBox.getSelectionModel().getSelectedItem() == OrderStatus.COMPLETED) {
            clientOrderBox.setDisable(true);
            orderPriceField.setDisable(true);
        } else {
            clientOrderBox.setDisable(false);
            orderPriceField.setDisable(false);
        }
        createOrderButton.setDisable(true);
    }

    public void loadChatForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("chat-form.fxml"));
        Parent parent = fxmlLoader.load();
        ChatForm chatForm = fxmlLoader.getController();
        chatForm.setData(entityManagerFactory, currentUser, foodOrderTable.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setTitle("Order Chat");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        reloadTableData();
    }

    public void filterOrdersByStatus(ActionEvent actionEvent) {
        List<FoodOrder> filteredOrders = new ArrayList<>();
        filteredOrders = customHibernate.filterOrdersByStatus(orderStatusFilter.getValue());
        foodOrderObservableList.clear();
        foodOrderObservableList.addAll(filteredOrders);
        foodOrderTable.setItems(foodOrderObservableList);
    }

    public void recalculateOrderPrice(MouseEvent mouseEvent) {
        List<FoodItem> selectedItems = foodItemForOrderListView.getSelectionModel().getSelectedItems();
        Double price = 0.;
        for(FoodItem item : selectedItems){
            price += item.getPrice();
        }
        orderPriceField.setText(price.toString());
    }
    //</editor-fold>

    //<editor-fold desc="Chat Management Tab Funcionality">
    public void sendMessageAsAdmin(ActionEvent actionEvent) {
        Message message = new Message(chatMessageField.getText(), LocalDateTime.now(), currentUser, chatTable.getSelectionModel().getSelectedItem());
        customHibernate.create(message);
        chatMessageField.clear();
        int selectedChat = chatTable.getSelectionModel().getSelectedIndex();
        reloadTableData();
        chatTable.getSelectionModel().select(selectedChat);
        loadChatMessages();
    }

    public void deleteChatMessage(ActionEvent actionEvent) {
        try {
            customHibernate.delete(Message.class, chatMessagesListView.getSelectionModel().getSelectedItem().getId());
            int selectedChat = chatTable.getSelectionModel().getSelectedIndex();
            reloadTableData();
            chatTable.getSelectionModel().select(selectedChat);
            loadChatMessages();
        } catch (NullPointerException e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "You have not chosen a Food Order To Delete!", "Please choose an Order to proceed");
        }
    }

    public void loadChatMessages() {
        Chat selectedChat = chatTable.getSelectionModel().getSelectedItem();
        chatMessagesListView.getItems().clear();
        chatMessagesListView.getItems().addAll(selectedChat.getMessages());
    }

    public void filterChatsByDate(ActionEvent actionEvent) {
        List<Chat> filteredChats = new ArrayList<>();
        filteredChats = customHibernate.filterChatByDate(chatDateSelector.getValue());
        chatObservableList.clear();
        chatObservableList.addAll(filteredChats);
        chatTable.setItems(chatObservableList);
    }
    //</editor-fold>
}
