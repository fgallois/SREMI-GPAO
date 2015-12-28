package fr.sremi.dao;

import fr.sremi.model.Buyer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by fgallois on 12/27/15.
 */
public interface BuyerRepository extends CrudRepository<Buyer, Long> {

    @Cacheable("buyers")
    Buyer findByCode(String code);
}
