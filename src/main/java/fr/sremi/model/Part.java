package fr.sremi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by fgallois on 9/13/15.
 */
@Entity
@Table(name = "PARTS")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "reference", unique = true)
    private String reference;

    private String modification;

    @NotNull
    private Boolean isPlanTableau;

    @NotNull
    private String description;

    @NotNull
    private Boolean retired;

    private String comment;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    private List<Price> prices;

    protected Part() {
    }

    public Part(String reference, String modification, Boolean isPlanTableau, String description) {
        this.reference = reference;
        this.modification = modification;
        this.isPlanTableau = isPlanTableau;
        this.description = description;
        this.retired = Boolean.FALSE;
    }

    public Part(String reference, String description) {
        this.reference = reference;
        this.isPlanTableau = Boolean.FALSE;
        this.description = description;
        this.retired = Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
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

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
}
