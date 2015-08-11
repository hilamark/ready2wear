package com.ready2wear.domain;

import com.facebook.Profile;

public class SessionData {

	private static SessionData sessionData = null;
	private User currentUser = null;
	private Profile currentProfile;
	
	public static SessionData getInstance(){
		if (sessionData == null){
			sessionData = new SessionData();
		}
		return sessionData;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Profile getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(Profile currentProfile) {
		this.currentProfile = currentProfile;
	}
}
