package com.monolith.tokenmint.ui.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.RedisMockDAO;
import com.monolith.shared.redis.RedisClient;
import com.monolith.shared.redis.RedisClientFactory;
import com.monolith.shared.service.ConnectWeb3Gateway;
import com.monolith.shared.utils.Constants;
import com.monolith.shared.utils.TokenMintConstants;
import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.create.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.create.dao.GameInfoDAO;
import com.monolith.tokenmint.create.dto.Web3GtwTransferTokenRequest;
import com.monolith.tokenmint.create.service.GameServerInteractionHandler;
import com.monolith.tokenmint.ui.dto.VerifyAddressRequest;
import com.monolith.tokenmint.ui.dto.VerifyAddressResponse;
import com.monolith.tokenmint.web3gateway.controller.Web3TransferTokensHandler;

@Service
public class VerifyAddressHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifyAddressHandler.class);

	@Autowired
	RedisClientFactory redisClientFactory;
	
	@Autowired
	GameServerInteractionHandler gameServerInterationHandler;
	
	@Autowired
	ConnectWeb3Gateway connectWeb3Gateway;
	
	@Autowired
	GameInfoDAO gameInfoDAO;
	
	@Autowired
	Web3TransferTokensHandler web3TransferTokensHandler;
	
	@Autowired
	RedisMockDAO redisMockDAO;
	
	@Autowired
	Environment env;
	
	
	public VerifyAddressResponse verifyAddress(VerifyAddressRequest verifyAddressRequest,
			CreateTokenTransactionBean createTokenTransactionBean) {
		String tokenMintTransactionId = createTokenTransactionBean.getTokenMintTransactionId();
		String redisKey = Constants.REDIS_KEY_TOKEN_MINT + tokenMintTransactionId;
		RedisClient<Object> redisClient = null;
		if("Y".equals(env.getProperty(Constants.MOCK_REDIS_PROPERTY))) {
			logger.debug("!!!! APPLICATION.PROP says Mock Redis Is ::Y:: getting to MySQL DB");
			String value = redisMockDAO.getRedisValue(redisKey);
			createTokenTransactionBean = Utility.parseJsonToObject(value, CreateTokenTransactionBean.class);
		}else {
			redisClient = redisClientFactory.getRedisClientForEntity(RedisClientFactory.ENTITY_TOKEN_MINT);
			createTokenTransactionBean = (CreateTokenTransactionBean)redisClient.getValue(redisKey);
			logger.info("Fetched CreateTokenTransactionBean from redis {}",createTokenTransactionBean);
			logger.info("CreateTokenTransactionBean JSON : {}",Utility.getJsonFromObject(createTokenTransactionBean));
		}
		
		
		if(createTokenTransactionBean == null) {
			logger.error("Could Not find Transaction corresponding to {}",redisKey);
			return (new VerifyAddressResponse()).setStatusCodeDesc("001", "Could Not Find The Transaction");
		}

		
		VerifyAddressResponse verifyAddressResponse = validateVerifyAddress(verifyAddressRequest,createTokenTransactionBean);
		if(!TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE.equals(verifyAddressResponse.getStatusCode())) {
			logger.error("Validation Failed For verifyAddressRequest {}",verifyAddressResponse);
			return verifyAddressResponse;
		}else {
			createTokenTransactionBean.setRecieptientAddress(verifyAddressResponse.getAddress());
		}
		
		verifyAddressResponse = verifySignerAddress(verifyAddressRequest,verifyAddressResponse,createTokenTransactionBean);
		if(!TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE.equals(verifyAddressResponse.getStatusCode())) {
			logger.error("Address verification failed {}",verifyAddressResponse);
			return verifyAddressResponse;
		}else {
			Web3GtwTransferTokenRequest web3GtwTransferTokenRequest = web3TransferTokensHandler.makeTransferTokenRequest(createTokenTransactionBean);
			connectWeb3Gateway.transferTokensRequest(createTokenTransactionBean, web3GtwTransferTokenRequest,verifyAddressResponse);
			if(!TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE.equals(verifyAddressResponse.getStatusCode())) {
				logger.error("Token Transfer Request Failed {}",verifyAddressResponse);
				return verifyAddressResponse;
			}
			logger.info("Web3GtwTransferTokenRequest :: {}",Utility.getJsonFromObject(web3GtwTransferTokenRequest));
		}
		
		verifyAddressResponse = sendVerifiedAddressAcknoweldgement(verifyAddressResponse,createTokenTransactionBean);
		if(!TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE.equals(verifyAddressResponse.getStatusCode())) {
			logger.error("Transaction not acknoweldge by game server {}",verifyAddressResponse);
			return verifyAddressResponse;
		}		
		
		
		
	
		if("Y".equals(env.getProperty(Constants.MOCK_REDIS_PROPERTY))) {
			logger.debug("!!!! APPLICATION.PROP says Mock Redis Is ::Y:: Updating to MySQL DB");
			redisMockDAO.updateRedisValue(redisKey,Utility.getJsonFromObject(createTokenTransactionBean));
		}else {
			boolean savedToCache = redisClient.putValue(redisKey, createTokenTransactionBean);
			if(!savedToCache) {
				logger.error("Saving to cache failed :: CreateTokenTrnasactionBeanCouldNotBeSaved...");
			}
		}
		createTokenTransactionBean.getEthContractInfo().getChainId();
		verifyAddressResponse.setAddressEtherScanUrl("https://sepolia.etherscan.io/address/"+verifyAddressResponse.getAddress());
		return verifyAddressResponse;
	}

	private VerifyAddressResponse sendVerifiedAddressAcknoweldgement(VerifyAddressResponse verifyAddressResponse,
			CreateTokenTransactionBean createTokenTransactionBean) {
	
		verifyAddressResponse = gameServerInterationHandler.sendAddressVerifedToGameServers(verifyAddressResponse,createTokenTransactionBean);
		
		return verifyAddressResponse;
	}

	private VerifyAddressResponse verifySignerAddress(VerifyAddressRequest verifyAddressRequest,
			VerifyAddressResponse verifyAddressResponse, CreateTokenTransactionBean createTokenTransactionBean) {
		verifyAddressResponse = connectWeb3Gateway.coneectWeb3AndVerifyAddress(verifyAddressRequest, verifyAddressResponse, createTokenTransactionBean);
		return verifyAddressResponse;
	}

	private VerifyAddressResponse validateVerifyAddress(VerifyAddressRequest verifyAddressRequest,
			CreateTokenTransactionBean createTokenTransactionBean) {
		VerifyAddressResponse verifyAddressResponse = new VerifyAddressResponse();
		verifyAddressResponse.setStatusCodeDesc("000", "Address Verifed");
		verifyAddressResponse.setAddress(verifyAddressRequest.getAddress());
		verifyAddressResponse.setTokenMintTransactionId(verifyAddressRequest.getTokenMintTransactionId());

		if(Utility.isNullOrEmpty(verifyAddressRequest.getAddress())
				|| Utility.isNullOrEmpty(verifyAddressRequest.getSignedMessage())
					|| Utility.isNullOrEmpty(verifyAddressRequest.getTokenMintTransactionId())) {
			verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Null or Empty Fields");
		}
		return verifyAddressResponse;
	}

}
