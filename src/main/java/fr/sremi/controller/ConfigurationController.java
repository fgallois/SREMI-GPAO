package fr.sremi.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.sremi.services.ConfigurationService;

/**
 * Created by fgallois on 8/23/15.
 */
@Controller
public class ConfigurationController {

    @Resource
    private ConfigurationService configurationService;

    @RequestMapping(value = "/configuration.json", method = RequestMethod.GET)
    public @ResponseBody GpaoConfiguration gpaoConfiguration() {
        return new GpaoConfiguration(configurationService.getInvoiceNumber(), configurationService.getArchivePath(),
                configurationService.getExcelPath());
    }

    private class GpaoConfiguration {
        private final int invoiceNumber;
        private final String archivePath;
        private final String excelPath;

        private GpaoConfiguration(int invoiceNumber, String archivePath, String excelPath) {
            this.invoiceNumber = invoiceNumber;
            this.archivePath = archivePath;
            this.excelPath = excelPath;
        }

        public int getInvoiceNumber() {
            return invoiceNumber;
        }

        public String getArchivePath() {
            return archivePath;
        }

        public String getExcelPath() {
            return excelPath;
        }
    }
}
