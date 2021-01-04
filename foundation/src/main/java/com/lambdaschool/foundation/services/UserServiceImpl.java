package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.repository.RentedItemRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements UserService Interface
 */
@Transactional
@Service(value = "userService")
public class UserServiceImpl
    implements UserService
{
    /**
     * Connects this service to the User table.
     */
    @Autowired
    private UserRepository userrepos;



    /**
     * Connects this service to the Role table
     */
    @Autowired
    private RoleService roleService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private HelperFunctions helperFunctions;

    public User findUserById(long id) throws
                                      ResourceNotFoundException
    {
        return userrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username)
    {

        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        userrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {

        User newUser = new User();

        if (user.getUserid() != 0)
        {
            userrepos.findById(user.getUserid())
                .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
            .toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail()
            .toLowerCase());

        if (user.getType()!=null){
            newUser.setType(user.getType().toLowerCase());
        }

        newUser.getRoles()
            .clear();
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleService.findRoleById(ur.getRole()
                .getRoleid());
            newUser.getRoles()
                .add(new UserRoles(newUser,
                    addRole));
        }

        newUser.getUseremails()
            .clear();
        for (Useremail ue : user.getUseremails())
        {
            newUser.getUseremails()
                .add(new Useremail(newUser,
                    ue.getUseremail()));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(
        User user,
        long id)
    {
        User currentUser = findUserById(id);

        // update own thing
        // admin update
        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername()
                    .toLowerCase());
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getPrimaryemail() != null)
            {
                currentUser.setPrimaryemail(user.getPrimaryemail()
                    .toLowerCase());
            }

            if (user.getType()!=null)
            {
                currentUser.setType(user.getType().toLowerCase());
            }

            if (user.getRoles()
                .size() > 0)
            {
                currentUser.getRoles()
                    .clear();
                for (UserRoles ur : user.getRoles())
                {
                    Role addRole = roleService.findRoleById(ur.getRole()
                        .getRoleid());

                    currentUser.getRoles()
                        .add(new UserRoles(currentUser,
                            addRole));
                }
            }

            if (user.getUseremails()
                .size() > 0)
            {
                currentUser.getUseremails()
                    .clear();
                for (Useremail ue : user.getUseremails())
                {
                    currentUser.getUseremails()
                        .add(new Useremail(currentUser,
                            ue.getUseremail()));
                }
            }

            if (user.getOwneditems().size()  >  0){
                currentUser.getOwneditems().clear();
                for (Owneditem owneditem: user.getOwneditems()){
                    Item addItem = itemService.findItemById(owneditem.getOwneditem().getItemid());
                    currentUser.getOwneditems().add(new Owneditem(addItem, currentUser));
                }
            }

            if (user.getRenteditems().size()  >  0){
                currentUser.getRenteditems().clear();
                for (RentedItem rentedItem: user.getRenteditems()){
                    Item addItem = itemService.findItemById(rentedItem.getRentedItem().getItemid());
                    currentUser.getRenteditems().add(new RentedItem(addItem, currentUser));
                }
            }

            return userrepos.save(currentUser);
        } else
        {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        userrepos.deleteAll();
    }

    @Transactional
    @Override
    public void setRented(long itemid) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userrepos.findByUsername(name);

        Item item = itemService.findItemById(itemid);
        RentedItem toAdd = new RentedItem(item, user);

        user.getRenteditems().add(toAdd);
    }


}
