package fr.sremi.vo;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String reference;
    private String fournisseur;
    private List<ItemCommand> items;

    public Command() {
        this("", "");
    }

    public Command(String reference, String fournisseur) {
        this.reference = reference;
        this.fournisseur = fournisseur;
        this.items = new ArrayList<ItemCommand>();
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<ItemCommand> getItems() {
        return items;
    }

    public void setItems(List<ItemCommand> items) {
        this.items = items;
    }

    public void addItem(ItemCommand item) {
        items.add(item);
    }

    public String toString() {
        return this.reference;
    }
}
