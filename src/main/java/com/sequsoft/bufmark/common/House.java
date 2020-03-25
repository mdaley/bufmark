package com.sequsoft.bufmark.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class House {
    private String id;
    private Address address;
    private List<Person> occupants;

    private House() {

    }

    public static House newHouse() {
        return new House();
    }


    public House withId(String id) {
        this.id = id;
        return this;
    }

    public House withAddress(Address address) {
        this.address = address;
        return this;
    }

    public House withOccupants(List<Person> occupants) {
        this.occupants = occupants;
        return this;
    }

    public String getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public List<Person> getOccupants() {
        return occupants;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("address", address)
                .append("occupants", occupants)
                .toString();
    }
}
