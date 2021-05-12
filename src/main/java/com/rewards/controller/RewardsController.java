package com.rewards.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.dao.PointsRequest;
import com.rewards.dao.PointsResponse;
import com.rewards.dao.UserInfo;
import com.rewards.service.TransactionService;


/**
 * @author nivashegde
 * 
 * Rewards Controller class
 *
 */
@RestController
@RequestMapping(value = "/fetch-rewards")
public class RewardsController {
		
	@Autowired
	private TransactionService transactionService;
	
	private Logger logger = LoggerFactory.getLogger(RewardsController.class);
	
	
	/**
	 * Controller to add Payer Information
	 * 
	 * @endpoint - /add-transaction
	 * @body - UserInfo
	 * @returns - Status of operation
	 * 
	 */
	@PostMapping(value = "/transcations", consumes = {"application/json"})
	public ResponseEntity<?> addTransaction(@RequestBody UserInfo userInfo){
		transactionService.addUserInfo(userInfo);
		logger.info("Transaction successful - User Added " + userInfo.getPayer());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	/**
	 * Controller to add Payer Information in bulk
	 * 
	 * @endpoint - /add-transaction-bulk
	 * @body - List<UserInfo>
	 * @returns - Status of operation
	 * 
	 */
	@PostMapping(value = "/transcations/bulk", consumes = {"application/json"})
	public ResponseEntity<?> addTransactionBulk(@RequestBody List<UserInfo> userInfo){
		userInfo.stream().forEach(x -> transactionService.addUserInfo(x));		
		logger.info("Bulk transaction successful");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	/**
	 * Controller to spend points
	 * 
	 * @endpoint - /spend-points
	 * @body - Points
	 * @returns - List of user and points spent and Status of operation
	 * 
	 */
	@PostMapping(value = "/points", consumes = {"application/json"})
	public ResponseEntity<?> spendPoints(@RequestBody PointsRequest points){
		PointsResponse pointsResponse = transactionService.spendPoints(points);
		
		if(pointsResponse.getIsTransactionComplete() == true) {
			logger.info("Transaction successful");
			return new ResponseEntity<>(pointsResponse.getListOfPoints(), HttpStatus.OK);
		} else {
			logger.error("Transcation unsuccessful - Insufficient points");
			return new ResponseEntity<>(pointsResponse.getListOfPoints(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Controller to get payer information
	 * 
	 * @endpoint - /get-balances
	 * @returns - List of payers
	 * 
	 */
	@GetMapping(value = "/balances", produces = {"application/json"})
	public ResponseEntity<?> addTransaction(){
		logger.info("Transaction successful - Retrieving all values");
		return new ResponseEntity<>(transactionService.getAllUserInfo(), HttpStatus.OK);
	}

}
