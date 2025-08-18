package com.travel.blog.entity.enums;

public enum TRIP_STATUS {
    DRAFT("DRAFT", 0),
    PLANNING("PLANNING", 1),
    IN_PROGRESS("IN PROGRESS", 2),
    COMPLETED("COMPLETED", 3),
    CANCELED("CANCELED", 4);

    private final String name;
    private final Integer value;

    TRIP_STATUS(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    // Optional: tìm enum theo value
    public static TRIP_STATUS fromValue(Integer value) {
        for (TRIP_STATUS tripStatus : TRIP_STATUS.values()) {
            if (tripStatus.value.equals(value)) {
                return tripStatus;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}