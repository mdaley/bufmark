package com.sequsoft.bufmark.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Address {
    private String id;
    private List<String> addressLines;
    private String locality;
    private String postcode;
    private Country country;

    private Address() {

    }

    public static Address newAddress() {
        return new Address();
    }

    public Address withId(String id) {
        this.id = id;
        return this;
    }

    public Address withAddressLines(List<String> addressLines) {
        this.addressLines = addressLines;
        return this;
    }

    public Address withLocality(String locality) {
        this.locality = locality;
        return this;
    }

    public Address withPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public Address withCountry(Country country) {
        this.country = country;
        return this;
    }

    public String getId() {
        return id;
    }

    public List<String> getAddressLines() {
        return addressLines;
    }

    public String getLocality() {
        return locality;
    }

    public String getPostcode() {
        return postcode;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("addressLines", addressLines)
                .append("locality", locality)
                .append("postcode", postcode)
                .append("country", country)
                .toString();
    }
}
