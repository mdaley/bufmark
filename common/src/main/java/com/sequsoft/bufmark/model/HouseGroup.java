package com.sequsoft.bufmark.model;

import java.util.List;

public class HouseGroup {
    private String id;
    private List<House> houses;
    private List<Person> organisers;

    private HouseGroup() {
    }

    public static HouseGroup newHouseGroup() {
        return new HouseGroup();
    }

    public HouseGroup withId(String id) {
        this.id = id;
        return this;
    }

    public HouseGroup withOrganisers(List<Person> organisers) {
        this.organisers = organisers;
        return this;
    }

    public HouseGroup withHouses(List<House> houses) {
        this.houses = houses;
        return this;
    }

    public String getId() {
        return id;
    }

    public List<House> getHouses() {
        return houses;
    }

    public List<Person> getOrganisers() {
        return organisers;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("id", id)
                .append("houses", houses)
                .append("organisers", organisers)
                .toString();
    }
}
