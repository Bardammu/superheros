package com.mindata.superheros.model;

import java.sql.Date;

public class SuperheroRequest {

    private final Integer id;

    private final String name;

    private final String gender;

    private final String origin;

    private final Date birthdate;

    public SuperheroRequest(Integer id, String name, String gender, String origin, Date birthdate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.origin = origin;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
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
