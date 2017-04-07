package fr.sremi.vo;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fgall on 4/6/2017.
 */
public class ItemCommandBuilder {
    private final Logger logger = Logger.getLogger(ItemCommandBuilder.class.getName());

    private int line;
    private int quantity;
    private Item item;
    private Date dueDate;
    private String version;

    public ItemCommandBuilder() {
        this.item = new Item();
    }

    public ItemCommand build() {
        ItemCommand itemCommand = new ItemCommand();
        itemCommand.setLine(line);
        itemCommand.setItem(item);
        itemCommand.setQuantity(quantity);
        itemCommand.setDueDate(dueDate);
        itemCommand.setVersion(version);
        return itemCommand;
    }

    public ItemCommandBuilder setLine(Cell cell) {
        this.line = cell != null ? (int) cell.getNumericCellValue() : null;
        return this;
    }

    public ItemCommandBuilder setReference(Cell cell) {
        if (cell != null)
            this.item.setReference(cell.getStringCellValue());
        return this;
    }

    public ItemCommandBuilder setDescription(Cell cell) {
        if (cell != null)
            this.item.setDescription(cell.getStringCellValue());
        return this;
    }

    public ItemCommandBuilder setEmplacement(Cell cell) {
        if (cell != null)
            this.item.setEmplacement(cell.getStringCellValue());
        return this;
    }

    public ItemCommandBuilder setQuantity(Cell cell) {
        if (cell != null) {
            this.quantity = (int) cell.getNumericCellValue();
        }
        return this;
    }

    public ItemCommandBuilder setDueDate(Cell cell) {
        if (cell != null) {
            this.dueDate = formatDate(cell.getStringCellValue());
        }
        return this;
    }

    public ItemCommandBuilder setVersion(Cell cell) {
        if (cell != null) {
            this.version = cell.getStringCellValue();
        }
        return this;
    }

    private Date formatDate(String date) {
        Date result = null;
        if (StringUtils.isNoneEmpty(date)) {
            DateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
            try {
                result = format.parse(date);

            } catch (ParseException e) {
                logger.error("Could not parse the date: " + date);
            }
        }
        return result;
    }
}
