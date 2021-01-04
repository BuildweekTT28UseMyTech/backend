package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "renteditems")
public class RentedItem extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long renteditemid;

    @OneToOne(mappedBy = "itemrented")
    @JsonIgnoreProperties(value = {"itemrented", "itemowned"}, allowSetters = true)
    private Item rentedItem;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "renteditems", allowSetters = true)
    private User user;

    public RentedItem() {
    }

    public RentedItem(Item rentedItem, User user) {
        this.renteditemid = renteditemid;
        this.rentedItem = rentedItem;
        this.user = user;
    }

    public long getRenteditemid() {
        return renteditemid;
    }

    public void setRenteditemid(long renteditemid) {
        this.renteditemid = renteditemid;
    }

    public Item getRentedItem() {
        return rentedItem;
    }

    public void setRentedItem(Item rentedItem) {
        this.rentedItem = rentedItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
