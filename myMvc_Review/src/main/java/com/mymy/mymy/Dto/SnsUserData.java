package com.mymy.mymy.Dto;

public class SnsUserData {
	private String nickname;
	private String profile_image;
	private String thumbnail_image;
	private String birthday;
	private String gender;
	private String tokenNumber;
	private String email;
	public SnsUserData() {
		
	}
	
	
	public SnsUserData(String nickname, String profile_image, String thumbnail_image, String birthday, String gender,
			String tokenNumber, String email) {
		super();
		this.nickname = nickname;
		this.profile_image = profile_image;
		this.thumbnail_image = thumbnail_image;
		this.birthday = birthday;
		this.gender = gender;
		this.tokenNumber = tokenNumber;
		this.email = email;
	}


	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
	public String getThumbnail_image() {
		return thumbnail_image;
	}
	public void setThumbnail_image(String thumbnail_image) {
		this.thumbnail_image = thumbnail_image;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTokenNumber() {
		return tokenNumber;
	}
	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "SnsUserData [nickname=" + nickname + ", profile_image=" + profile_image + ", thumbnail_image="
				+ thumbnail_image + ", birthday=" + birthday + ", gender=" + gender + ", tokenNumber=" + tokenNumber
				+ ", email=" + email + ", getNickname()=" + getNickname() + ", getProfile_image()=" + getProfile_image()
				+ ", getThumbnail_image()=" + getThumbnail_image() + ", getBirthday()=" + getBirthday()
				+ ", getGender()=" + getGender() + ", getTokenNumber()=" + getTokenNumber() + ", getEmail()="
				+ getEmail() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
}
