package com.mindata.superheros.repository;

import com.mindata.superheros.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SuperheroRepository extends JpaRepository<Superhero, Integer>, JpaSpecificationExecutor<Superhero> {

    List<Superhero> findByName(String name);

}
