package com.gr.inv.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gr.inv.model.Item;
import com.gr.inv.model.ItemNotFound;
import com.gr.inv.model.SurgePricer;

@Repository
public class InventoryDaoImpl implements InventoryDao {	
	
	// this is easy to change to property driven
	private static long MILLISECONDS_IN_HOUR = 60 * 60 * 1000;
	private static int MAX_VIEWS_IN_WINDOW = 10;
	private static int SURGE_PRICE_PERCENT = 10;
	
	private Map<String, Item> items = FakeDataGenerator.dummyItemList();
	private SurgePricer surgePricer = new SurgePricer(MILLISECONDS_IN_HOUR, MAX_VIEWS_IN_WINDOW, SURGE_PRICE_PERCENT);

	@Override
	public boolean buyItem(String name) throws ItemNotFound {
		Item item = items.get(name);
		if (item == null){
			throw new ItemNotFound("No item of name:" + name);
		}
		return item.attemptToBuy();
	}

	@Override
	public List<Item> getItems() {
		int surgeMultiplier = surgePricer.calculateSurgePriceMultiplierPercent();
		List<Item> pricedItems = new ArrayList<Item>();
		// show the user prices with the surge effects applied
		for (Item item : items.values()) {
			pricedItems.add(new Item(item.getName(), item.getDescription(), SurgePricer.applySurgeMultiplier(item.getPrice(), surgeMultiplier),item.getAvailable()));
		}
		return pricedItems;
	}

}
