package com.example.javacoursework.hibernatecontrol;

import com.example.javacoursework.fxcontrollers.FxUtils;
import com.example.javacoursework.model.*;
import jakarta.persistence.Basic;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class CustomHibernate extends GenericHibernate {
    public CustomHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public User getUserByCredentials(String username, String password)
    {
        User user = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);

            query.select(root).where(cb.and(cb.equal(root.get("username"), username), cb.equal(root.get("password"), password)));
            Query q = entityManager.createQuery(query);
            user = (User) q.getSingleResult();
        }catch(Exception e){

        }
        return user;
    }

    public List<FoodOrder> getRestaurantOrders(Restaurant restaurant){
        List<FoodOrder> foodOrders = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);

            query.select(root).where(cb.equal(root.get("restaurant"), restaurant));
            Query q = entityManager.createQuery(query);
            foodOrders = q.getResultList();
        }catch(Exception e){
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "Problem occurred when retrieving Restaurant Orders", "Check error log");
        }
        return foodOrders;
    }

    public List<BasicUser> getOnlyBasicUsers(){
        List<BasicUser> basicUsers = new ArrayList<>();
        for(BasicUser user : getAllRecords(BasicUser.class)){
            if(!(user instanceof Restaurant) && !(user instanceof Driver)){
                basicUsers.add(user);
            }
        }
        return basicUsers;
    }

    public List<FoodItem> getRestaurantFoodMenu(Restaurant restaurant){
            List<FoodItem> menu = new ArrayList<>();
            try {
                entityManager = entityManagerFactory.createEntityManager();
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<FoodItem> query = cb.createQuery(FoodItem.class);
                Root<FoodItem> root = query.from(FoodItem.class);
                query.select(root).where(cb.equal(root.get("restaurant"), restaurant));
                Query q = entityManager.createQuery(query);
                menu = q.getResultList();
            } catch (Exception e) {
                FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "Problem occurred when retrieving Restaurant Menu", "Check error log");
            }
            return menu;
    }
}

