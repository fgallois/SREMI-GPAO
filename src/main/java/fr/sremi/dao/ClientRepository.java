package fr.sremi.dao;

import fr.sremi.model.Buyer;
import fr.sremi.model.Client;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by fgallois on 12/27/15.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

}
