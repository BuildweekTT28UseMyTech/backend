package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemid;

    @NotNull
    @Column
    private String itemname;

    @NotNull
    @Column
    private double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owneditemid", referencedColumnName = "owneditemid")
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Owneditem itemowned;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "renteditemid", referencedColumnName = "renteditemid")
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private RentedItem itemrented;

//    @Column
//    private boolean isRented = false;

    public Item() {
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Owneditem getItemowned() {
        return itemowned;
    }

    public void setItemowned(Owneditem itemowned) {
        this.itemowned = itemowned;
    }

    public RentedItem getItemrented() {
        return itemrented;
    }

    public void setItemrented(RentedItem itemrented) {
        this.itemrented = itemrented;
    }

//    public boolean isRented() {
//        return isRented;
//    }
//
//    public void setRented(boolean rented) {
//        isRented = rented;
//    }
}
