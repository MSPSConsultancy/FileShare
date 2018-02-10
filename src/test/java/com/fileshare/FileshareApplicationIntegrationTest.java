package com.fileshare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileshare.controller.ItemController;
import com.fileshare.model.ItemEntity;
import com.fileshare.model.UserEntity;
import com.fileshare.repository.ItemRepositoryTest;
import com.fileshare.repository.UsersRepositoryTest;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileshareApplicationIntegrationTest {

	private static RequestSpecification spec;

	private MockMvc mockMvc;

	@Mock
	private ItemController itemController;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private UsersRepositoryTest userRepositoryTest;

	@Autowired
	private ItemRepositoryTest itemRepositoryTest;

	private ArrayList<ItemEntity> items= new ArrayList<>();

	private ArrayList<UserEntity> userEntities= new ArrayList<>();

	private ObjectMapper mapper = new ObjectMapper();

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

	ItemEntity[] itemsInserted = null;


	@Before
	public  void initSpec() throws Exception {
		spec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri("http://localhost:8080/")
				.addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
				.addFilter(new RequestLoggingFilter())
				.build();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		deleteTestUsers();

        setUpTestUsersInDb();

		setUpTestItemsInDb();

//		mockMvc.perform(MockMvcRequestBuilders.
//				get("/v1/deleteAllUsers"))
//				.andExpect(status().isOk()).andDo(print())
//				.andDo(print());
//
//		mockMvc.perform(MockMvcRequestBuilders.
//				get("/v1/deleteAllItems"))
//				.andExpect(status().isOk()).andDo(print())
//				.andDo(print());
	}

	@Test
	public void test1() throws Exception {

		//get latest item list form db
		MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.
				get("/v1/items"))
				.andDo(print()).andReturn();

		Gson gson = new Gson();
		itemsInserted = gson.fromJson(result.getResponse().getContentAsString(), ItemEntity[].class);

		Boolean itemPresent = false;

		for (ItemEntity itemInserted : itemsInserted){
			if(itemInserted.getItemName().contains(ITEM_1)){
				itemPresent = true;
			}
		}

		if(itemPresent){
			assertTrue("This will pass!", true);
		}else{
			assertTrue("This will fail!", false);
		}

	}

		@Test
	public void test2() throws Exception {

			//update the username
			List<ItemEntity> itemEntitiesListUpdated = Arrays.asList(itemsInserted);
			itemEntitiesListUpdated.forEach(item -> {  item.setItemName("updated username"); });

			// update items in db
			mockMvc.perform(MockMvcRequestBuilders.
					post("/v1/items/createItemsToShare")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(itemEntitiesListUpdated)))
					.andExpect(status().isOk()).andDo(print());

			//get latest item list form db
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
					get("/v1/items"))
//					.andExpect(jsonPath( "$[0].itemName" ,is("updated username" )))
					.andDo(print()).andReturn();


			Gson gson = new Gson();
			itemsInserted = gson.fromJson(result.getResponse().getContentAsString(), ItemEntity[].class);

			Boolean itemPresent = false;

			for (ItemEntity itemInserted : itemsInserted){
				if(itemInserted.getItemName().contains("updated username")){
					itemPresent = true;
				}
			}

			if(itemPresent){
				assertTrue("This will pass!", true);
			}else{
				assertTrue("This will fail!", false);
			}
	}



//	@Test
//	public void verifyAllToDoList() throws Exception {
//
//
//		when(itemController.getItems()).thenReturn(ResponseEntity.ok(items));
//
//		assertEquals( itemController.getItems().getBody().size(),3);
//
////		mockMvc.perform(MockMvcRequestBuilders.get("/v1/items").accept(MediaType.APPLICATION_JSON))
////				.andExpect(jsonPath("$", hasSize(1))).andDo(print());
//	}

//	@Test
//	public void createItem() throws Exception {
//
//
////		when(itemController.getItems()).thenReturn(ResponseEntity.ok(items));
////
////		assertEquals( itemController.getItems().getBody().size(),3);
//
////		mockMvc.perform(MockMvcRequestBuilders.get("/v1/items").accept(MediaType.APPLICATION_JSON))
////				.andExpect(jsonPath("$", hasSize(1))).andDo(print());
//	}


	@After
	public  void cleanupSpec() throws Exception {


		deleteTestUsers();

		// delete inserted users
		 if(itemsInserted != null){
			 for (ItemEntity itemInserted : itemsInserted)
				 itemRepositoryTest.delete(itemInserted);
		 }
	}


	void deleteTestUsers(){

		if(userRepositoryTest.findByUserName(USER_1) != null) {

			UserEntity[] userEntities = userRepositoryTest.findByUserName(USER_1);

			for (UserEntity userEntity : userEntities )
				userRepositoryTest.delete(userEntity);
		}


		if(userRepositoryTest.findByUserName(USER_2) != null) {

			UserEntity[] userEntities = userRepositoryTest.findByUserName(USER_2);

			for (UserEntity userEntity : userEntities )
				userRepositoryTest.delete(userEntity);
		}



		if(userRepositoryTest.findByUserName(USER_3) != null) {

			UserEntity[] userEntities = userRepositoryTest.findByUserName(USER_3);

			for (UserEntity userEntity : userEntities)
				userRepositoryTest.delete(userEntity);
		}

		if(userRepositoryTest.findByUserName(USER_4) != null) {

			UserEntity[] userEntities = userRepositoryTest.findByUserName(USER_4);

			for (UserEntity userEntity : userEntities)
				userRepositoryTest.delete(userEntity);
		}
	}

	void deleteTestItem(){

		if(itemRepositoryTest.findByItemName(ITEM_1) != null)
			itemRepositoryTest.delete(itemRepositoryTest.findByItemName(ITEM_1));


		if(itemRepositoryTest.findByItemName(ITEM_2) != null)
			itemRepositoryTest.delete(itemRepositoryTest.findByItemName(ITEM_2));


		if(itemRepositoryTest.findByItemName(ITEM_3) != null)
			itemRepositoryTest.delete(itemRepositoryTest.findByItemName(ITEM_3));

	}

	void setUpTestUsersInDb(){


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

		userEntities.forEach( userEntity -> {
					try {
						mockMvc.perform(MockMvcRequestBuilders.
								post("/v1/createUser")
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(userEntity)))

								.andExpect(status().isOk());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		);
	}

	void setUpTestItemsInDb() throws Exception {


		item1.setItemDescription(ITEM_1);
		item1.setItemName(ITEM_1);
		item1.setSharedBy(userRepositoryTest.findUniqueByUserName("user name 1"));
		item1.setUserOwned(userRepositoryTest.findUniqueByUserName("user name 2"));

		item2.setItemDescription(ITEM_2);
		item2.setItemName(ITEM_2);
		item2.setSharedBy(userRepositoryTest.findUniqueByUserName("user name 3"));
		item2.setUserOwned(userRepositoryTest.findUniqueByUserName("user name 4"));

		item3.setItemDescription(ITEM_3);
		item3.setItemName(ITEM_3);
		item3.setSharedBy(userRepositoryTest.findUniqueByUserName("user name 1"));
		item3.setUserOwned(userRepositoryTest.findUniqueByUserName("user name 4"));

		items.add(item1);
		items.add(item2);
		items.add(item3);


		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
				post("/v1/items/createItemsToShare")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(items)))
				.andExpect(status().isOk()).andReturn();


		Gson gson = new Gson();
		itemsInserted = gson.fromJson(result.getResponse().getContentAsString(), ItemEntity[].class);
	}
}
