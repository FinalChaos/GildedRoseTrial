package com.gr.inv;

public class ItemPurchase {

	private String result;
	private int itemsRequested;
	private int itemsPurchased;
	
	
	public ItemPurchase() {
		super();
	}
	
	
	public ItemPurchase(String result, int itemsRequested, int itemsPurchased) {
		this();
		this.result = result;
		this.itemsRequested = itemsRequested;
		this.itemsPurchased = itemsPurchased;
	}


	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getItemsRequested() {
		return itemsRequested;
	}
	public void setItemsRequested(int itemsRequested) {
		this.itemsRequested = itemsRequested;
	}
	public int getItemsPurchased() {
		return itemsPurchased;
	}
	public void setItemsPurchased(int itemsPurchased) {
		this.itemsPurchased = itemsPurchased;
	}
	
}
