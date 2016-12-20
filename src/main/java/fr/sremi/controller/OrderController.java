package fr.sremi.controller;

import fr.sremi.data.OrderData;
import fr.sremi.data.OrderDetailData;
import fr.sremi.data.invoice.InvoiceData;
import fr.sremi.services.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public InvoiceData getOpenOrderDetails(@PathVariable String commandeRef) {
        return orderService.getInvoiceData(commandeRef);
    }

    @RequestMapping(value = "/updateOrderLineItem.json", method = RequestMethod.PUT)
    public void updateLineItem(@RequestBody OrderDetailData orderDetailData) {
        orderService.updateLineItemPrice(orderDetailData);
    }

    //    @RequestMapping(value = "/paginated-orders.json", method = RequestMethod.GET)
    //    public OrderPaginationData gpaoConfiguration(@RequestParam("limit") int limit, @RequestParam("offset") int offset,
    //      @RequestParam(value = "search", required = false, defaultValue = "") String search) {
    //        //        return catalogService.getPartsByReferenceOrDescriptionPaginated(search, offset / limit, limit);
    //    }

}
