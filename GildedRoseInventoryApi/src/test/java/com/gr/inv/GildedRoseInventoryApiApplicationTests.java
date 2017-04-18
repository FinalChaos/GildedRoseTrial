package com.gr.inv;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gr.inv.web.InventoryController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GildedRoseInventoryApiApplicationTests {


    @Autowired
    private MockMvc mockMvc;
    
	
    @Test
    public void inventoryListCheckSurge() throws Exception {
 
    	for (int i = 0; i <= 9; i++) {
        	this.mockMvc.perform(get("/inventoryList")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(3))).andExpect(jsonPath("$[0].price", Matchers.is(1000) ));			
		}

    	// on the tenth item surge should have happened 
    	this.mockMvc.perform(get("/inventoryList")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(3))).andExpect(jsonPath("$[0].price", Matchers.is(1100)));

    }
    
    @Test
    @WithMockUser(username="user1",password="user1Pass")
    public void purchase() throws Exception {

    	// future enhancement use Spring Message source and compare by message keys
        this.mockMvc.perform(get("/purchase?itemName=chair")).andExpect(status().isOk()).andExpect(jsonPath("$.result", Matchers.is(InventoryController.PURCHASED_SUCCESS)));
        this.mockMvc.perform(get("/purchase?itemName=chair")).andExpect(status().isOk()).andExpect(jsonPath("$.result", Matchers.is(InventoryController.OUT_OF_STOCK)));
        this.mockMvc.perform(get("/purchase?itemName=chai")).andExpect(status().isOk()).andExpect(jsonPath("$.result", Matchers.is(InventoryController.ITEM_NOT_CARRIED)));
        
    }
    
    @Test
    public void purchaseNoAuth() throws Exception {
    	// future enhancement use Spring Message source and compare by message keys
        this.mockMvc.perform(get("/purchase?itemName=chair")).andExpect(status().isUnauthorized());
    }
}
