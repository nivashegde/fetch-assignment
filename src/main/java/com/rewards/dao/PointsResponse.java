package com.rewards.dao;

import java.util.ArrayList;
import java.util.List;

public class PointsResponse {
	
	private List<Points> listOfPoints;
	
	private Boolean isTransactionComplete;
	
	private Integer remainingPoints;
	
	public PointsResponse(List<Points> listOfPoints, Boolean isTransactionComplete, Integer remainingPoints) {
		super();
		setListOfPoints(listOfPoints);
		this.isTransactionComplete = isTransactionComplete;
		this.remainingPoints = remainingPoints;
	}
	
	public List<Points> getListOfPoints() {
		return listOfPoints;
	}
	
	public void setListOfPoints(List<Points> listOfPoints) {
		this.listOfPoints = new ArrayList<>();
		this.listOfPoints.addAll(listOfPoints);
	}
	
	public Boolean getIsTransactionComplete() {
		return isTransactionComplete;
	}
	
	public void setIsTransactionComplete(Boolean isTransactionComplete) {
		this.isTransactionComplete = isTransactionComplete;
	}
	
	public Integer getRemainingPoints() {
		return remainingPoints;
	}
	
	public void setRemainingPoints(Integer remainingPoints) {
		this.remainingPoints = remainingPoints;
	}
	
	
	
	
	

}
