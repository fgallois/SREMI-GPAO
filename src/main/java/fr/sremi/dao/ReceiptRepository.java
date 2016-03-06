package fr.sremi.dao;

import org.springframework.data.repository.CrudRepository;

import fr.sremi.model.Receipt;

/**
 * Created by fgallois on 11/4/15.
 */
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {

}
