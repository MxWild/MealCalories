package com.gmail.mxwild.mealcalories.common;

import static com.gmail.mxwild.mealcalories.common.Constants.UNSUPPORTED_CLASS;

public class Profiles {

    public static final String JDBC = "jdbc";
    public static final String JPA = "jpa";

    public static final String REPOSITORY_IMPLEMENTATION = JPA;

    public static final String POSTGRES_DB = "postgres";
    public static final String HSQL_DB = "hsqldb";

    public static final String ACTIVE_DB = POSTGRES_DB;

    private Profiles() {
        throw new UnsupportedOperationException(UNSUPPORTED_CLASS);
    }

}
