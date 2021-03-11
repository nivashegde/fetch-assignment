package com.rewards.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Points {
	
	@JsonProperty("payer")
	private String payer;
	
	@JsonProperty("points")
	private Integer points;

	public Points(String payer, Integer points) {
		this.payer = payer;
		this.points = points;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
