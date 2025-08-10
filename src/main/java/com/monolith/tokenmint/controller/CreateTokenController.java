package com.monolith.tokenmint.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monolith.shared.dao.RedisMockDAO;
import com.monolith.shared.dao.TokenMintTransactionDAO;
import com.monolith.shared.redis.RedisClient;
import com.monolith.shared.redis.RedisClientFactory;
import com.monolith.shared.utils.Constants;
import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.dto.CreateTokenRequest;
import com.monolith.tokenmint.dto.CreateTokenResponse;
import com.monolith.tokenmint.service.CreateTokenHandler;

@RestController
@RequestMapping("/tokenmint")
public class CreateTokenController {

	private static final Logger logger = LoggerFactory.getLogger(CreateTokenController.class);
	
	@Autowired
	CreateTokenHandler createTokenHandler;
	
	@Autowired
	RedisClientFactory redisClientFactory;
	
	@Autowired
	RedisMockDAO redisMockDAO;
	
	@Autowired
	TokenMintTransactionDAO tokeMintTransactionDAO;
	
	@Autowired
	Environment env;
	
	
	@PostMapping(path = "/createtoken/{gameId}")
	public ResponseEntity<CreateTokenResponse> createTokenRequest(@PathVariable String gameId,@RequestBody CreateTokenRequest createTokenRequest){
		logger.info("**************************************** Create Token Request **************************************");
		logger.info("====> CreateTokenRequest :: {}", Utility.getJsonFromObject(createTokenRequest));
		CreateTokenTransactionBean createTokenTransactionBean = new CreateTokenTransactionBean();
		String tokenMintTransactionId = Utility.generateDateTimeId();
		String redisKey = Constants.REDIS_KEY_TOKEN_MINT + tokenMintTransactionId;
		
		createTokenTransactionBean.setTokenMintTransactionId(tokenMintTransactionId);
		createTokenTransactionBean.setTokenMintTransactionIdHex("0x"+Long.toHexString(Long.valueOf(tokenMintTransactionId)));
		createTokenTransactionBean.setTableName(Utility.getTransactionTableName().toLowerCase());
		CreateTokenResponse createTokenResponse = createTokenHandler.getCreateTokenResponse(createTokenRequest,createTokenTransactionBean);
		
		if("Y".equals(env.getProperty(Constants.MOCK_REDIS_PROPERTY))) {
			logger.debug("!!!! APPLICATION.PROP says Mock Redis Is ::Y:: saving to MySQL DB");
			redisMockDAO.saveInRedis(redisKey, Utility.getJsonFromObject(createTokenTransactionBean));
		}else {
			RedisClient<Object> redisClient = redisClientFactory.getRedisClientForEntity(RedisClientFactory.ENTITY_TOKEN_MINT);
			boolean savedToCache = redisClient.putValue(redisKey, createTokenTransactionBean);
			if(!savedToCache){
				logger.error("!!! FATAL !!! TransactionCould not be saved to cache");
			}else {
				logger.info("CreateTokenTransactionBean :: {}",createTokenTransactionBean);
			}
		}
	
		
		boolean savedtodb = tokeMintTransactionDAO.saveTransactionToDB(createTokenTransactionBean.getGameId(), createTokenTransactionBean);
		if(!savedtodb) {
			logger.error("Couldn't save the transaction to db {}",createTokenTransactionBean.getTokenMintTransactionId());
		}
		
		logger.info("**************************************** Create Token Response **************************************");
		return new ResponseEntity<>(createTokenResponse,HttpStatus.OK);
	}
}
