package com.mindata.superheros.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.mindata.superheros.aop.LogRequestExecutionTime;
import com.mindata.superheros.model.ModelUtils;
import com.mindata.superheros.model.Superhero;
import com.mindata.superheros.model.request.SuperheroRequest;
import com.mindata.superheros.model.response.SuperheroResponse;
import com.mindata.superheros.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.mindata.superheros.model.ModelUtils.getSuperheroFromRequest;
import static com.mindata.superheros.model.ModelUtils.getSuperheroResponseFromSuperhero;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/superheros")
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(@Autowired SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @LogRequestExecutionTime
    @GetMapping
    public ResponseEntity<List<SuperheroResponse>> getSuperheros(@RequestParam Map<String, String> filteringParams) {
        List<Superhero> superheroes = superheroService.getSuperheroFilterBy(filteringParams);
        List<SuperheroResponse> superheroResponses = superheroes.stream()
                .map(ModelUtils::getSuperheroResponseFromSuperhero).toList();

        return ok(superheroResponses);
    }

    @LogRequestExecutionTime
    @GetMapping("/{superheroId}")
    public ResponseEntity<SuperheroResponse> getSuperhero(@PathVariable Integer superheroId) {
        Optional<Superhero> superhero = superheroService.getSuperhero(superheroId);

        return superhero.map(s -> ok(getSuperheroResponseFromSuperhero(superhero.get()))).orElse(status(NOT_FOUND).build());
    }

    @LogRequestExecutionTime
    @PostMapping
    public ResponseEntity<SuperheroResponse> postSuperhero(@RequestBody SuperheroRequest request) {
        Superhero superhero = getSuperheroFromRequest(request);
        superhero = superheroService.addSuperhero(superhero);

        return status(CREATED).body(getSuperheroResponseFromSuperhero(superhero));
    }

    @LogRequestExecutionTime
    @PatchMapping(path = "/{superheroId}", consumes = "application/json")
    public ResponseEntity<SuperheroResponse> updateSuperhero(@PathVariable Integer superheroId, @RequestBody SuperheroRequest request) {
        if (!Objects.equals(superheroId, request.id())) {
            return badRequest().build();
        }

        Superhero superhero = getSuperheroFromRequest(request);
        superhero = superheroService.updateSuperhero(superhero);

        return accepted().body(getSuperheroResponseFromSuperhero(superhero));
    }

    @LogRequestExecutionTime
    @PatchMapping(path = "/{superheroId}", consumes = "application/json-patch+json")
    public ResponseEntity<SuperheroResponse> updateSuperhero(@PathVariable Integer superheroId, @RequestBody JsonPatch jsonPatchRequest) {
        Optional<Superhero> superhero = superheroService.getSuperhero(superheroId);
        if (superhero.isPresent()) {
            Superhero updatedSuperhero = superheroService.updateSuperhero(superhero.get(), jsonPatchRequest);
            return accepted().body(getSuperheroResponseFromSuperhero(updatedSuperhero));
        } else {
            return status(NOT_FOUND).contentType(APPLICATION_JSON).build();
        }
    }

    @LogRequestExecutionTime
    @DeleteMapping("/{superheroId}")
    public ResponseEntity<String> removeSuperhero(@PathVariable Integer superheroId) {
        boolean deleted = superheroService.removeSuperhero(superheroId);

        return deleted ? status(NO_CONTENT).contentType(APPLICATION_JSON).build()
                : status(NOT_FOUND).contentType(APPLICATION_JSON).build();
    }

}
