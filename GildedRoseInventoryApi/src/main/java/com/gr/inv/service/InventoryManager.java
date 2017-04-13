package com.gr.inv.service;

import java.util.List;

import com.gr.inv.model.Item;
import com.gr.inv.model.ItemNotFound;

public interface InventoryManager {

	public boolean buyItem(String name) throws ItemNotFound;
	public List<Item> getItemList();
}
