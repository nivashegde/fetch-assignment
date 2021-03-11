package com.rewards.service;

import java.util.List;

import com.rewards.dao.Points;
import com.rewards.dao.PointsRequest;
import com.rewards.dao.PointsResponse;
import com.rewards.dao.UserInfo;

public interface TransactionService {
	
	/**
	 * Method to add user to memory
	 * @param userInfo
	 */
	public void addUserInfo(UserInfo userInfo);
	
	/**
	 * Method to calculate points spent
	 * @param points
	 * @return
	 */
	public PointsResponse spendPoints(PointsRequest points);
	
	/**
	 * Method to get all User Information
	 * @return List<userInfo>
	 */
	public List<Points> getAllUserInfo();

}
