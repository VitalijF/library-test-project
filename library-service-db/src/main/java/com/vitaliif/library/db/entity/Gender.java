package com.vitaliif.library.db.entity;

import java.util.Arrays;

public enum Gender {

    MALE("m"),
    FEMALE("f"),
    OTHERS("o");

    private final String genderSymbol;

    Gender(String genderSymbol) {
        this.genderSymbol = genderSymbol;
    }

    public String getGenderSymbol() {
        return genderSymbol;
    }

    public static Gender getFromSymbol(String symbol) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getGenderSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find gender by symbol = " + symbol));
    }
}
