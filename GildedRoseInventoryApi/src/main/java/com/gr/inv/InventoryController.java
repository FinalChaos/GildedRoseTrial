package com.gr.inv;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gr.inv.fakedata.FakeDataGenerator;
import com.gr.inv.model.Item;
import com.gr.inv.model.ItemPurchase;

@RestController
public class InventoryController {

	private List<Item> inventoryItems  = FakeDataGenerator.dummyItemList();
	
    @RequestMapping("/inventoryList")
    public List<Item> itemsToSell() {    	
        return inventoryItems;
    }
    
    @RequestMapping("/purchase")
    public ItemPurchase purchase(@RequestParam(value="itemName") String itemName) {
        return new ItemPurchase("Purchased",1,1);
    }
    
}
