package com.mymy.mymy.Dto;

public class memberCheckDto
{

	private String user_id;
	private String user_password;
	
	memberCheckDto()
	{
		
	}
	public memberCheckDto(String user_id, String user_password) {
		super();
		this.user_id = user_id;
		this.user_password = user_password;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getUser_password() {
		return user_password;
	}

	
	
}
