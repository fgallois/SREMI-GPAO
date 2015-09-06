package fr.sremi.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.sremi.data.CommandeData;
import fr.sremi.services.CommandeService;

/**
 * Created by fgallois on 9/4/15.
 */
@Controller
public class BonLivraisonController {

    @Resource
    private CommandeService commandeService;

    @RequestMapping(value = "/orders.json", method = RequestMethod.GET)
    public @ResponseBody List<CommandeData> gpaoConfiguration() {
        return commandeService.getAvailableCommandes();
    }
}
