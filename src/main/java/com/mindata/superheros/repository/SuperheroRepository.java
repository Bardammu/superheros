package com.mindata.superheros.repository;

import com.mindata.superheros.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperheroRepository extends JpaRepository<Superhero, Integer> {

}
