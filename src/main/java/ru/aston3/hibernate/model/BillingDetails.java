package ru.aston3.hibernate.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BILLING_DETAILS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILLING_DETAILS_SEQ")
    @SequenceGenerator(name = "BILLING_DETAILS_SEQ", sequenceName = "global_sequence", allocationSize = 1)
    private int id;

    private String owner;

    public BillingDetails() {
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "BillingDetails{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                '}';
    }
}
