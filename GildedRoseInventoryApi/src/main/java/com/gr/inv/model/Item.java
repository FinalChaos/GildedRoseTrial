package com.gr.inv.model;

public class Item {

	private String name;
	private String description;
	private int price;
	private int available;
	
	public Item() {
		super();
	}
	
	
	public Item(String name, String description, int price, int available) {
		this();
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
	}

	public int getAvailable() {
		return available;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	/**
	 * Try to buy an item. Will return true if available count of items > 0 and 
	 * change state decrementing the available amount.
	 * If number items available is not greater than zero than return false.
	 * @return true - if item was bought false otherwise
	 */
	public synchronized boolean attemptToBuy(){
		if (available > 0){
			--available;
			return true;
		} 

		return false;
	}
	
	
}
