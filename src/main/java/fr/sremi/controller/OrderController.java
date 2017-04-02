package fr.sremi.controller;

import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.exception.ExcelException;
import fr.sremi.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fgallois on 9/4/15.
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping(value = "/orders.json")
    public ResponseEntity<List<OrderData>> getAvailableOrders() {
        try {
            return ResponseEntity.ok().body(orderService.importOrders());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/order.json/{commandeRef}")
    public List<OrderDetailData> getOrderDetails(@PathVariable String commandeRef) {
        return orderService.getOrderDetails(commandeRef);
    }

    @GetMapping(value = "/openOrders.json")
    public List<OrderData> getOpenOrders() {
        return orderService.getOpenOrders();
    }

    @GetMapping(value = "/openOrder.json/{commandeRef}")
    public InvoiceData getOpenOrderDetails(@PathVariable String commandeRef) {
        return orderService.getInvoiceData(commandeRef);
    }

    @PutMapping(value = "/updateOrderLineItem.json")
    public void updateLineItem(@RequestBody OrderDetailData orderDetailData) {
        orderService.updateLineItemPrice(orderDetailData);
    }
}
