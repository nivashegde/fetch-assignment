package com.rewards.dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty; 

/**
 * @author nivashegde
 *
 */
public class UserInfo implements Comparable<UserInfo>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private Logger logger = LoggerFactory.getLogger(UserInfo.class);

	@JsonProperty("payer")
	private String payer;
	
	@JsonProperty("points")
	private Integer points;
	
	@JsonProperty("timestamp")
	private String timestamp;

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

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
    public String toString() { 
		return "User : " + this.payer + ", Points : " + this.points + ", Timestamp : " + this.timestamp;
	}

	/**
	 * Comparator to store Objects based on date 
	 */
	@Override
	public int compareTo(UserInfo o) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		
		Date dateOne = new Date();
		try {
			dateOne = format.parse(this.getTimestamp());
		} catch (ParseException e) {
			logger.error("Invalid time format for " + this.payer + ", initilizing with current timestamp");
			dateOne = new Date();
		}
		
		Date dateTwo = new Date();
		try {
			dateTwo = format.parse(o.getTimestamp());
		} catch (ParseException e) {
			logger.error("Invalid time format for " + o.getPayer() + ", initilizing with current timestamp");
			dateTwo = new Date();
		}
		return dateOne.compareTo(dateTwo);
	}

}
