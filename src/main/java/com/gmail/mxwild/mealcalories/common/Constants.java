package com.gmail.mxwild.mealcalories.common;

import java.time.LocalDateTime;

public class Constants {

    public static final int START_SEQ = 100000;

    public static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    public static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    private Constants() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

}
