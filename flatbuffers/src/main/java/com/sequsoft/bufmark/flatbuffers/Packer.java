package com.sequsoft.bufmark.flatbuffers;

import com.sequsoft.bufmark.model.Address;
import com.sequsoft.bufmark.model.Country;
import com.sequsoft.bufmark.model.House;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.model.Person;
import com.sequsoft.bufmark.model.Sex;

import com.google.flatbuffers.FlatBufferBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Packer {

    public static final Map<String, Short> SEX_ENUM_MAP = buildSexEnumMap();

    private static final Map<String, Short> COUNTRY_ENUM_MAP = buildCountryEnumMap();

    private static short convertSex(Sex sex) {
        return SEX_ENUM_MAP.getOrDefault(sex.toString(), F_Sex.UNSET);
    }

    private static short convertCountry(Country country) {
        return COUNTRY_ENUM_MAP.getOrDefault(country.toString(), F_Country.UNSET);
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private static int storePerson(FlatBufferBuilder fbb, Person incoming) {
        int id = fbb.createString(incoming.getId());
        int name = fbb.createString(incoming.getName());
        short sex = convertSex(incoming.getSex());
        long dateOfBirth = incoming.getDateOfBirth().toEpochSecond();
        int about = fbb.createString(nullSafe(incoming.getAbout()));

        return F_Person.createF_Person(fbb, id, name, sex, dateOfBirth, about);
    }

    private static int storeAddress(FlatBufferBuilder fbb, Address incoming) {
        int id = fbb.createString(incoming.getId());

        int addressLines = F_Address.createAddressLinesVector(fbb, storeStrings(fbb, incoming.getAddressLines()));

        int locality = fbb.createString(incoming.getLocality());
        int postcode = fbb.createString(incoming.getPostcode());
        short country = convertCountry(incoming.getCountry());

        return F_Address.createF_Address(fbb, id, addressLines, locality, postcode, country);
    }

    private static int[] storePeople(FlatBufferBuilder fbb, List<Person> people) {
        int[] offsets = new int[people.size()];

        for (int i = 0; i < people.size(); i++) {
            offsets[i] = storePerson(fbb, people.get(i));
        }

        return offsets;
    }

    private static int storeHouse(FlatBufferBuilder fbb, House incoming) {
        int id = fbb.createString(incoming.getId());
        int address = storeAddress(fbb, incoming.getAddress());
        int occupants = F_House.createOccupantsVector(fbb, storePeople(fbb, incoming.getOccupants()));

        return F_House.createF_House(fbb, id, address, occupants);
    }

    private static int[] storeHouses(FlatBufferBuilder fbb, List<House> incoming) {
        int[] offsets = new int[incoming.size()];

        for (int i = 0; i < incoming.size(); i++) {
            offsets[i] = storeHouse(fbb, incoming.get(i));
        }

        return offsets;
    }

    private static int storeHouseGroup(FlatBufferBuilder fbb, HouseGroup incoming) {
        int id = fbb.createString(incoming.getId());
        int houses = F_HouseGroup.createHousesVector(fbb, storeHouses(fbb, incoming.getHouses()));
        int organisers = F_HouseGroup.createOrganisersVector(fbb, storePeople(fbb, incoming.getOrganisers()));

        return F_HouseGroup.createF_HouseGroup(fbb, id, houses, organisers);
    }

    public static byte[] createHouseGroupBuffer(HouseGroup houseGroup) {
        FlatBufferBuilder fbb = new FlatBufferBuilder();
        int offset = storeHouseGroup(fbb, houseGroup);
        fbb.finish(offset);

        return fbb.sizedByteArray();
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
        for (short i = 0; i < F_Sex.names.length; i++) {
            if (F_Sex.names[i] != null && !F_Sex.names[i].isEmpty()) {
                m.put(F_Sex.names[i], i);
            }
        }
        return m;
    }

    private static Map<String, Short> buildCountryEnumMap() {
        Map<String, Short> m = new HashMap<>();
        for (short i = 0; i < F_Country.names.length; i++) {
            if (F_Country.names[i] != null && !F_Country.names[i].isEmpty()) {
                m.put(F_Country.names[i], i);
            }
        }
        return m;
    }
}
