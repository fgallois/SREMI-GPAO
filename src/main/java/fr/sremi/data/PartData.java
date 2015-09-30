package fr.sremi.data;

/**
 * Created by fgallois on 9/28/15.
 */
public class PartData {
    private String reference;
    private String indice;
    private Boolean isPlanTableau;
    private String description;
    private String comment;

    public PartData(String reference, String indice, Boolean isPlanTableau, String description, String comment) {
        this.reference = reference;
        this.indice = indice;
        this.isPlanTableau = isPlanTableau;
        this.description = description;
        this.comment = comment;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public Boolean getIsPlanTableau() {
        return isPlanTableau;
    }

    public void setIsPlanTableau(Boolean isPlanTableau) {
        this.isPlanTableau = isPlanTableau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
