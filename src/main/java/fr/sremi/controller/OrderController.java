package fr.sremi.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.services.OrderService;

/**
 * Created by fgallois on 9/4/15.
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/orders.json", method = RequestMethod.GET)
    public List<OrderData> getAvailableOrders() {
        orderService.importOrders();
        return orderService.getAvailableOrders();
    }

    @RequestMapping(value = "/order.json/{commandeRef}", method = RequestMethod.GET)
    public List<OrderDetailData> getOrderDetails(@PathVariable String commandeRef) {
        return orderService.getOrderDetails(commandeRef);
    }

    @RequestMapping(value = "/openOrders.json", method = RequestMethod.GET)
    public List<OrderData> getOpenOrders() {
        return orderService.getOpenOrders();
    }

    @RequestMapping(value = "/openOrder.json/{commandeRef}", method = RequestMethod.GET)
    public List<OrderDetailData> getOpenOrderDetails(@PathVariable String commandeRef) {
        return orderService.getOpenOrderDetails(commandeRef);
    }


}
