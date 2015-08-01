package com.ready2wear.domain;

public class SessionData {

	private static SessionData sessionData = null;
	private User currentUser = null;
	
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
}
