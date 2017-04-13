/**
 * 
 */
package com.gr.inv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gr.inv.model.Item;
import com.gr.inv.model.ItemNotFound;
import com.gr.inv.repository.InventoryDao;

/**
 * Implementation of Inventory Manager not really needed but allows application
 * to use DB at some point
 * @author Tomas
 *
 */
@Service
public class InventoryManagerImpl implements InventoryManager {

	private InventoryDao inventoryDao;
	

	@Autowired
	public void setInventoryDao(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	/* (non-Javadoc)
	 * @see com.gr.inv.service.InventoryManager#buyItem(java.lang.String)
	 */
	@Override
	public boolean buyItem(String name) throws ItemNotFound {
		return inventoryDao.buyItem(name);
	}

	/* (non-Javadoc)
	 * @see com.gr.inv.service.InventoryManager#getItemList()
	 */
	@Override
	public List<Item> getItemList() {
		return inventoryDao.getItems();
	}

}
