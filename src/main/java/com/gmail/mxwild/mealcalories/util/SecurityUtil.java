package com.gmail.mxwild.mealcalories.util;

import static com.gmail.mxwild.mealcalories.common.Constants.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private SecurityUtil() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static int authUserId() {
        return 1;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
