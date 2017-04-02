package fr.sremi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by fgall on 3/28/2017.
 */
@Entity
@Table(name = "CLIENTS")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private String numeroIntracommunautaire;

    @NotNull
    private String orderFilename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNumeroIntracommunautaire() {
        return numeroIntracommunautaire;
    }

    public void setNumeroIntracommunautaire(String numeroIntracommunautaire) {
        this.numeroIntracommunautaire = numeroIntracommunautaire;
    }

    public String getOrderFilename() {
        return orderFilename;
    }

    public void setOrderFilename(String orderFilename) {
        this.orderFilename = orderFilename;
    }
}
