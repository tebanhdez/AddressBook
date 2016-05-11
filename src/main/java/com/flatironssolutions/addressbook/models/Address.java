package com.flatironssolutions.addressbook.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Address", schema="public")
@XmlRootElement
public class Address {
    //full name of a person, street name, zip code and city.
    public Address(){
    }
    
    public Address(int id){
        setId(id);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "person_name")
    private String personName;
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
    @Column(name = "person_lastname")
    private String personLastName;
    public String getPersonLastname() {
        return personLastName;
    }
    public void setPersonLastname(String personLastname) {
        this.personLastName = personLastname;
    }
    
    @Column(name = "street_name")
    private String streetName;
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    @Column(name = "zip_code")
    private String zipCode;
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Column(name = "city")
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address book = (Address) o;
        if (id != book.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + (personName != null ? personName.hashCode() : 0);
        result = 31 * result + (personLastName != null ? personLastName.hashCode() : 0);
        result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
