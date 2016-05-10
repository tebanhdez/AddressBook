package com.flatironssolutions.addressbook.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.flatironssolutions.addressbook.models.Address;
import com.flatironssolutions.addressbook.utils.HibernateUtil;

public class AddressService {
    private static volatile AddressService instance = null;
    private static final Log LOGGER = LogFactory.getLog(AddressService.class);
    
    private AddressService() { }

    public static synchronized AddressService getInstance() {
        if (instance == null) {
            instance = new AddressService();
        }
        return instance;
    }

    public void deleteAllAddress() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.createQuery("delete from Address").executeUpdate();
        //session.close();
    }

    public List<Address> getAllAddress() {
        return getAllAddress(0, 0);
    }
    public List<Address> getAllAddress(int firstResult, int maxResult) {
        List<Address> addresses = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        if(transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE))
            LOGGER.debug(" >>> Transaction close.");
        Query query = session.createQuery("from Address");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        @SuppressWarnings("unchecked")
        List allAddresses = query.list();
        transaction.commit();
        for (Object addressObject : allAddresses) {
            Address address = (Address) addressObject;
            addresses.add(address);
        }
        //session.close();
        return addresses;
    }

    public Address getAddress(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Address address = (Address) session.get(Address.class, id);
        //session.close();
        return address;
    }

    public void saveOrUpdateAddress(Address address) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(address);
        transaction.commit();
    }

    public Address deleteAddress(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Address address = getAddress(id);
        if (address != null) {
            session.delete(address);
            session.flush();
        }
        //session.close();
        return address;
    }
}
