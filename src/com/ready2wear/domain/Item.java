package com.ready2wear.domain;


import java.util.List;

import android.graphics.Bitmap;

public class Item {
	private String ownerID;
	private String itemID;
	private List<Bitmap> images;
	private ItemSize size;
	private ItemCondition condition;
	private int pricePerDay;

	public List<Bitmap> getImages() {
		return images;
	}

	public void setImages(List<Bitmap> images) {
		for (Bitmap image : images)
			addImage(image);
	}
	public void addImage(Bitmap image) {
		this.images.add(image);
	}
	public ItemSize getSize() {
		return size;
	}
	public void setSize(ItemSize size) {
		this.size = size;
	}
	public ItemCondition getCondition() {
		return condition;
	}
	public void setCondition(ItemCondition condition) {
		this.condition = condition;
	}
	public int getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(int pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public String getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
}
