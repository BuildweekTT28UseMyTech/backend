package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item save(Item item);
    Item findItemById(long id);
    Item createItem(Item item);
    Item rentItem(Item item);
    void delete(long id);


    public void unRent(long id);
}
