package fr.sremi.data;

import java.util.List;

/**
 * Created by fgallois on 9/6/15.
 */
public class CommandeData {
    private Integer id;
    private String commandeReference;

    public CommandeData(Integer id, String commandeReference) {
        this.id = id;
        this.commandeReference = commandeReference;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommandeReference() {
        return commandeReference;
    }

    public void setCommandeReference(String commandeReference) {
        this.commandeReference = commandeReference;
    }
}
