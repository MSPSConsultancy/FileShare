package com.fileshare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileshare.controller.HibernateAwareObjectMapper;
import com.fileshare.model.ItemEntity;
import com.fileshare.model.ItemStatus;
import com.fileshare.model.UserEntity;
import com.fileshare.repository.ItemRepository;
import com.fileshare.repository.UsersRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileShareDataService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SessionFactory sessionFactory;

    private ObjectMapper mapper = new ObjectMapper();

    private HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();

      public void createUser(List<UserEntity> usersList) {
        usersList.forEach(user -> {
            usersRepository.saveOrUpdate(user);
        });
    }

    public void createUsers(UserEntity user){
        //
//        usersRepository.saveOrUpdate(user);

        Session session = sessionFactory.openSession();
//            item.setSharedBy(null);
        session.flush();
        session.clear();
        session.saveOrUpdate(user);
        session.flush();

        session.close();

    }

    public List<ItemEntity> createItemsToShare(List<ItemEntity> itemList){
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
        return itemList;
    }

    public void removeItem(ItemEntity item){
        itemRepository.delete(item);

    }

    public UserEntity[] users(){


        List<UserEntity> userEntity = usersRepository.findAll();

        UserEntity [] countries = userEntity.toArray(new UserEntity[userEntity.size()]);

        return countries;

    }

    public List<ItemEntity> items(){

        List<ItemStatus> itemStatusList = new ArrayList<>();
        List<ItemEntity> itemEntities = itemRepository.findAll();

        return itemEntities;
    }
}
