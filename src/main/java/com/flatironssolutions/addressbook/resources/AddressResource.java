package com.flatironssolutions.addressbook.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.flatironssolutions.addressbook.models.Address;
import com.flatironssolutions.addressbook.services.AddressService;


@Path("address")
public class AddressResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressList(@QueryParam("offset") int offset,
                                @QueryParam("count") int count) throws Exception {
        List<Address> result = AddressService.getInstance().getAllAddress();//(offset, count);
        GenericEntity<List<Address>> list = new GenericEntity<List<Address>>(result) {};
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getAddress(@PathParam("id") int id) throws Exception {
        Address address = AddressService.getInstance().getAddress(id);
        return Response.ok(address).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) throws Exception {
        AddressService.getInstance().deleteAddress(id);
        return Response.ok().build();
    }

    @DELETE
    public Response deleteAllAddress() throws Exception {
        AddressService.getInstance().deleteAllAddress();
        return Response.ok().build();
    }

    @PUT
    public Response putAddress(Address newAddress) throws Exception {
        AddressService.getInstance().saveOrUpdateAddress(newAddress);
        return Response.ok().build();
    }
}