package com.sequsoft.bufmark.protobuf;

import com.sequsoft.bufmark.model.Address;
import com.sequsoft.bufmark.model.Country;
import com.sequsoft.bufmark.model.House;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.model.Person;
import com.sequsoft.bufmark.model.Sex;

import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Utils {

    private static P_Sex convertSex(Sex incoming) {
        return P_Sex.valueOf(incoming.toString());
    }

    private static P_Country convertCountry(Country incoming) {
        return P_Country.valueOf(incoming.toString());
    }

    private static Timestamp convertZonedDateTime(ZonedDateTime incoming) {
        return Timestamp.newBuilder()
                .setSeconds(incoming.toEpochSecond())
                .build();
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private <T extends Message> T nullSafe(T t) {
        return t == null ?  (T)t.getDefaultInstanceForType() : t;
    }

    private static <T> void safeSet(T value, Consumer<T> setter) {
        if(value != null) {
            setter.accept(value);
        }
    }

    public static P_Person convertPerson(Person incoming) {
        P_Person.Builder builder = P_Person.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .setName(nullSafe(incoming.getName()))
                .setAbout(nullSafe(incoming.getAbout()));

        safeSet(incoming.getSex(), s -> builder.setSex(convertSex(s)));
        safeSet(incoming.getDateOfBirth(), d -> builder.setDateOfBirth(convertZonedDateTime(d)));

        return builder.build();
    }

    public static List<P_Person> convertPeople(List<Person> people) {
        return people.stream().map(Utils::convertPerson).collect(Collectors.toList());
    }

    public static P_Address convertAddress(Address incoming) {
        P_Address.Builder builder = P_Address.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .setLocality(nullSafe(incoming.getLocality()))
                .setPostcode(nullSafe(incoming.getPostcode()));

        safeSet(incoming.getAddressLines(), l -> builder.addAllAddressLines(l));
        safeSet(incoming.getCountry(), c -> builder.setCountry(convertCountry(c)));
        return builder.build();
    }

    public static P_House convertHouse(House incoming) {
        return P_House.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .setAddress(convertAddress(incoming.getAddress()))
                .addAllOccupants(convertPeople(incoming.getOccupants()))
                .build();
    }

    public static List<P_House> convertHouses(List<House> houses) {
        return houses.stream().map(Utils::convertHouse).collect(Collectors.toList());
    }

    public static P_HouseGroup convertHouseGroup(HouseGroup incoming) {
        return P_HouseGroup.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .addAllHouses(convertHouses(incoming.getHouses()))
                .addAllOrganisers((convertPeople(incoming.getOrganisers())))
                .build();
    }
}
