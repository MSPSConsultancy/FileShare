package com.fileshare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileshare.controller.ItemController;
import com.fileshare.model.ItemEntity;
import com.fileshare.model.UserEntity;
import com.fileshare.repository.ItemRepository;
import com.fileshare.repository.ItemRepositoryTest;
import com.fileshare.repository.UsersRepository;
import com.fileshare.repository.UsersRepositoryTest;
import com.sun.tools.javac.jvm.Items;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

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

	@Before
	public  void initSpec() throws Exception {
		spec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri("http://localhost:8080/")
				.addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
				.addFilter(new RequestLoggingFilter())
				.build();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		// users list
		UserEntity user1 = new UserEntity();
//		user1.setUserId(1);
		user1.setUserName("user 1");
		user1.setUserName("user name 1");

		UserEntity user2 = new UserEntity();
//		user1.setUserId(2);
		user2.setUserName("user 2");
		user2.setUserName("user name 2");

		UserEntity user3 = new UserEntity();
//		user1.setUserId(3);
		user3.setUserName("user 3");
		user3.setUserName("user name 3");

		UserEntity user4 = new UserEntity();
//		user1.setUserId(2);
		user4.setUserName("user 4");
		user4.setUserName("user name 4");

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


		// item list
		ItemEntity item1 = new ItemEntity();
		item1.setItemDescription("Item  1");
//		item1.setItemId(1);
		item1.setItemName("Item 1");
		item1.setSharedBy(userRepositoryTest.findByUserName("user name 1"));
		item1.setUserOwned(userRepositoryTest.findByUserName("user name 2"));

		ItemEntity item2 = new ItemEntity();
		item2.setItemDescription("Item 2");
//		item1.setItemId(2);
		item2.setItemName("Item 2");
		item2.setSharedBy(userRepositoryTest.findByUserName("user name 3"));
		item2.setUserOwned(userRepositoryTest.findByUserName("user name 4"));


		ItemEntity item3 = new ItemEntity();
		item3.setItemDescription("Item 3");
//		item1.setItemId(3);
		item3.setItemName("Item 3");
		item3.setSharedBy(userRepositoryTest.findByUserName("user name 1"));
		item3.setUserOwned(userRepositoryTest.findByUserName("user name 4"));

		items.add(item1);
		items.add(item2);
		items.add(item3);

		mockMvc.perform(MockMvcRequestBuilders.
						post("/v1/items/createItemsToShare")
						.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(items)))
				.andExpect(status().isOk());


	}
	@Test
	public void testItemsStatus() throws IOException {



		given()
				.spec(spec).
				expect().statusCode(200)

				.when()
				.get("v1/items");

	}

	@Test
	public void verifyAllToDoList() throws Exception {


		when(itemController.getItems()).thenReturn(ResponseEntity.ok(items));

		assertEquals( itemController.getItems().getBody().size(),3);

//		mockMvc.perform(MockMvcRequestBuilders.get("/v1/items").accept(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$", hasSize(1))).andDo(print());
	}

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

		itemRepositoryTest.delete(itemRepositoryTest.findByItemName("Item 1"));

		itemRepositoryTest.delete(itemRepositoryTest.findByItemName("Item 2"));

		itemRepositoryTest.delete(itemRepositoryTest.findByItemName("Item 3"));



		userRepositoryTest.delete(userRepositoryTest.findByUserName("user name 1"));

		userRepositoryTest.delete(userRepositoryTest.findByUserName("user name 2"));
		userRepositoryTest.delete(userRepositoryTest.findByUserName("user name 3"));

		userRepositoryTest.delete(userRepositoryTest.findByUserName("user name 4"));




	}
}
