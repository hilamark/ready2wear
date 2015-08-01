package com.ready2wear.domain;

public enum ItemCondition {
	AS_NEW("As new", "Like new, no flaws to report"), 
	EXELLENT("Exellent", "Has been worn, no flaws to report"),
	LITTLE_USED("Little used", "Noted minor flaws"), 
	FAIR("Fair", "Worn a lot, noted minor flaws");
	
    private String desc;
	private String name;

    private ItemCondition(String desc, String name) {
            this.desc = desc;
            this.name = name;
    }
    
    public String getDesc() {
		return desc;
	}

	public String getName() {
		return name;
	}
}
