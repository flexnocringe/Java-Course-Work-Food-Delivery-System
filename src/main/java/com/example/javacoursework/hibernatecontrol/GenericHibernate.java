package com.example.javacoursework.hibernatecontrol;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.List;

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

        }finally{
            if(entityManager!=null)entityManager.close();
        }
    }

    public <T> T getEntityById(Class<T> entityClass, String id){
        T entity = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity = entityManager.find(entityClass, id);
            entityManager.getTransaction().commit();
        }catch(Exception e){

        }finally{
            if(entityManager!=null)entityManager.close();
        }
        return entity;
    }
    public <T> void delete(Class<T> entityClass, String id){
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityClass, id);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        }catch(Exception e){

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

        }finally{
            if(entityManager!=null)entityManager.close();
        }
        return list;
    }
}
