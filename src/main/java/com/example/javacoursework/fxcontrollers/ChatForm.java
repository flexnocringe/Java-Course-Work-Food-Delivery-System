package com.example.javacoursework.fxcontrollers;

import com.example.javacoursework.hibernatecontrol.CustomHibernate;
import com.example.javacoursework.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChatForm {
    @FXML
    public ListView<Review> chatMessageListView;
    @FXML
    public TextArea chatMessageTextField;

    private EntityManagerFactory entityManagerFactory;
    private CustomHibernate customHibernate;
    private User currentUser;
    private FoodOrder currentFoodOrder;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, FoodOrder currentFoodOrder) {
        this.entityManagerFactory = entityManagerFactory;
        this.customHibernate = new CustomHibernate(this.entityManagerFactory);
        this.currentUser = currentUser;
        this.currentFoodOrder = currentFoodOrder;
        loadChatMessages();
    }

    private void loadChatMessages() {
        if (currentFoodOrder.getChat() != null) {
            chatMessageListView.getItems().clear();
            chatMessageListView.getItems().addAll(currentFoodOrder.getChat().getMessages());
        }
    }

    public void sendMessage(ActionEvent actionEvent) {
        if (currentFoodOrder.getChat() == null) {
            Chat chat = new Chat("Chat for order: " + currentFoodOrder.getName(), LocalDate.now(), currentFoodOrder);
            customHibernate.create(chat);
            currentFoodOrder.setChat(chat);
            customHibernate.edit(currentFoodOrder);
        }
        Review message = new Review(chatMessageTextField.getText(), LocalDateTime.now(), (BasicUser) currentUser, currentFoodOrder.getChat());
        customHibernate.create(message);
        chatMessageListView.getItems().add(message);
        chatMessageTextField.clear();
    }
}