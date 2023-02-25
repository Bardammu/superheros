package com.mindata.superheros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mindata.superheros.model.Superhero;
import com.mindata.superheros.repository.SuperheroRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Default implementation of {@link SuperheroService} service
 *
 * @since 1.0.0
 */
@Service
public class DefaultSuperheroService implements SuperheroService {

    private final SuperheroRepository superheroRepository;

    public DefaultSuperheroService(@Autowired SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @Override
    public List<Superhero> getSuperheroes() {
        return superheroRepository.findAll();
    }

    @Override
    public Optional<Superhero> getSuperhero(Integer superheroId) throws IllegalArgumentException {
        if (superheroId <= 0) {
            throw new IllegalArgumentException(format("Invalid Superhero Id %s, must be greater than 0", superheroId));
        }

        return superheroRepository.findById(superheroId);
    }

    @Override
    public Superhero addSuperhero(Superhero superhero) {
        return superheroRepository.saveAndFlush(superhero);
    }

    @Override
    public boolean removeSuperhero(Integer superheroId) throws IllegalArgumentException {
        if (superheroId <= 0) {
            throw new IllegalArgumentException(format("Invalid Superhero Id %s, must be greater than 0", superheroId));
        }
        if (!superheroRepository.existsById(superheroId)) {
            return false;
        }
        superheroRepository.deleteById(superheroId);
        return true;
    }

    public List<Superhero> getSuperheroFilterBy(Map<String, String> filteringParameters) {
        Specification<Superhero> specification = (root, query, criteriaBuilder) -> {
            String sqlFormat = "%%%s%%";
            List<Predicate> predicates = new ArrayList<>();
            filteringParameters.forEach((param, value) ->
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(param)), format(sqlFormat, value.toLowerCase()))) );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return superheroRepository.findAll(specification);
    }

    @Override
    public Superhero updateSuperhero(Superhero superhero) {
        return superheroRepository.saveAndFlush(superhero);
    }

    @Override
    public Superhero updateSuperhero(Superhero superhero, JsonPatch jsonPatch) throws IllegalArgumentException {
        Superhero patchedSuperhero = applyPatchToSuperhero(jsonPatch, superhero);
        return superheroRepository.saveAndFlush(patchedSuperhero);
    }

    private Superhero applyPatchToSuperhero(JsonPatch patch, Superhero superhero) throws IllegalArgumentException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(superhero, JsonNode.class));
            return objectMapper.treeToValue(patched, Superhero.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new IllegalArgumentException(format("Can't perform update operation on superhero %s", superhero.getId()));
        }
    }
}
