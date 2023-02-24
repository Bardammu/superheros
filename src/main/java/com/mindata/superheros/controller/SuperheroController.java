package com.mindata.superheros.controller;

import com.mindata.superheros.model.Superhero;
import com.mindata.superheros.model.SuperheroRequest;
import com.mindata.superheros.model.SuperheroResponse;
import com.mindata.superheros.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/superheros")
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(@Autowired SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @GetMapping
    public List<Superhero> getSuperheros(@RequestParam Map<String, String> filteringParams) {
        return superheroService.getSuperheroFilterBy(filteringParams);
    }

    @GetMapping("/{superheroId}")
    public SuperheroResponse getSuperhero(@PathVariable Integer superheroId) {
        Optional<Superhero> superhero = superheroService.getSuperhero(superheroId);
        if (superhero.isPresent()) {
            Superhero s = superhero.get();
            return new SuperheroResponse(s.getId(), s.getName(), s.getGender(), s.getOrigin(), s.getBirthdate());
        } else {
            return new SuperheroResponse(null, null, null, null, null);
        }
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public SuperheroResponse postSuperhero(@RequestBody SuperheroRequest request) {
        Superhero superhero = new Superhero();

        superhero.setName(request.getName());
        superhero.setGender(request.getGender());
        superhero.setOrigin(request.getOrigin());
        superhero.setBirthdate(request.getBirthdate());

        superhero = superheroService.addSuperhero(superhero);

        return new SuperheroResponse(superhero.getId(), superhero.getName(), superhero.getGender(),
                superhero.getOrigin(), superhero.getBirthdate());
        }

        @PatchMapping
        @ResponseStatus(ACCEPTED)
        public SuperheroResponse updateSuperhero(@RequestBody SuperheroRequest request) {
            Superhero superhero = new Superhero();

            superhero.setId(request.getId());
            superhero.setName(request.getName());
            superhero.setGender(request.getGender());
            superhero.setOrigin(request.getOrigin());
            superhero.setBirthdate(request.getBirthdate());

            superhero = superheroService.updateSuperhero(superhero);

            return new SuperheroResponse(superhero.getId(), superhero.getName(), superhero.getGender(),
                    superhero.getOrigin(), superhero.getBirthdate());
        }
}
