package com.ready2wear.management;

import java.util.List;

import android.graphics.Bitmap;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.ready2wear.domain.Item;
import com.ready2wear.domain.SessionData;

public class ItemsManagment {
	
	private static final String ITEMS_TABLE_NAME = "Items";
	
	private static final String ID = "ID";
	private static final String SIZE = "size";
	private static final String PRICE_1D = "pricePerDay";
	private static final String CONDITION = "condition";
	private static final String IMAGES = "images";
	private static final String OWNERID = "ownerID";
	protected static final int FIRST = 0;
	
	public static Item getItenm(String id) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ITEMS_TABLE_NAME);
		query.whereEqualTo(ID, id);
		try {
			ParseObject itemPO = query.find().get(FIRST); 
			Item item = new Item();
			item.setCondition((String) itemPO.get(CONDITION));
			//item.setImages((Bitmap) itemPO.get(IMAGE));
			item.setPricePerDay((Integer) itemPO.get(PRICE_1D));
			item.setSize((String) itemPO.get(SIZE));
			item.setOwnerID((String) itemPO.get(OWNERID));
			item.setItemID((String) itemPO.get(ID));
			
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void saveItem(Item item){
		// Search user in DB
				ParseQuery<ParseObject> query = ParseQuery.getQuery(ITEMS_TABLE_NAME);
				query.whereEqualTo(ID, item);
				
				try {
					List<ParseObject> itemDataOnCloud = query.find();
					
					// If no such user yet, create a new one
					final ParseObject itemParse = itemDataOnCloud.isEmpty() ? new ParseObject(
							ITEMS_TABLE_NAME) : itemDataOnCloud.get(FIRST);
							
					// Update values
					itemParse.put(ID, item.getItemID());
					itemParse.put(SIZE, item.getSize());
					itemParse.put(PRICE_1D, item.getPricePerDay());
					itemParse.put(CONDITION, item.getCondition());
					itemParse.put(OWNERID, item.getOwnerID());
					itemParse.put(IMAGES, item.getImages());
					itemParse.saveInBackground(new SaveCallback() {
		                @Override
		                public void done(ParseException e) {
		                	if (e == null){
		                		SessionData.getInstance().getCurrentUser().addItemID(itemParse.getObjectId());
		                	}
		                }
		            });
							
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
	}
}
