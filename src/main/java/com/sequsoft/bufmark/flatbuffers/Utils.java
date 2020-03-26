package com.sequsoft.bufmark.flatbuffers;

import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static final Map<String, Short> SEX_ENUM_MAP = buildSexEnumMap();

    private static final Map<String, Short> COUNTRY_ENUM_MAP = buildCountryEnumMap();

    private static short convertSex(com.sequsoft.bufmark.common.Sex sex) {
        return SEX_ENUM_MAP.getOrDefault(sex.toString(), com.sequsoft.bufmark.flatbuffers.Sex.UNSET);
    }

    private static short convertCountry(com.sequsoft.bufmark.common.Country country) {
        return COUNTRY_ENUM_MAP.getOrDefault(country.toString(), com.sequsoft.bufmark.flatbuffers.Country.UNSET);
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private static int storePerson(FlatBufferBuilder fbb, com.sequsoft.bufmark.common.Person incoming) {
        int id = fbb.createString(incoming.getId());
        int name = fbb.createString(incoming.getName());
        short sex = convertSex(incoming.getSex());
        long dateOfBirth = incoming.getDateOfBirth().toEpochSecond();
        int about = fbb.createString(nullSafe(incoming.getAbout()));

        return Person.createPerson(fbb, id, name, sex, dateOfBirth, about);
    }

    private static int storeAddress(FlatBufferBuilder fbb, com.sequsoft.bufmark.common.Address incoming) {
        int id = fbb.createString(incoming.getId());

        int addressLines = Address.createAddressLinesVector(fbb, storeStrings(fbb, incoming.getAddressLines()));

        int locality = fbb.createString(incoming.getLocality());
        int postcode = fbb.createString(incoming.getPostcode());
        short country = convertCountry(incoming.getCountry());

        return Address.createAddress(fbb, id, addressLines, locality, postcode, country);
    }

    private static int[] storePeople(FlatBufferBuilder fbb, List<com.sequsoft.bufmark.common.Person> people) {
        int[] offsets = new int[people.size()];

        for (int i = 0; i < people.size(); i++) {
            offsets[i] = storePerson(fbb, people.get(i));
        }

        return offsets;
    }

    private static int storeHouse(FlatBufferBuilder fbb, com.sequsoft.bufmark.common.House incoming) {
        int id = fbb.createString(incoming.getId());
        int address = storeAddress(fbb, incoming.getAddress());
        int occupants = House.createOccupantsVector(fbb, storePeople(fbb, incoming.getOccupants()));

        return House.createHouse(fbb, id, address, occupants);
    }

    private static int[] storeHouses(FlatBufferBuilder fbb, List<com.sequsoft.bufmark.common.House> incoming) {
        int[] offsets = new int[incoming.size()];

        for (int i = 0; i < incoming.size(); i++) {
            offsets[i] = storeHouse(fbb, incoming.get(i));
        }

        return offsets;
    }

    private static int storeHouseGroup(FlatBufferBuilder fbb, com.sequsoft.bufmark.common.HouseGroup incoming) {
        int id = fbb.createString(incoming.getId());
        int houses = HouseGroup.createHousesVector(fbb, storeHouses(fbb, incoming.getHouses()));
        int organisers = HouseGroup.createOrganisersVector(fbb, storePeople(fbb, incoming.getOrganisers()));

        return HouseGroup.createHouseGroup(fbb, id, houses, organisers);
    }

    public static ByteBuffer createHouseGroupBuffer(com.sequsoft.bufmark.common.HouseGroup houseGroup) {
        FlatBufferBuilder fbb = new FlatBufferBuilder();
        int offset = storeHouseGroup(fbb, houseGroup);
        fbb.finish(offset);

        return fbb.dataBuffer();
    }

    private static int[] storeStrings(FlatBufferBuilder fbb, List<String> strs) {
        int[] addresses = new int[strs.size()];

        for (int i = 0; i < strs.size(); i++) {
            addresses[i] = fbb.createString(strs.get(i));
        }
        return addresses;
    }

    private static Map<String, Short> buildSexEnumMap() {
        Map<String, Short> m = new HashMap<>();
        for (short i = 0; i < Sex.names.length; i++) {
            if (Sex.names[i] != null && !Sex.names[i].isEmpty()) {
                m.put(Sex.names[i], i);
            }
        }
        return m;
    }

    private static Map<String, Short> buildCountryEnumMap() {
        Map<String, Short> m = new HashMap<>();
        for (short i = 0; i < Sex.names.length; i++) {
            if (Country.names[i] != null && !Sex.names[i].isEmpty()) {
                m.put(Sex.names[i], i);
            }
        }
        return m;
    }
}
