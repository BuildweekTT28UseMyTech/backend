package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "owneditems")
public class Owneditem extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long owneditemid;

    @OneToOne(mappedBy = "itemowned")
    @JsonIgnoreProperties(value = {"itemowned", "itemrented"}, allowSetters = true)
    private Item owneditem;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "owneditems", allowSetters = true)
    private User user;

    public Owneditem() {
    }

    public Owneditem(Item owneditem, @NotNull User user) {
        this.owneditem = owneditem;
        this.user = user;
    }

    public long getOwneditemid() {
        return owneditemid;
    }

    public void setOwneditemid(long owneditemsid) {
        this.owneditemid = owneditemsid;
    }

    public Item getOwneditem() {
        return owneditem;
    }

    public void setOwneditem(Item owneditem) {
        this.owneditem = owneditem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
