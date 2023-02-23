package com.mindata.superheros.model;

import java.sql.Date;

public class SuperheroRequest {

    private final String name;

    private final String gender;

    private final String origin;

    private final Date birthdate;

    public SuperheroRequest(String name, String gender, String origin, Date birthdate) {
        this.name = name;
        this.gender = gender;
        this.origin = origin;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getOrigin() {
        return origin;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
