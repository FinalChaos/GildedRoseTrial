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

	// messages hard coded but could use Spring Message source
	public static final String ITEM_NOT_CARRIED = "Item not carried by Gilded Rose - check the name spelling - perhaps you wanted something else.";
	public static final String OUT_OF_STOCK = "Out of stock.";
	public static final String PURCHASED_SUCCESS = "Purchased.";
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

    	try {
			if (inventoryManager.buyItem(itemName)){
		        return new ItemPurchase(PURCHASED_SUCCESS,1,1);
			} else {
		        return new ItemPurchase(OUT_OF_STOCK,0,0);	
			}
		} catch (ItemNotFound e) {
	        return new ItemPurchase(ITEM_NOT_CARRIED,0,0);
		}

    	
    }
    
}
