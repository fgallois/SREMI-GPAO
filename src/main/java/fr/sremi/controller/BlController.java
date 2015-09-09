package fr.sremi.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.sremi.data.BLData;
import fr.sremi.services.BLService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by fgallois on 9/7/15.
 */
@RestController
@RequestMapping("/bonLivraison")
public class BlController {

    @Resource
    private BLService blService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createBonLivraison(@RequestBody BLData blData) {

        String filename = blService.createBL(blData);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
          .fromCurrentRequest().path("/{filename}")
          .buildAndExpand(filename).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{filename}", method = RequestMethod.GET)
    public String readBonLivraison(@PathVariable String filename) {
//        TODO Read file and return in response
        return filename;
    }
}
