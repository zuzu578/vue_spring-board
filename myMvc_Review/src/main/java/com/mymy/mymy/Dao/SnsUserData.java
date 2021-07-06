package com.mymy.mymy.Dao;

import java.util.ArrayList;

import com.mymy.mymy.Dto.Dto;

public interface SnsUserData {
	public void insertSnSUserData(String nickname, String profile_image, String thumbnail_image, String birthday,
			String gender, String tokenNumber,String email);

	public ArrayList<Dto> selectUserData(String tokens);

	public void tokenDelete(String tokenNumber);
}
