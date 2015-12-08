package fr.sremi.dao;

import org.springframework.data.repository.CrudRepository;

import fr.sremi.model.LineItem;

/**
 * Created by fgallois on 11/4/15.
 */
public interface OrderLineItemRepository extends CrudRepository<LineItem, Long> {

}
