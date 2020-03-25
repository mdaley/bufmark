package com.sequsoft.bufmark.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class CommonUtils {
    private static final Random RANDOM = new Random();

    private static final List<String> FIRST_NAMES = List.of("Matt", "Mark", "Lyra", "Ranesh", "Mary", "Marmaduke", "Theresa",
            "Samuel", "Georgina", "Archibald");

    private static final List<String> LAST_NAMES = List.of("Smith", "Jones", "de Tourville", "Ranathamagathurgam", "Oh",
            "la Blanche", "Silver Tongue", "Metheny", "Dhugashvili", "Parker");

    private static final List<String> LOCALITIES = List.of("Bristol", "Chelsea", "Bahrain", "Acapulco", "New York",
            "Chatham", "Livepool", "Tangiers", "Timbuktu", "Dawlish");

    private static final String POSTCODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final List<String> STREET_TYPE = List.of("Lane", "Road", "Avenue", "Corner", "Street");

    private static final List<String> STREET_NAME = List.of("Lancashire", "Yorkshire", "Mary Bush", "Wollabonga", "Nash",
            "Devenish", "Sanderson", "Droitwich", "Kamchatka", "Billabong");

    private static final List<String> SUB_LOCALITY_NAME = List.of("Bishopston", "Seaport", "7th Arrondisment", "The Old Town",
            "Speaker's Corner", "Xanadu", "Crinkly Bottom", "Paramente", "Ninth Sector", "Palomino");

    public static String randomGUID() {
        return UUID.randomUUID().toString();
    }

    public static Sex randomSex() {
        return Sex.values()[RANDOM.nextInt(Sex.values().length)];
    }

    public static Country randomCountry() {
        return Country.values()[RANDOM.nextInt(Country.values().length)];
    }

    public static String randomName() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size())) + " "
                + LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()));
    }

    public static Person randomPerson() {
        return Person.newPerson()
                .withId(randomGUID())
                .withName(randomName())
                .withSex(randomSex())
                .withDateOfBirth(randomUtcDateTime());
    }

    public static List<Person> randomPeople() {
        List<Person> people = new ArrayList<>();
        int count = RANDOM.nextInt(6) + 1;
        for (int i = 0; i < count; i++) {
            people.add(randomPerson());
        }

        return people;
    }

    public static Address randomAddress() {
        return Address.newAddress()
                .withId(randomGUID())
                .withAddressLines(randomAddressLines())
                .withLocality(randomLocality())
                .withPostcode(randomPostCode())
                .withCountry(randomCountry());
    }

    public static House randomHouse() {
        return House.newHouse()
                .withId(randomGUID())
                .withAddress(randomAddress())
                .withOccupants(randomPeople());
    }

    public static List<House> randomHouses() {
        List<House> houses = new ArrayList<>();
        int count = RANDOM.nextInt(100) + 1;
        for (int i = 0; i < count; i++) {
            houses.add(randomHouse());
        }

        return houses;
    }

    public static HouseGroup randomHouseGroup() {
        return HouseGroup.newHouseGroup()
                .withId(randomGUID())
                .withHouses(randomHouses())
                .withOrganisers(randomPeople());
    }

    public static String randomLocality() {
        return LOCALITIES.get(RANDOM.nextInt(LOCALITIES.size()));
    }

    public static String randomPostCode() {
        int length = RANDOM.nextInt(3) + 6;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(POSTCODE_CHARS.charAt(RANDOM.nextInt(POSTCODE_CHARS.length())));
        }

        return builder.toString();
    }

    public static List<String> randomAddressLines() {
        List<String> addressLines = new ArrayList<>();
        addressLines.add(RANDOM.nextInt(5000) + " " + STREET_NAME.get(RANDOM.nextInt(STREET_NAME.size())) + " "
                + STREET_TYPE.get(RANDOM.nextInt(STREET_TYPE.size())));
        addressLines.add(SUB_LOCALITY_NAME.get(RANDOM.nextInt(SUB_LOCALITY_NAME.size())));
        for (int i = 0; i < 2; i++) {
            if (RANDOM.nextInt(4) == 0) {
                addressLines.add(SUB_LOCALITY_NAME.get(RANDOM.nextInt(SUB_LOCALITY_NAME.size())));
            }
        }

        return addressLines;
    }

    private static int daysInMonth(int month) {
        if (month == 2) {
            return 28;
        } else if (Set.of(4, 6, 9, 11).contains(month)) {
            return 30;
        } else {
            return 31;
        }
    }
    public static ZonedDateTime randomUtcDateTime() {
        int y = RANDOM.nextInt(100) + 1920;
        int m = RANDOM.nextInt(12) + 1;
        int d = RANDOM.nextInt(daysInMonth(m)) + 1;;
        ZoneId z = ZoneId.of("UTC");
        return ZonedDateTime.of(LocalDateTime.of(LocalDate.of(y, m, d), LocalTime.of(0, 0)), z);
    }
}
