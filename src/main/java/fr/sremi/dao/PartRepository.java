package fr.sremi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import fr.sremi.model.Part;

/**
 * Created by fgallois on 9/25/15.
 */
public interface PartRepository extends PagingAndSortingRepository<Part, Long> {

    Page<Part> findByReferenceContaining(String reference, Pageable pageable);
}
