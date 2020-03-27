package com.sequsoft.bufmark.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.ZonedDateTime;

public class Person {
    private String id;
    private String name;
    private Sex sex;
    private ZonedDateTime dateOfBirth;
    private String about;

    private Person() {
    }

    public static Person newPerson() {
        return new Person();
    }

    public Person withId(String id) {
        this.id = id;
        return this;
    }

    public Person withName(String name) {
        this.name = name;
        return this;
    }

    public Person withSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public Person withDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public Person withAbout(String about) {
        this.about = about;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAbout() {
        return about;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("sex", sex)
                .append("dateOfBirth", dateOfBirth)
                .append("about", about)
                .toString();
    }
}
