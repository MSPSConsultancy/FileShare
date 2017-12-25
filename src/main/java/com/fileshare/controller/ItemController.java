package com.fileshare.controller;

import com.fileshare.model.ItemEntity;
import com.fileshare.model.ItemStatus;
import com.fileshare.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
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

//                UserEntity[] userEntities = mapper.readValue(usersList,
//                        UserEntity[].class);
        usersList.forEach(user -> {usersRepository.saveOrUpdate(user);});
    	return new ResponseEntity<List<UserEntity>>(usersList,HttpStatus.OK);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> createUsers(@RequestBody UserEntity user) {
//
//        usersRepository.saveOrUpdate(user);

        Session session = sessionFactory.openSession();
//            item.setSharedBy(null);
        session.flush();
        session.clear();
        session.saveOrUpdate(user);
        session.flush();

        session.close();

        return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
@Transactional
    @RequestMapping(value = "/items/createItemsToShare", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createItemToShare(@RequestBody ArrayList<ItemEntity> itemList) throws IOException {

//        Group group = mapper.readValue(mapper.writeValueAsString(groupRepository.searchByGroupName(groupName)),
//                Group.classss);
//        itemList.forEach(item -> {usersRepository.save(item.getUserOwned());});

//    itemList.forEach(item -> {item.setUserOwned();});

        itemList.forEach(item -> {



            Session session = sessionFactory.openSession();
//            item.setSharedBy(null);
            session.flush();
            session.clear();
            session.saveOrUpdate(item);
            session.flush();

            session.close();

//            itemRepository.flush(); itemRepository.(item);

        });
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }


    @RequestMapping(value = "/items/createItemToShare", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createItemToShare(@RequestBody UserEntity user) throws IOException {
//            usersRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/items/removeItem", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeItem(@RequestBody ItemEntity itemEntity) throws IOException {
            itemRepository.delete(itemEntity);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() throws IOException {

        List<UserEntity> userEntity = usersRepository.findAll();

        UserEntity [] countries = userEntity.toArray(new UserEntity[userEntity.size()]);

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemEntity>> getItems() throws IOException {
        List<ItemStatus> itemStatusList = new ArrayList<>();
        List<ItemEntity> itemEntities = itemRepository.findAll();

//        ItemEntity [] itemEntity = itemEntities.toArray(new ItemEntity[itemEntities.size()]);
//        String finalString = hibernateAwareObjectMapper.writeValueAsString(itemEntities);

//        for (ItemEntity itemEntity: itemEntities
//             ) {
//            ItemStatus itemStatus = new ItemStatus();
//            itemStatus.setItemId(itemEntity.getItemId());
//            itemStatus.setItemName(itemEntity.getItemName());
//            itemStatus.setOwnerId(itemEntity.getOwnerId());
//            itemStatus.setOwnerName(itemEntity.getUserOwned().getUserName());
//            itemStatus.setSharedByUserId(itemEntity.getSharedByUserId());
//            itemStatus.setSharedByUserName(itemEntity.getUserShared().getUserName());
//
//            itemStatusList.add(itemStatus);
//        }

        return new ResponseEntity<>(itemEntities, HttpStatus.OK);
    }

}
