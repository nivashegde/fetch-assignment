package com.rewards.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PointsRequest {
	
	@JsonProperty("points")
	private Integer points;

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
}
