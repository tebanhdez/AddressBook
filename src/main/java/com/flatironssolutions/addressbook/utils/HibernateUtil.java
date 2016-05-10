package com.flatironssolutions.addressbook.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory.isClosed())
            sessionFactory.openSession();
        return sessionFactory;
    }

    public static void closeSession(Session session){
        try{
            if(session.isOpen())
                session.close();
        }catch(HibernateException exception){
            //TODO: Log exception
        }
    }
}
