package com.example.javacoursework.hibernatecontrol;

import com.example.javacoursework.fxcontrollers.FxUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.scene.control.Alert;
import org.hibernate.PersistentObjectException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericHibernate {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public <T> void create(T entity){
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            if(!Objects.equals(e.getMessage(), "detached entity passed to persist: com.example.javacoursework.model.FoodOrder")){
                FxUtils.generateDialogAlert(Alert.AlertType.WARNING, "Warning!", "Something went wrong during insert operation", e);
            }
        }finally{
            if(entityManager!=null)entityManager.close();
        }
    }
    public <T> void edit(T entity){
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            FxUtils.generateDialogAlert(Alert.AlertType.WARNING, "Warning!", "Something went wrong during update operation", e);
        }finally{
            if(entityManager!=null)entityManager.close();
        }
    }

    public <T> T getEntityById(Class<T> entityClass, int id){
        T entity = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity = entityManager.find(entityClass, id);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            FxUtils.generateDialogAlert(Alert.AlertType.WARNING, "Waring!", "Something went wrong during search for specific element", e);
        }finally{
            if(entityManager!=null)entityManager.close();
        }
        return entity;
    }
    public <T> void delete(Class<T> entityClass, int id){
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityClass, id);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            FxUtils.generateDialogAlert(Alert.AlertType.WARNING, "Warning!", "Something went wrong during delete operation", e);
        }finally{
            if(entityManager!=null)entityManager.close();
        }
    }

    public <T> List<T> getAllRecords(Class<T> entityClass){
        List<T> list = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        }catch(Exception e){
            FxUtils.generateDialogAlert(Alert.AlertType.WARNING, "Warning!", "Something went wrong during data extraction", e);
        }finally{
            if(entityManager!=null)entityManager.close();
        }
        return list;
    }
}
