package fr.sremi.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sremi.data.PartPaginationData;
import fr.sremi.services.CatalogService;

/**
 * Created by fgallois on 9/28/15.
 */
@RestController
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    @RequestMapping(value = "/parts.json", method = RequestMethod.GET)
    public PartPaginationData gpaoConfiguration(@RequestParam("order") String order, @RequestParam("limit") int limit,
            @RequestParam("offset") int offset,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return catalogService.getPartsByReferencePaginated(search, offset / limit, limit);
    }

}
