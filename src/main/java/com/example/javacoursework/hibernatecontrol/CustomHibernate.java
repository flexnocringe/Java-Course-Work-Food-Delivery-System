package com.example.javacoursework.hibernatecontrol;

import com.example.javacoursework.fxcontrollers.FxUtils;
import com.example.javacoursework.fxcontrollers.UserTableParameters;
import com.example.javacoursework.model.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javafx.scene.control.Alert;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomHibernate extends GenericHibernate {
    public CustomHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public TextEncryptor passwordEncryptor = Encryptors.text("whatdoyoumean", Salt.getSalt());
    public User getUserByCredentials(String username, String password)
    {
        User user = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.equal(root.get("username"), username));
            Query q = entityManager.createQuery(query);
            user = (User) q.getSingleResult();
            System.out.println(passwordEncryptor.decrypt(user.getPassword()));
            System.out.println(password);
            if(!passwordEncryptor.decrypt(user.getPassword()).equals(password)){
                return null;
            }
        }catch(Exception e){
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Error!", "Something went wrong during login", "No such user or wrong credentials");
            e.printStackTrace();
        }finally{
            if(entityManager!=null)entityManager.close();
        }
        return user;
    }

    public List<FoodOrder> getRestaurantOrders(Restaurant restaurant){
        List<FoodOrder> foodOrders = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);
            query.select(root).where(cb.equal(root.get("restaurant"), restaurant));
            Query q = entityManager.createQuery(query);
            foodOrders = q.getResultList();
        }catch(Exception e){
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "Problem occurred when retrieving Restaurant Orders", "Check error log");
            e.printStackTrace();
        }finally{
            if(entityManager!=null)entityManager.close();
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
                e.printStackTrace();
            }finally{
                if(entityManager!=null)entityManager.close();
            }
            return menu;
    }

    public List<FoodOrder> getBuyerOrders(BasicUser buyer) {
        List<FoodOrder> foodOrders = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);
            query.select(root).where(cb.equal(root.get("buyer"), buyer));
            Query q = entityManager.createQuery(query);
            foodOrders = q.getResultList();
        }catch(Exception e){
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "Problem occurred when retrieving Restaurant Orders", "Check error log");
            e.printStackTrace();
        }finally{
            if(entityManager!=null)entityManager.close();
        }
        return foodOrders;
    }

    public List<Chat> filterChatByDate(LocalDate date) {
        List<Chat> chats = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Chat> query = cb.createQuery(Chat.class);
            Root<Chat> root = query.from(Chat.class);

            query.select(root).where(cb.greaterThanOrEqualTo(root.get("dateCreated"), date));
            Query q = entityManager.createQuery(query);

            chats = q.getResultList();
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "There was something wrong during filtration process" , "Check your filter parameters and resubmit");
        } finally {
            if (entityManager != null) { entityManager.close(); }
        }
        return chats;
    }

    public List<FoodOrder> filterOrdersByStatus(OrderStatus value) {
        List<FoodOrder> foodOrders = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);

            query.select(root).where(cb.equal(root.get("orderStatus"), value));
            Query q = entityManager.createQuery(query);

            foodOrders = q.getResultList();
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "There was something wrong during filtration process" , "Check your filter parameters and resubmit");
        } finally {
            if (entityManager != null) { entityManager.close(); }
        }
        return foodOrders;
    }

    public List<FoodItem> filterFoodItemsByCriteria(Double price, boolean spicy, boolean vegan) {
        List<FoodItem> foodItems = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodItem> query = cb.createQuery(FoodItem.class);
            Root<FoodItem> root = query.from(FoodItem.class);

            query.select(root).where(cb.and(cb.and(cb.greaterThanOrEqualTo(root.get("price"), price), cb.equal(root.get("spicy"), spicy)), cb.equal(root.get("vegan"), vegan)));
            Query q = entityManager.createQuery(query);

            foodItems = q.getResultList();
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Warning!", "There was something wrong during filtration process" , "Check your filter parameters and resubmit");
        } finally {
            if (entityManager != null) { entityManager.close(); }
        }
        return foodItems;
    }

    public List<UserTableParameters> filterUsers(String userType, String username, String name, String surname) {
        entityManager = entityManagerFactory.createEntityManager();
        List<User> users = new ArrayList<>();
        List<UserTableParameters> filteredUsers = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!userType.isBlank()) {
            Class<? extends User> type = switch (userType) {
                case "Driver" -> Driver.class;
                case "BasicUser" -> BasicUser.class;
                case "Restaurant" -> Restaurant.class;
                default -> User.class;
            };
            predicates.add(cb.equal(root.type(), type));
        }
        if (!username.isBlank()) {
            predicates.add(cb.equal(root.get("username"), username));
        }
        if(!name.isBlank()) {
            predicates.add(cb.equal(root.get("name"), name));
        }
        if(!surname.isBlank()) {
            predicates.add(cb.equal(root.get("surname"), surname));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<User> q = entityManager.createQuery(query);
        users = q.getResultList();
        System.out.println(users);
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
                userTableParameters.setBirthDate(String.valueOf(((Driver) user).getBirthDate()));
                userTableParameters.setLicense(((Driver) user).getDriverLicence());
                userTableParameters.setVechicleType(String.valueOf(((Driver) user).getVechicleType()));
            }
            filteredUsers.add(userTableParameters);
        }
        return filteredUsers;
    }
}

