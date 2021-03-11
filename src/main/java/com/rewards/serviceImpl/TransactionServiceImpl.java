package com.rewards.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rewards.dao.Points;
import com.rewards.dao.PointsRequest;
import com.rewards.dao.PointsResponse;
import com.rewards.dao.UserInfo;
import com.rewards.service.TransactionService;


/**
 * @author nivashegde
 * Service method to carry out operations
 */
@Component
public class TransactionServiceImpl implements TransactionService {

	//PriorityQueue of UserInfo to store data based on timestamp
	private PriorityQueue<UserInfo> userInfoList;

	//Boolean cache to return list of users if no operations has been carried out since getAllUserInfo()
	private Boolean cache = false;

	//List of all userInfo
	private List<Points> allUserInfo;


	public TransactionServiceImpl() {
		userInfoList = new PriorityQueue<UserInfo>();
	}


	/**
	 * Method to add userInfo.
	 */
	@Override
	public void addUserInfo(UserInfo userInfo) {
		cache = false;
		userInfoList.add(userInfo);
	}


	/**
	 * Spend points
	 */
	@Override
	public PointsResponse spendPoints(PointsRequest points) {

		cache = false;

		if(userInfoList.size() == 0) {
			return new PointsResponse(new ArrayList<>(), false, points.getPoints());
		}

		Integer pointsToBeProcessed = points.getPoints();

		List<UserInfo> zeroScoreList = new ArrayList<>();

		//Store Payer name and points spent
		Map<String, Integer> userInfoMap = new HashMap<>();

		while(pointsToBeProcessed > 0) {

			//If queue is empty and pointsToBeProcessed is still greater than 0, transaction unsuccessful
			if(userInfoList.size() == 0) {
				userInfoList.addAll(zeroScoreList);
				List<Points> result = userInfoMap.entrySet()
						.stream()
						.map(m -> new Points(m.getKey(), m.getValue()))
						.filter(x -> x.getPoints() < 0)
						.collect(Collectors.toList());
				return new PointsResponse(result, false, pointsToBeProcessed);
			}

			UserInfo userInfo = userInfoList.poll();
			Integer userPoints = userInfo.getPoints();

			//If points is positive value, subtract with pointsToBeProcessed
			if(userPoints > 0) {
				Integer userPointsTemp = userPoints - pointsToBeProcessed;

				if(userPointsTemp > 0) {

					userInfo.setPoints(userPointsTemp);
					userInfoMap.put(userInfo.getPayer(), 
							userInfoMap.getOrDefault(userInfo.getPayer(), 0) + (-1 * (userPoints - userPointsTemp)));

					userInfoList.add(userInfo);
					break;
				} else {

					userInfo.setPoints(0);
					zeroScoreList.add(userInfo);
					userInfoMap.put(userInfo.getPayer(), userInfoMap.getOrDefault(userInfo.getPayer(), 0) + (-1 * userPoints));
				}

				pointsToBeProcessed = pointsToBeProcessed - userPoints;

			} else 
				
				//If points is negative value, add to map
				if(userPoints < 0){
					pointsToBeProcessed += -1 * userPoints;
					userInfoMap.put(userInfo.getPayer(), userInfoMap.getOrDefault(userInfo.getPayer(), 0) + (-1 * userPoints));
				}
				else {
				//If points is 0, it's already been processed
					zeroScoreList.add(userInfo);
				}
		}

		userInfoList.addAll(zeroScoreList);

		List<Points> result = userInfoMap.entrySet()
				.stream()
				.map(m -> new Points(m.getKey(), m.getValue()))
				.filter(x -> x.getPoints() < 0)
				.collect(Collectors.toList());

		return new PointsResponse(result, true, 0);
	}


	/**
	 * Method to get All User Information
	 * 
	 * Will return only users with positive points
	 */
	@Override
	public List<Points> getAllUserInfo(){

		//Check if this method has been already called before
		if(!cache) {
			Map<String, Integer> userInfoMap = new HashMap<>();
			List<UserInfo> tempList = new ArrayList<>();

			//Fetch all values from queue
			while(!userInfoList.isEmpty()) {
				UserInfo userInfo = userInfoList.poll();
				tempList.add(userInfo);
				userInfoMap.put(userInfo.getPayer(), userInfoMap.getOrDefault(userInfo.getPayer(), 0) + userInfo.getPoints());
			}

			userInfoList.addAll(tempList);

			allUserInfo = new ArrayList<>();

			allUserInfo = userInfoMap.entrySet()
					.stream()
					.map(m->new Points(m.getKey(), m.getValue()))
					.filter(x -> x.getPoints() > 0)
					.collect(Collectors.toList());
			cache = true;
		}

		return allUserInfo;
	}



}
