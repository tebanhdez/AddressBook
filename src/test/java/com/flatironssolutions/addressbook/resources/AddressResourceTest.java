package com.flatironssolutions.addressbook.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flatironssolutions.addressbook.models.Address;
import com.flatironssolutions.addressbook.services.AddressService;

public class AddressResourceTest extends JerseyTest {

    
    private final int id = 9999;
    private final String PERSON_NAME = "Esteban";
    private final String PERSON_LASTNAME = "Hernandez";
    private final String STREET_NAME = "Tres Rios, La Union";
    private final String ZIP_CODE = "11801";
    private final String CITY = "San Jose";
    
    private Address address;
    private AddressService addressService = AddressService.getInstance();


    private List<Address> inserTestAddresses(int count) {
        List<Address> testAddresses = new ArrayList<>();
        //int index = 10;
        for(;count > 0; count--){
            Address testAddress = new Address();
            testAddress.setPersonName(PERSON_NAME);
            testAddress.setPersonLastname(PERSON_LASTNAME);
            testAddress.setStreetName(STREET_NAME);
            testAddress.setZipCode(ZIP_CODE);
            testAddress.setCity(CITY);
            addressService.saveOrUpdateAddress(testAddress);
            testAddresses.add(testAddress);
        }
        return testAddresses;
    }

    @Override
    protected Application configure() {
      return new ResourceConfig(AddressResource.class);
    }

    @Test
    public void testGetAllAddresses() {
        int addressListSize = 5;
        inserTestAddresses(addressListSize);
        final Response responseMsg = target().path("address").request().get();
        Assert.assertEquals(200, responseMsg.getStatus());
        List<Address> addresses = responseMsg.readEntity(new GenericType<List<Address>>(){});
        Assert.assertEquals(addressListSize, addresses.size());
    }
}
