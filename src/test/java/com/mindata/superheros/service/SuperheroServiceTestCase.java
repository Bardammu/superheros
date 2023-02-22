package com.mindata.superheros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SuperheroServiceTestCase {

    private final Superhero superman = new DefaultSuperhero("Superman");
    private final Superhero ironman = new DefaultSuperhero("Iron man");

    private SuperheroService superheroService;

    @BeforeEach
    public void setUp() {
        superheroService.addSuperhero(superman);
        superheroService.addSuperhero(ironman);
    }

    @Test
    public void getSuperheros() {
        List<Superhero> superheros = superheroService.getSuperheroes();

        assertThat(superheros.size(), is(2));
        assertThat(superheros, hasItem(superman));
        assertThat(superheros, hasItem(ironman));
    }

    @Test
    public void getSuperheroById() {
        Optional<Superhero> superman = superheroService.getSuperhero(0);

        assertThat(superman, equalTo(superman));
    }

    @Test
    public void getSuperheroByIdUnknown() {
        Optional<Superhero> superhero = superheroService.getSuperhero(100000);

        assertThat(superhero, equalTo(empty()));
    }

    @Test
    public void getSuperheroByIdInvalid() {
        IllegalArgumentException  exception = assertThrows(
                IllegalArgumentException .class,
                () -> superheroService.getSuperhero(-1),
                "Expected superheroService#getSuperhero(-1) to throw IllegalArgumentException , but it didn't"
        );

        assertThat(exception.getMessage(), equalTo("Invalid Superhero Id"));
    }

    @Test
    public void addSuperhero() {
        Superhero spiderman = new DefaultSuperhero("Spiderman");
        superheroService.addSuperhero(spiderman);
        List<Superhero> superheroes = superheroService.getSuperheroes();

        assertThat(superheroes.size(), is(3));
        assertThat(superheroes, hasItem(spiderman));
    }

    @Test
    public void removeSuperheroById() {
        boolean removedSuperhero = superheroService.removeSuperhero(superman.getId());
        Optional<Superhero> superhero = superheroService.getSuperhero(0);

        assertThat(superhero, equalTo(empty()));
        assertThat(removedSuperhero, is(true));
    }

    @Test
    public void removeSuperheroByIdUnknown() {
        Superhero spiderman = new DefaultSuperhero("Spiderman");
        boolean removedSuperhero = superheroService.removeSuperhero(spiderman.getId());

        assertThat(removedSuperhero, is(false));
    }
}
