package com.mindata.superheros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Entity representing a Superhero
 *
 * @since 1.0.0
 */
@Entity(name = "superhero")
public class Superhero {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotBlank(message = "Name should not be null or blank")
    @Column(unique = true)
    private String name;

    private String gender;

    private String origin;

    private Date birthdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

}
