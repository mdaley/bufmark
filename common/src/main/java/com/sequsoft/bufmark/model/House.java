package com.sequsoft.bufmark.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

public class House {
    private String id;
    private Address address;
    private List<Person> occupants;
    private Map<String, String> metadata;

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

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public House withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("address", address)
                .append("occupants", occupants)
                .append("metadata", metadata)
                .toString();
    }
}
