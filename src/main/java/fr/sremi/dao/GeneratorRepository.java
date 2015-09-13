package fr.sremi.dao;

import org.springframework.data.repository.CrudRepository;

import fr.sremi.model.Generator;

/**
 * Created by fgallois on 9/12/15.
 */
public interface GeneratorRepository extends CrudRepository<Generator, Long> {

    Generator findByType(String type);

}
