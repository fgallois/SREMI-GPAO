package fr.sremi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by fgallois on 9/12/15.
 */
@Entity
public class Generator {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    private Integer number;

    @NotNull
    private String type;

    protected Generator() {
    }

    public Generator(Integer number, String type) {
        this.number = number;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
