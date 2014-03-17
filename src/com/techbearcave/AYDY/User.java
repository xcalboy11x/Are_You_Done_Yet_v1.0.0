package com.techbearcave.AYDY;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class User  {

	private String userId;
	private String userPassword;
	private String userEmail;

	public User (String id, String password, String email) {
		this.userId = id;
		this.userPassword = password;
		this.userEmail = email;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String id) {
		this.userId = id;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String password) {
		this.userPassword = password;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String email) {
		this.userEmail = email;
	}
}
