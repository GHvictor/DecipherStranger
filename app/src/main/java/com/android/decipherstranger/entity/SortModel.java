package com.android.decipherstranger.entity;

public class SortModel {

	private String userName; // 显示的数据
	private String sortLetters; // 显示数据拼音的首字母
	private String userAccount;
	private int userPhoto;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public int getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(int userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}