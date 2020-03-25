package com.sequsoft.bufmark.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Utils {

    private static Sex convertSex(com.sequsoft.bufmark.common.Sex incoming) {
        return Sex.valueOf(incoming.toString());
    }

    private static Country convertCountry(com.sequsoft.bufmark.common.Country incoming) {
        return Country.valueOf(incoming.toString());
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

    public static Person convertPerson(com.sequsoft.bufmark.common.Person incoming) {
        Person.Builder builder = Person.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .setName(nullSafe(incoming.getName()))
                .setAbout(nullSafe(incoming.getAbout()));

        safeSet(incoming.getSex(), s -> builder.setSex(convertSex(s)));
        safeSet(incoming.getDateOfBirth(), d -> builder.setDateOfBirth(convertZonedDateTime(d)));

        return builder.build();
    }

    public static List<Person> convertPeople(List<com.sequsoft.bufmark.common.Person> people) {
        return people.stream().map(Utils::convertPerson).collect(Collectors.toList());
    }

    public static Address convertAddress(com.sequsoft.bufmark.common.Address incoming) {
        Address.Builder builder = Address.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .setLocality(nullSafe(incoming.getLocality()))
                .setPostcode(nullSafe(incoming.getPostcode()));

        safeSet(incoming.getAddressLines(), l -> builder.addAllAddressLines(l));
        safeSet(incoming.getCountry(), c -> builder.setCountry(convertCountry(c)));
        return builder.build();
    }

    public static House convertHouse(com.sequsoft.bufmark.common.House incoming) {
        return House.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .setAddress(convertAddress(incoming.getAddress()))
                .addAllOccupants(convertPeople(incoming.getOccupants()))
                .build();
    }

    public static List<House> convertHouses(List<com.sequsoft.bufmark.common.House> houses) {
        return houses.stream().map(Utils::convertHouse).collect(Collectors.toList());
    }

    public static HouseGroup convertHouseGroup(com.sequsoft.bufmark.common.HouseGroup incoming) {
        return HouseGroup.newBuilder()
                .setId(nullSafe(incoming.getId()))
                .addAllHouses(convertHouses(incoming.getHouses()))
                .addAllOrganisers((convertPeople(incoming.getOrganisers())))
                .build();
    }
}
