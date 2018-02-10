package com.fileshare;

import com.fileshare.controller.ItemController;
import com.fileshare.model.ItemEntity;
import com.fileshare.model.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileshareApplicationUnitTest {

    private ArrayList<ItemEntity> items= new ArrayList<>();

    private ArrayList<UserEntity> userEntities= new ArrayList<>();

    private final String ITEM_1 = "Item  1";
    private final String ITEM_2 = "Item  2";
    private final String ITEM_3 = "Item  3";

    private final String USER_1 = "user name 1";
    private final String USER_2 = "user name 2";
    private final String USER_3 = "user name 3";
    private final String USER_4 = "user name 4";

    // users list
    UserEntity user1 = new UserEntity();
    UserEntity user2 = new UserEntity();
    UserEntity user3 = new UserEntity();
    UserEntity user4 = new UserEntity();

    // item list
    ItemEntity item1 = new ItemEntity();
    ItemEntity item2 = new ItemEntity();
    ItemEntity item3 = new ItemEntity();



    @Before
    public  void initSpec() throws Exception {


        setUpTestUsers();

        setUpTestItems();
    }
    @Test
    public void testCreateUsers() throws IOException {
        //  create mock
        ItemController itemController = mock(ItemController.class);

        // define return value for method createUsers()
        when(itemController.createUsers(userEntities)).thenReturn(new ResponseEntity<List<UserEntity>>(userEntities, HttpStatus.OK));

        // use mock in test....

        List<UserEntity> userEntitiesResult = itemController.createUsers(userEntities).getBody();

           Boolean userCheck = false;

       for (UserEntity userEntity : userEntitiesResult) {
           if (userEntity.getUserName().equals("user name 1"))
               userCheck = true;
       }

        assertEquals(userCheck, true);
    }

    @Test
    public void testCreateItems() throws IOException {
        //  create mock
        ItemController itemController = mock(ItemController.class);

        // define return value for method createUsers()
        when(itemController.createItemsToShare(items)).thenReturn(new ResponseEntity<>(items,HttpStatus.OK));

        // use mock in test....

        List<ItemEntity> itemEntitiesResult = itemController.createItemsToShare(items).getBody();

        Boolean userCheck = false;

        for (ItemEntity itemEntity : itemEntitiesResult) {
            if (itemEntity.getItemName().equals("Item  1"))
                userCheck = true;
        }

        assertEquals(userCheck, true);
    }


    @Test
    public void testRemoveItems() throws IOException {
        //  create mock
        ItemController itemController = mock(ItemController.class);

        // define return value for method createUsers()
        when(itemController.removeItem(item1)).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

        assertEquals(itemController.removeItem(item1).getBody(), true);
    }

    @Test
    public void testGetItems() throws IOException {
        //  create mock
        ItemController itemController = mock(ItemController.class);

        // define return value for method createUsers()
        when(itemController.getItems()).thenReturn(new ResponseEntity<>(items, HttpStatus.OK));

        assertEquals(itemController.getItems().getBody().size(), 3);
    }

    @Test
    public void testGetUsers() throws IOException {
        //  create mock
        ItemController itemController = mock(ItemController.class);

        UserEntity[] userEntitiesArray = userEntities.toArray(new UserEntity[userEntities.size()]);

        // define return value for method createUsers()
        when(itemController.getUsers()).thenReturn(new ResponseEntity<>(userEntitiesArray, HttpStatus.OK));

        assertEquals(itemController.getUsers().getBody().length, 4);
    }

    void setUpTestUsers() {


        //		user1.setUserId(1);
        user1.setUserName("user 1");
        user1.setUserName(USER_1);

        //		user1.setUserId(2);
        user2.setUserName("user 2");
        user2.setUserName(USER_2);

        //		user1.setUserId(3);
        user3.setUserName("user 3");
        user3.setUserName(USER_3);

        //		user1.setUserId(2);
        user4.setUserName("user 4");
        user4.setUserName(USER_4);

        userEntities.add(user1);
        userEntities.add(user2);
        userEntities.add(user3);
        userEntities.add(user4);


    }


    void setUpTestItems() throws Exception {


        item1.setItemDescription(ITEM_1);
        item1.setItemName(ITEM_1);
        item1.setSharedBy(user1);
        item1.setUserOwned(user2);

        item2.setItemDescription(ITEM_2);
        item2.setItemName(ITEM_2);
        item2.setSharedBy(user3);
        item2.setUserOwned(user4);

        item3.setItemDescription(ITEM_3);
        item3.setItemName(ITEM_3);
        item3.setSharedBy(user1);
        item3.setUserOwned(user4);

        items.add(item1);
        items.add(item2);
        items.add(item3);


    }
}
