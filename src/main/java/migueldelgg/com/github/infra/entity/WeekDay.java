package migueldelgg.com.github.infra.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    @JsonCreator
    public static WeekDay fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return WeekDay.valueOf(value.toUpperCase());
    }
}

