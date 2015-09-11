package fr.sremi.util;

import java.io.File;
import java.util.Calendar;

public class InvoiceUtils {

	public static Integer getInvoiceNumber() {
		return new Integer(SremiProperties.getInstance().getProperty("invoice.number"));
	}
	
	public static void updateInvoiceNumber() {
		Integer currentInvoiceNumber = new Integer(SremiProperties.getInstance().getProperty("invoice.number"));
		SremiProperties.getInstance().setProperty("invoice.number", new Integer(currentInvoiceNumber + 1).toString() );
	}
	
	public static String getInvoiceFilePath() {
		String archivePath = SremiProperties.getInstance().getProperty("archive.file.path");

		Calendar cal = Calendar.getInstance();
		archivePath += cal.get(Calendar.YEAR) + "\\" + new Integer(cal.get(Calendar.MONTH) + 1);
		
		File path = new File(archivePath);
		if(!path.exists()) {
			path.mkdirs();
		}
		
		return archivePath + "\\";
	}

	public static String getInvoiceFileName(String invoiceNumber) {
		return "\\BL-" + invoiceNumber + ".pdf";
	}
	
	public static String getExcelPathFile() {
		return SremiProperties.getInstance().getProperty("excel.file.path");
	}
}