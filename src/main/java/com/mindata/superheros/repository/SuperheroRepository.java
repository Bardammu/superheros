package com.mindata.superheros.repository;

import com.mindata.superheros.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SuperheroRepository extends JpaRepository<Superhero, Integer>, JpaSpecificationExecutor<Superhero> {

}
