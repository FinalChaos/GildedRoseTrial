package com.gr.inv.repository;

import java.util.HashMap;
import java.util.Map;

import com.gr.inv.model.Item;

/***
 * Since this is a demo application with no db we are hard coding fake data.
 * This could change later - so for now put it in its own class
 * @author Tomas
 *
 */
public class FakeDataGenerator {

    static public Map<String, Item>  dummyItemList(){
    	Map<String, Item> items = new HashMap<String, Item>();

    	addItem(items,new Item("chair","Antique Chair", 1000, 1));
    	addItem(items,new Item("coin","Rare Roman Denarius", 5000, 10));
    	addItem(items,new Item("Action1","Super Man Fair Condition", 100000, 1));
    	return items;
    }
    
    static private void addItem(Map<String, Item> map, Item item){
    	map.put(item.getName(), item);
    }
}
