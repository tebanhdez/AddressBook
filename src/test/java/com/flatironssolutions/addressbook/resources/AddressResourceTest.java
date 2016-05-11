package com.flatironssolutions.addressbook.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    private List<Address> testAddress;
    private AddressService addressService = AddressService.getInstance();


    private List<Address> inserTestAddresses(int count) {
        List<Address> testAddresses = new ArrayList<>();
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
        List<Address> testAddress = inserTestAddresses(5);
        Assert.assertTrue(testAddress.size() == 5);
        final Response response = target().path("address").request().get();
        Assert.assertEquals(200, response.getStatus());
        List<Address> addressesList = response.readEntity(new GenericType<List<Address>>(){});
        Assert.assertEquals(testAddress.size(), addressesList.size());
    }
    
    @Test
    public void testGetSingleAddress(){
        List<Address> testAddress = inserTestAddresses(1);
        Assert.assertTrue(testAddress.size() > 0);
        Address toCompare = testAddress.get(0);
        String path = "address/%d";
        final Response response = target()
                .path(String.format(path, toCompare.getId()))
                .request().get();
        Assert.assertEquals(200, response.getStatus());
        Address address = response.readEntity(Address.class);
        Assert.assertTrue("Object do not match", address.equals(toCompare));
        addressService.deleteAddress(toCompare.getId());
    }

    @Test
    public void testDeleteAddress(){
        List<Address> testAddress = inserTestAddresses(1);
        Assert.assertTrue(testAddress.size() > 0);
        Address toDelete = testAddress.get(0);
        String path = "address/%d";
        final Response response = target()
                .path(String.format(path, toDelete.getId()))
                .request().delete();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNull("Object still persist", addressService.getAddress(toDelete.getId()));
    }

    @Test
    public void testEditAddress(){
        List<Address> testAddress = inserTestAddresses(1);
        Assert.assertTrue(testAddress.size() > 0);
        Address toUpdate = testAddress.get(0);
        toUpdate.setPersonName("Modified PersonName");
        toUpdate.setPersonLastname("Modified PersonLastName");
        final Response response = target()
                .path("address")
                .request().put(Entity.json(toUpdate), Response.class);
        Assert.assertEquals(200, response.getStatus());
        Address modifiedAddress = AddressService.getInstance().getAddress(toUpdate.getId());
        Assert.assertTrue("Not the same object", modifiedAddress.equals(toUpdate));
        Assert.assertNotEquals("Name not modified", PERSON_NAME, modifiedAddress.getPersonName());
        Assert.assertNotEquals("Lastname not modified", PERSON_LASTNAME, modifiedAddress.getPersonLastname());
    }

    @Test
    public void testDeleteAllAddresses(){
        List<Address> address = inserTestAddresses(10);
        final Response response = target()
                .path("address")
                .request().delete();
        Assert.assertEquals(200, response.getStatus());
        address = AddressService.getInstance().getAllAddress();
        Assert.assertTrue(address.size() == 0);
    }
}
