package com.gr.inv.fakedata;

import java.util.ArrayList;
import java.util.List;

import com.gr.inv.Item;

/***
 * Since this is a demo application with no db we are hard coding a lot of fake data.
 * This could change later - so for now put it in its own package and class
 * @author Tomas
 *
 */
public class FakeDataGenerator {

    static public List<Item> dummyItemList(){
    	List<Item> items = new ArrayList<Item>();
    	items.add(new Item("chair","Antique Chair", 1000));
    	items.add(new Item("coin","Rare Roman Denarius", 5000));
    	items.add(new Item("Action#1","Super Man Fair Condition", 100000));
    	return items;
    }
}
