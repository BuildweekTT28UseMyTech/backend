package com.lambdaschool.foundation.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.foundation.models.User;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class ItemView {

    private double price;
    private String name;

    @JsonIgnoreProperties(value = {"user", "useremails", "type", "roles", "owneditems", "renteditems"}, allowSetters = true)
    private User owner;

    @JsonIgnoreProperties(value = {"user", "useremails", "type", "roles", "renteditems", "owneditems"}, allowSetters = true)
    private User renter;

    private long id;

    public ItemView(double price, String name, User owner, long id) {
        this.price = price;
        this.name = name;
        this.owner = owner;
        this.id = id;
    }

    public ItemView(double price, String name, User owner, User renter, long id) {
        this.price = price;
        this.name = name;
        this.owner = owner;
        this.renter = renter;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ItemView{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", renter=" + renter +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }
}
