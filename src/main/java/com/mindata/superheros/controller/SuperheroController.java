package com.mindata.superheros.controller;

import com.mindata.superheros.model.Superhero;
import com.mindata.superheros.model.SuperheroRequest;
import com.mindata.superheros.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/superheros")
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(@Autowired SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @GetMapping
    public List<Superhero> getSuperheros() {
        return superheroService.getSuperheroes();
    }

    @PostMapping
    public void postSuperhero(@RequestBody SuperheroRequest request) {
        Superhero superhero = new Superhero();
        superhero.setName(request.getName());
        superhero.setGender(request.getGender());
        superhero.setOrigin(request.getOrigin());
        superhero.setBirthdate(request.getBirthdate());

        superheroService.addSuperhero(superhero);
    }
}
