package com.fileshare.controller;

import com.fileshare.model.ItemEntity;
import com.fileshare.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileshare.service.FileShareDataService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fileshare.repository.ItemRepository;
import com.fileshare.repository.UsersRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1")
public class ItemController {

    @Autowired
    private FileShareDataService fileShareDataService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SessionFactory sessionFactory;

    private ObjectMapper mapper = new ObjectMapper();

    private  HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();


    @Transactional
    @RequestMapping(value = "/createUsers", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserEntity>> createUsers(@RequestBody List<UserEntity> usersList) throws IOException {
    fileShareDataService.createUser(usersList);
    	return new ResponseEntity<List<UserEntity>>(usersList,HttpStatus.OK);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> createUsers(@RequestBody UserEntity user) {

        fileShareDataService.createUsers(user);

        return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/items/createItemsToShare", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemEntity>> createItemsToShare(@RequestBody ArrayList<ItemEntity> itemList) throws IOException {

        List<ItemEntity> itemEntities = fileShareDataService.createItemsToShare(itemList);

        return new ResponseEntity<List<ItemEntity>>(itemEntities, HttpStatus.OK);
    }


    @RequestMapping(value = "/items/createItemToShare", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createItemToShare(@RequestBody UserEntity user) throws IOException {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/items/removeItem", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> removeItem(@RequestBody ItemEntity itemEntity) throws IOException {
            itemRepository.delete(itemEntity);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity[]> getUsers() throws IOException {

        UserEntity[] itemEntities = fileShareDataService.users();

        return new ResponseEntity<UserEntity[]>(itemEntities, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemEntity>> getItems() throws IOException {

        List<ItemEntity> itemEntities = fileShareDataService.items();

        return new ResponseEntity<>(itemEntities, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/deleteAllItems", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public void deleteItems() throws IOException {
        itemRepository.deleteByQuery("delete from ItemEntity");
    }

    @Transactional
    @RequestMapping(value = "/deleteAllUsers", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public void deleteUsers() throws IOException {
        usersRepository.deleteByQuery("delete from UserEntity");
    }

}
