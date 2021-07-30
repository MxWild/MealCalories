package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.model.BaseEntity;
import com.gmail.mxwild.mealcalories.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id = " + id);
    }

    public static void checkNew(BaseEntity entity) {
        if(!entity.isNew()) {
            throw new IllegalArgumentException(entity + "must ne new (id = null)");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id = " + id);
        }
    }

}
