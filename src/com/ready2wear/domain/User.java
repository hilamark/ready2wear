package com.ready2wear.domain;

import java.util.List;

import com.facebook.Profile;
import com.parse.ParseObject;

import android.graphics.Bitmap;

public class User {

	private String firstName = "";
	private String lastName = "";
	private String facebookId = "";
	private Bitmap profilePic;
	private String adress = "";
	private String phoneNumber = "";
	private String email = "";
	private List<String> itemsID;
	
	public static final String TAG = "User";
	
	public User(){
		super();
	}
	
	public User(Bitmap profilePic, String name, String lName,
			String facebookId, String location, String email,
			int yearOfBirth, String phone) {
		this.profilePic = profilePic;
		this.firstName = name;
		this.lastName = lName;
		this.facebookId = facebookId;
		this.adress = location;
		this.phoneNumber = phone;
		this.email = email;
	}
	
	public User(ParseObject po) {
		facebookId = po.getString("ID");
		firstName = po.getString("firstName");
		lastName = po.getString("lastName");
		adress = po.getString("adress");
		phoneNumber = po.getString("phoneNumber");
		email = po.getString("email");
	}

	public User(Profile currentProfile) {
		this.facebookId = currentProfile.getId();
		this.firstName = currentProfile.getFirstName();
		this.lastName = currentProfile.getLastName();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Bitmap getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Bitmap profilePic) {
		this.profilePic = profilePic;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getItems() {
		return itemsID;
	}

	public void setItems(List<String> items) {
		this.itemsID = items;
	}
	
	public void addItem(String item){
		this.itemsID.add(item);
	}
	
}
