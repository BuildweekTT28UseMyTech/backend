package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Item;
import com.lambdaschool.foundation.models.RentedItem;
import com.lambdaschool.foundation.services.ItemService;
import com.lambdaschool.foundation.views.ItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/item/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        Item item = itemService.findItemById(id);
        ItemView view = new ItemView(item.getPrice(), item.getItemname(), item.getItemowned().getUser(), item.getItemid());

        if (item.getItemrented()!=null){
            view.setRenter(item.getItemrented().getUser());
        }

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    @GetMapping(value = "/items")
    public ResponseEntity<?> findAll(){
        List<Item> list = itemService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/item", consumes = "application/json")
    public ResponseEntity<?> addNewItem(@RequestBody Item item){
        item = itemService.createItem(item);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newItemURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemid}")
                .buildAndExpand(item.getItemid())
                .toUri();
        responseHeaders.setLocation(newItemURI);

//        double price, String name, User owner, User renter
        ItemView view = new ItemView(item.getPrice(), item.getItemname(), item.getItemowned().getUser(), item.getItemid());

        return new ResponseEntity<>(view,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @PutMapping(value = "/rent/{id}", consumes = "application/json")
    public ResponseEntity<?> rentItem(@PathVariable long id){
        Item item = itemService.findItemById(id);

        item = itemService.rentItem(item);

        ItemView view = new ItemView(item.getPrice(), item.getItemname(), item.getItemowned().getUser(), item.getItemrented().getUser(), item.getItemid());

        return new ResponseEntity<>(view,HttpStatus.OK);
    }

    @DeleteMapping(value = "/item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable long id){
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/unrent/{id}")
    public ResponseEntity<?> unrent (@PathVariable long id){

        itemService.unRent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
