package com.ready2wear.domain;

public enum ItemSize {
	SMALL(6, "Small"),
	MEDUIM(10, "Meduim"),
	LARGE(12, "Large");
	
	private int size;
	private String name;

	private ItemSize(int size, String name){
		this.size = size;
		this.name = name;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getName() {
		return name;
	}
}
