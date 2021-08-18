package com.gmail.mxwild.mealcalories;

import com.gmail.mxwild.mealcalories.common.Profiles;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfilesResolver;

public class ActiveDbProfileResolver implements ActiveProfilesResolver {
    @Override
    public @NonNull String[] resolve(Class<?> testClass) {
        return new String[] {
                Profiles.getActiveDbProfile()
        };
    }
}
