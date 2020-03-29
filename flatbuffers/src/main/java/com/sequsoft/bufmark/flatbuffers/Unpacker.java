package com.sequsoft.bufmark.flatbuffers;

import com.sequsoft.bufmark.model.Address;
import com.sequsoft.bufmark.model.Country;
import com.sequsoft.bufmark.model.House;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.model.Person;
import com.sequsoft.bufmark.model.Sex;

import com.google.flatbuffers.StringVector;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Unpacker {

    public static Person unpackPerson(F_Person incoming) {
        Person person = Person.newPerson()
                .withId(incoming.id())
                .withName(incoming.name())
                .withAbout(incoming.about())
                .withSex(Sex.valueOf(F_Sex.name(incoming.sex())))
                .withDateOfBirth(ZonedDateTime.ofInstant(Instant.ofEpochSecond(incoming.dateOfBirth()), ZoneId.of("UTC")));

        return person;
    }

    private static List<Person> unpackPeople(F_HouseGroup hg) {
        List<Person> organisers = new ArrayList<>();
        for (int i = 0; i < hg.organisersLength(); i++) {
            organisers.add(unpackPerson(hg.organisers(i)));
        }

        return organisers;
    }

    private static List<Person> unpackPeople(F_House h) {
        List<Person> organisers = new ArrayList<>();
        for (int i = 0; i < h.occupantsLength(); i++) {
            organisers.add(unpackPerson(h.occupants(i)));
        }

        return organisers;
    }

    private static List<House> unpackHouses(F_HouseGroup hg) {
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < hg.housesLength(); i++) {
            houses.add(unpackHouse(hg.houses(i)));
        }

        return houses;
    }

    private static House unpackHouse(F_House h) {
        House house = House.newHouse()
                .withAddress(unpackAddress(h.address()))
                .withId(h.id())
                .withOccupants(unpackPeople(h));

        return house;
    }

    private static Address unpackAddress(F_Address a) {
        Address address = Address.newAddress()
                .withId(a.id())
                .withAddressLines(unpackStrings(a.addressLinesVector()))
                .withLocality(a.locality())
                .withPostcode(a.postcode())
                .withCountry(Country.valueOf(F_Country.name(a.country())));

        return address;
    }

    private static List<String> unpackStrings(StringVector vector) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < vector.length(); i++) {
            strings.add(vector.get(i));
        }

        return strings;
    }

    public static HouseGroup unpack(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        F_HouseGroup incomingHouseGroup = F_HouseGroup.getRootAsF_HouseGroup(buffer);

        HouseGroup houseGroup = HouseGroup.newHouseGroup()
                .withId(incomingHouseGroup.id())
                .withOrganisers(unpackPeople(incomingHouseGroup))
                .withHouses(unpackHouses(incomingHouseGroup));

        return houseGroup;
    }
}
