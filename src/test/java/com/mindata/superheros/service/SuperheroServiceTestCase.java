package com.mindata.superheros.service;

import com.mindata.superheros.model.Superhero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(scripts = "/sql/testData.sql", executionPhase = BEFORE_TEST_METHOD)
public class SuperheroServiceTestCase {

    @Autowired
    private SuperheroService superheroService;

    @Test
    public void getSuperheros() {
        List<Superhero> superheros = superheroService.getSuperheroes();

        assertThat(superheros.size(), is(2));
        assertThat(superheros.get(0).getName(), equalTo("Superman"));
        assertThat(superheros.get(1).getName(), equalTo("Iron Man"));
    }

    @Test
    public void getSuperheroById() {
        Optional<Superhero> superman = superheroService.getSuperhero(1);

        assertThat(superman.isEmpty(), is(false));
        assertThat(superman.get().getName(), equalTo("Superman"));
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

        assertThat(exception.getMessage(), equalTo("Invalid Superhero Id -1, must be greater than 0"));
    }

    @Test
    public void addSuperhero() {
        Superhero spiderman = new Superhero();
        spiderman.setName("Spiderman");
        spiderman.setGender("Male");
        spiderman.setOrigin("New York, US, Earth");
        spiderman.setBirthdate(Date.valueOf("1962-08-10"));

        Superhero newSuperhero = superheroService.addSuperhero(spiderman);
        List<Superhero> superheroes = superheroService.getSuperheroes();


        assertThat(newSuperhero.getName(), equalTo("Spiderman"));
        assertThat(newSuperhero.getId(), is(3));
        assertThat(superheroes.size(), is(3));
        assertThat(superheroes, hasItem(hasProperty("name", equalTo("Spiderman"))));
    }

    @Test
    public void addSuperheroWithRepeatedName() {
        Superhero secondSuperman = new Superhero();
        secondSuperman.setName("Superman");
        secondSuperman.setGender("Male");
        secondSuperman.setOrigin("Buenos Aires, Argentina, Earth");
        secondSuperman.setBirthdate(Date.valueOf("1967-08-10"));

        assertThrows(Exception .class,
                () -> superheroService.addSuperhero(secondSuperman),
                "Expected superheroService#addSuperhero(secondSuperman) to throw IllegalArgumentException , but it didn't"
        );

        List<Superhero> superheroes = superheroService.getSuperheroes();

        assertThat(superheroes.size(), is(2));
    }

    @Test
    public void addSuperheroWithoutName() {
        Superhero noNameSuperhero = new Superhero();
        noNameSuperhero.setGender("Male");
        noNameSuperhero.setOrigin("New York, US, Earth");
        noNameSuperhero.setBirthdate(Date.valueOf("1962-08-10"));

        assertThrows(Exception.class,
                () -> superheroService.addSuperhero(noNameSuperhero),
                "Expected superheroService#addSuperhero(noNameSuperhero) to throw ConstraintViolationException , but it didn't"
        );

        List<Superhero> superheroes = superheroService.getSuperheroes();

        assertThat(superheroes.size(), is(2));
        assertThat(superheroes, not(hasItem(hasProperty("name", is("Spiderman")))));
    }

    @Test
    public void removeSuperheroById() {
        boolean removedSuperhero = superheroService.removeSuperhero(1);
        Optional<Superhero> superhero = superheroService.getSuperhero(1);

        assertThat(removedSuperhero, is(true));
        assertThat(superhero, equalTo(empty()));
    }

    @Test
    public void removeSuperheroByIdUnknown() {
        boolean removedSuperhero = superheroService.removeSuperhero(10000);

        assertThat(removedSuperhero, is(false));
    }

    @Test
    public void removeSuperheroByIdInvalid() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> superheroService.removeSuperhero(-1),
                "Expected superheroService#removeSuperhero(-1) to throw IllegalArgumentException , but it didn't"
        );

        assertThat(exception.getMessage(), equalTo("Invalid Superhero Id -1, must be greater than 0"));
    }

    @Test
    public void getSuperheroFilterByNameSubstring() {
        List<Superhero> superheroes = superheroService.getSuperheroFilterBy(singletonMap("name", "man"));

        assertThat(superheroes.size(), is(2));
        assertThat(superheroes, hasItem(hasProperty("name", is("Superman"))));
        assertThat(superheroes, hasItem(hasProperty("name", is("Iron Man"))));
    }

    @Test
    public void getSuperheroFilterByOriginSubstring() {
        List<Superhero> superheroes = superheroService.getSuperheroFilterBy(singletonMap("origin", "New York"));

        assertThat(superheroes.size(), is(1));
        assertThat(superheroes, hasItem(hasProperty("name", is("Iron Man"))));
    }

    @Test
    public void getSuperheroFilterByNameSubstringNotExisting() {
        List<Superhero> superheroes = superheroService.getSuperheroFilterBy(singletonMap("name", "what?"));

        assertThat(superheroes.size(), is(0));
    }

    @Test
    public void updateSuperhero() throws Exception {
        Optional<Superhero> superman = superheroService.getSuperhero(1);
        Superhero updatedSuperman = superman.orElseThrow(() -> new Exception("Couldn't get old Superman to update"));
        updatedSuperman.setOrigin("Buenos Aires, Argentina, Earth");

        Superhero superhero = superheroService.updateSuperhero(updatedSuperman);
        List<Superhero> superheros = superheroService.getSuperheroes();

        assertThat(superheros.size(), is(2));
        assertThat(superhero.getName(), equalTo("Superman"));
        assertThat(superhero.getOrigin(), equalTo("Buenos Aires, Argentina, Earth"));
    }

}
