package com.gr.inv.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gr.inv.model.Item;
import com.gr.inv.model.ItemNotFound;
import com.gr.inv.model.ItemPurchase;
import com.gr.inv.service.InventoryManager;

@RestController
public class InventoryController {

	
	private InventoryManager inventoryManager;
	

    @Autowired
	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	@RequestMapping("/inventoryList")
    public List<Item> itemsToSell() {    	
        return inventoryManager.getItemList();
    }
    
    @RequestMapping("/purchase")
    public ItemPurchase purchase(@RequestParam(value="itemName") String itemName) {
    	// names hard coded but could use Spring Message source
    	try {
			if (inventoryManager.buyItem(itemName)){
		        return new ItemPurchase("Purchased.",1,1);
			} else {
		        return new ItemPurchase("Out of stock.",0,0);	
			}
		} catch (ItemNotFound e) {
	        return new ItemPurchase("Item not carried by Gilded Rose - check the name spelling - perhaps you wanted something else.",0,0);
		}

    	
    }
    
}
