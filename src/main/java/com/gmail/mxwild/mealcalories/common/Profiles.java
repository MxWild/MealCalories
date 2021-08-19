package com.gmail.mxwild.mealcalories.common;

import org.springframework.util.ClassUtils;

import static com.gmail.mxwild.mealcalories.common.Constants.UNSUPPORTED_CLASS;

public class Profiles {

    public static final String JDBC = "jdbc";
    public static final String JPA = "jpa";
    public static final String DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String POSTGRES_DB = "postgres";
    public static final String HSQL_DB = "hsqldb";

    // Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }

    private Profiles() {
        throw new UnsupportedOperationException(UNSUPPORTED_CLASS);
    }

}
