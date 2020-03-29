package com.sequsoft.bufmark.protobuf;

import com.sequsoft.bufmark.model.Address;
import com.sequsoft.bufmark.model.Country;
import com.sequsoft.bufmark.model.House;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.model.Person;
import com.sequsoft.bufmark.model.Sex;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class Unpacker {
    private static Person unpackPerson(P_Person incoming) {
        Person person = Person.newPerson()
                .withId(incoming.getId())
                .withSex(Sex.valueOf(incoming.getSex().name()))
                .withName(incoming.getName())
                .withAbout(incoming.getAbout())
                .withDateOfBirth(ZonedDateTime.ofInstant(Instant
                        .ofEpochSecond(incoming.getDateOfBirth().getSeconds()), ZoneId.of("UTC")));
        return person;
    }

    private static Address unpackAddress(P_Address incoming) {
        return Address.newAddress()
                .withId(incoming.getId())
                .withAddressLines(incoming.getAddressLinesList())
                .withLocality(incoming.getLocality())
                .withPostcode(incoming.getPostcode())
                .withCountry(Country.valueOf(incoming.getCountry().name()));
    }

    private static House unpackHouse(P_House incoming) {
        return House.newHouse()
                .withId(incoming.getId())
                .withAddress(unpackAddress(incoming.getAddress()))
                .withOccupants(incoming.getOccupantsList().stream()
                        .map(Unpacker::unpackPerson).collect(Collectors.toList()));
    }

    public static HouseGroup unpack(P_HouseGroup incoming) {
        return HouseGroup.newHouseGroup()
                .withId(incoming.getId())
                .withOrganisers(incoming.getOrganisersList().stream()
                        .map(Unpacker::unpackPerson).collect(Collectors.toList()))
                .withHouses(incoming.getHousesList().stream().map(Unpacker::unpackHouse).collect(Collectors.toList()));
    }
}
