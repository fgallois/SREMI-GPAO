package fr.sremi.dao;

import fr.sremi.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by fgallois on 11/4/15.
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

    Order findByReference(String reference);

    List<Order> findByOpenTrueOrderByReferenceAsc();
}
