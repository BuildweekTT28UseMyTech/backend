package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.repository.ItemRepository;
import com.lambdaschool.foundation.repository.OwneditemRepository;
import com.lambdaschool.foundation.repository.RentedItemRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "itemService")
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemRepository itemrepos;

    @Autowired
    UserRepository userrepos;

    @Autowired
    private RentedItemRepository rentedrepos;

    @Autowired
    OwneditemRepository owneditemRepository;

    @Override
    public List<Item> findAll() {

        List<Item> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        itemrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;

    }

    @Override
    public Item findItemById(long id) throws ResourceNotFoundException{
        return itemrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    @Transactional
    public Item save(Item item) {
        Item newItem = new Item();

        newItem.setPrice(item.getPrice());
        newItem.setItemname(item.getItemname());


        return itemrepos.save(newItem);
    }

    @Transactional
    @Override
    public Item createItem(Item item) {
        Item newItem = save(item);

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("name is"+ name);

        Owneditem owneditem = new Owneditem(newItem, userrepos.findByUsername(name));
        owneditem = owneditemRepository.save(owneditem);

        newItem.setItemowned(owneditem);
        return newItem;
    }

    @Transactional
    @Override
    public Item rentItem(Item item){

        // Get the current user
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userrepos.findByUsername(name);

        // Add the rented item to the Item
        RentedItem rentedItem = new RentedItem(item, curUser);
        item.setItemrented(rentedItem);

        // Add the rented item to the User
        curUser.getRenteditems().add(rentedItem);

        return item;
    }

    @Transactional
    @Override
    public void delete(long id) {
        itemrepos.deleteByItemid(id);
    }

    @Override
    @Transactional
    public void unRent(long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userrepos.findByUsername(name);

        Item item = findItemById(id);

        // Delete the rented item from the Item
        for (int i = 0; i < curUser.getRenteditems().size(); i++){
            if (curUser.getRenteditems().get(i).getRentedItem().getItemid() == id){
                item.setItemrented(null);
                curUser.getRenteditems().remove(i);
            }
        }
    }
}
