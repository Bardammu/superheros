package com.mindata.superheros.service;

import com.mindata.superheros.model.Superhero;
import com.mindata.superheros.repository.SuperheroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.sql.Date.valueOf;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SuperheroServiceTestCase {

    @Mock
    private SuperheroRepository superheroRepository;

    private SuperheroService superheroService;

    private Superhero superman;
    private Superhero ironMan;
    private List<Superhero> superheroes;

    @BeforeEach
    public void setUp() {
        superman = new Superhero();
        superman.setId(1);
        superman.setName("Superman");
        superman.setGender("Male");
        superman.setOrigin("Krypton");
        superman.setBirthdate(valueOf("1962-08-10"));

        ironMan = new Superhero();
        ironMan.setId(2);
        ironMan.setName("Iron Man");
        ironMan.setGender("Male");
        ironMan.setOrigin("New York, US, Earth");
        ironMan.setBirthdate(valueOf("1970-05-29"));

        superheroes = List.of(superman, ironMan);

        when(superheroRepository.findAll()).thenReturn(superheroes);
        when(superheroRepository.findById(1)).thenReturn(of(superman));
        when(superheroRepository.findById(2)).thenReturn(of(ironMan));
        when(superheroRepository.findByName("Superman")).thenReturn(singletonList(superman));
        when(superheroRepository.saveAndFlush(any(Superhero.class))).thenAnswer(returnsFirstArg());

        superheroService = new DefaultSuperheroService(superheroRepository);
    }

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
        spiderman.setBirthdate(valueOf("1962-08-10"));

        Superhero newSuperhero = superheroService.addSuperhero(spiderman);

        assertThat(newSuperhero, is(spiderman));
    }

    @Test
    public void addSuperheroWithRepeatedName() {
        Superhero secondSuperman = new Superhero();
        secondSuperman.setName("Superman");
        secondSuperman.setGender("Male");
        secondSuperman.setOrigin("Buenos Aires, Argentina, Earth");
        secondSuperman.setBirthdate(valueOf("1967-08-10"));

        IllegalArgumentException  exception = assertThrows(IllegalArgumentException .class,
                () -> superheroService.addSuperhero(secondSuperman),
                "Expected superheroService#addSuperhero(secondSuperman) to throw IllegalArgumentException , but it didn't"
        );

        assertThat(exception.getMessage(), equalTo("Superhero with name Superman already exists"));
    }

    @Test
    public void addSuperheroWithoutName() {
        Superhero noNameSuperhero = new Superhero();
        noNameSuperhero.setGender("Male");
        noNameSuperhero.setOrigin("New York, US, Earth");
        noNameSuperhero.setBirthdate(valueOf("1962-08-10"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> superheroService.addSuperhero(noNameSuperhero),
                "Expected superheroService#addSuperhero(noNameSuperhero) to throw IllegalArgumentException , but it didn't"
        );

        assertThat(exception.getMessage(), equalTo("Superhero name is mandatory"));
    }

    @Test
    public void removeSuperheroById() {
        when(superheroRepository.existsById(anyInt())).thenReturn(true);

        boolean removedSuperhero = superheroService.removeSuperhero(1);

        assertThat(removedSuperhero, is(true));
    }

    @Test
    public void removeSuperheroByIdUnknown() {
        when(superheroRepository.existsById(anyInt())).thenReturn(false);

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
        when(superheroRepository.findAll(any(Specification.class))).thenReturn(superheroes);

        List<Superhero> superheroes = superheroService.getSuperheroFilterBy(singletonMap("name", "man"));

        assertThat(superheroes.size(), is(2));
        assertThat(superheroes, hasItem(hasProperty("name", is("Superman"))));
        assertThat(superheroes, hasItem(hasProperty("name", is("Iron Man"))));
    }

    @Test
    public void getSuperheroFilterByNameSubstringAndUnknownAttribute() {
        when(superheroRepository.findAll(any(Specification.class))).thenReturn(superheroes);

        List<Superhero> superheroes = superheroService.getSuperheroFilterBy(Map.of("name","man","what","no"));

        assertThat(superheroes.size(), is(0));
    }

    @Test
    public void getSuperheroFilterByOriginSubstring() {
        when(superheroRepository.findAll(any(Specification.class))).thenReturn(singletonList(ironMan));

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
        superman.setOrigin("Buenos Aires, Argentina, Earth");
        Superhero superhero = superheroService.updateSuperhero(superman);

        assertThat(superhero.getName(), is("Superman"));
        assertThat(superhero.getOrigin(), is("Buenos Aires, Argentina, Earth"));
    }

}
