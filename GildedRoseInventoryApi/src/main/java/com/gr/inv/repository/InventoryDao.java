package com.gr.inv.repository;

import java.util.List;

import com.gr.inv.model.Item;
import com.gr.inv.model.ItemNotFound;

public interface InventoryDao {

	public boolean buyItem(String name) throws ItemNotFound;
	public List<Item> getItems();
}
