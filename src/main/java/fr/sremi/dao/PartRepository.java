package fr.sremi.dao;

import org.springframework.data.repository.CrudRepository;

import fr.sremi.model.Part;

/**
 * Created by fgallois on 9/25/15.
 */
public interface PartRepository extends CrudRepository<Part, Long> {
}
