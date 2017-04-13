package com.gr.inv.model;

public class Item {

	private String name;
	private String description;
	private int price;
	
	
	public Item() {
		super();
	}
	
	
	public Item(String name, String description, int price) {
		this();
		this.name = name;
		this.description = description;
		this.price = price;
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
	
	
}
