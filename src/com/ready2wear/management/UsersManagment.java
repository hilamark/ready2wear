package com.ready2wear.management;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.ready2wear.domain.User;

public class UsersManagment {
	
	private static final String USERS_TABLE_NAME = "Users";
	private static final String ID = "ID";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String EMAIL = "email";
	private static final String PHONE = "phoneNumber";
	private static final String ADRESS = "adress";
	protected static final int FIRST = 0;
	
	public static boolean userAlreadySignedup(String id) {
		return getUser(id) != null;
	}
	
	public static User getUser(String id) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(USERS_TABLE_NAME);
		query.whereEqualTo(ID, id);
		try {
			return new User(query.find().get(FIRST));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void removeUser(String id){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(USERS_TABLE_NAME);
		query.whereEqualTo(ID, id);
		
		try {
			List<ParseObject> userDataOnCloud = query.find();
			
			// If no such user yet, create a new one
			ParseObject userParse = userDataOnCloud.get(FIRST);
			userParse.delete();
		}catch (ParseException e) {
			e.printStackTrace();
		}
					
	}
	
	public static void saveUser(User user){
		// Search user in DB
		ParseQuery<ParseObject> query = ParseQuery.getQuery(USERS_TABLE_NAME);
		query.whereEqualTo(ID, user.getFacebookId());
		
		try {
			List<ParseObject> userDataOnCloud = query.find();
			
			// If no such user yet, create a new one
			ParseObject userParse = userDataOnCloud.isEmpty() ? new ParseObject(
					USERS_TABLE_NAME) : userDataOnCloud.get(FIRST);
					
			// Update values
			userParse.put(ID, user.getFacebookId());
			userParse.put(FIRST_NAME, user.getFirstName());
			userParse.put(LAST_NAME, user.getLastName());
			userParse.put(ADRESS, user.getAdress());
			userParse.put(PHONE, user.getPhoneNumber());
			userParse.put(EMAIL, user.getEmail());
			userParse.saveInBackground();
					
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
