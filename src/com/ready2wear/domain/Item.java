package com.ready2wear.domain;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Item {
	private String ownerID;
	private String itemID;
	private List<Bitmap> images = new ArrayList<Bitmap>();
	private String size;
	private String duration;
	private String color;
	private String condition;
	private int pricePerDay;
	
	public Item(){
		
	}
	
	public Item(String oID, List<Bitmap> images, String size, String dur,
			String color, String cond, int price){
		this(oID, "", images, size, dur, color, cond, price);
	}
	
	public Item(String oID, String iID, List<Bitmap> images, String size, String dur,
			String color, String cond, int price){
		this.ownerID = oID;
		this.itemID = iID;
		this.images = images;
		this.size = size;
		this.duration = dur;
		this.color = color;
		this.condition = cond;
		this.pricePerDay = price;
	}

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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
