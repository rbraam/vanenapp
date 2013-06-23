/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Roy Braam
 */
public class PersistenceManager implements ServletContextListener {
    private EntityManager entityManager=null;
    private EntityManagerFactory factory;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        factory = Persistence.createEntityManagerFactory("vanenappPU");
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (this.entityManager!=null &&
            this.entityManager.isOpen()){
            this.entityManager.close();
        }
        if (this.factory!= null && this.factory.isOpen()){
            this.factory.close();
        }
    }
    
    public EntityManager getEntityManager(){
        if (this.entityManager ==null){
            this.entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
}
