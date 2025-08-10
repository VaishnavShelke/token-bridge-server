package com.monolith.tokenmint.service;

import java.util.ArrayList;

import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import com.monolith.tokenmint.entities.GameItemsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.RedisMockDAO;
import com.monolith.shared.redis.RedisClient;
import com.monolith.shared.redis.RedisClientFactory;
import com.monolith.shared.utils.Constants;
import com.monolith.shared.utils.TokenMintConstants;
import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.entities.GameInfoEntity;
import com.monolith.tokenmint.beans.PlayerInfo;
import com.monolith.tokenmint.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.beans.TokenMintTransactionInfo;

import com.monolith.tokenmint.dto.LoadUIDTORequest;
import com.monolith.tokenmint.dto.LoadUIDTOResponse;

@Service
public class LoadUIServiceHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadUIServiceHandler.class);
	
	@Autowired
	RedisClientFactory redisClientFactory;
	

	
	@Value("${verify.address.url}")
	private String verifyAddressUrl;
	
	
	@Autowired
	RedisMockDAO redisMockDAO;
	
	@Autowired
	Environment env;
	

	public LoadUIDTOResponse getLoadUIResponse(LoadUIDTORequest loadUIDTORequest,
			CreateTokenTransactionBean createTokenTransactionBean) {
		
		String tokenMintTransactionId = createTokenTransactionBean.getTokenMintTransactionId();
		String redisKey = Constants.REDIS_KEY_TOKEN_MINT + tokenMintTransactionId;
		logger.debug("Redis Key {}",redisKey);
		
		if("Y".equals(env.getProperty(Constants.MOCK_REDIS_PROPERTY))) {
			logger.debug("!!!! APPLICATION.PROP says Mock Redis Is ::Y:: getting to MySQL DB");
			String value = redisMockDAO.getRedisValue(redisKey);
			createTokenTransactionBean = Utility.parseJsonToObject(value, CreateTokenTransactionBean.class);
		}else {
			RedisClient<Object> redisClient = redisClientFactory.getRedisClientForEntity(RedisClientFactory.ENTITY_TOKEN_MINT);
			createTokenTransactionBean = (CreateTokenTransactionBean)redisClient.getValue(redisKey);
			logger.info("Fetched CreateTokenTransactionBean from redis {}",createTokenTransactionBean);
		}
	
		
		LoadUIDTOResponse loadUIDTOResponse = new LoadUIDTOResponse();
		if(createTokenTransactionBean == null) {
			logger.error("Could Not find Transaction corresponding to {}",redisKey);
			return loadUIDTOResponse.setStatusCodeDesc("001", "Could Not Find The Transaction");
		}
		
		loadUIDTOResponse = validateLoadUIDTORequest(loadUIDTORequest,loadUIDTOResponse,createTokenTransactionBean);
		if(!TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE.equals(loadUIDTOResponse.getStatusCode())) {
			logger.error("Validation Failed Response {}",loadUIDTOResponse);
			return loadUIDTOResponse;
		}
		
		loadUIDTOResponse = fillLoadUIResponse(loadUIDTORequest,loadUIDTOResponse,createTokenTransactionBean);
		logger.info("Load UI DTO Response {}",loadUIDTOResponse);
		
		
		return loadUIDTOResponse;
	}

	private LoadUIDTOResponse fillLoadUIResponse(LoadUIDTORequest loadUIDTORequest,
			LoadUIDTOResponse loadUIDTOResponse, CreateTokenTransactionBean createTokenTransactionBean) {
		
		loadUIDTOResponse.setTokenMintTransactionId(createTokenTransactionBean.getTokenMintTransactionId());
		
		PlayerInfo playerInfo = new PlayerInfo();
		PlayerInfo plinfo = createTokenTransactionBean.getPalyerInfo();
		
		GameInfoEntity gminfo = createTokenTransactionBean.getGameInfoEntity();
		GameItemsEntity itmbean = createTokenTransactionBean.getGameItemsEntity();
		ETHContractInfoEntity ethinfo = createTokenTransactionBean.getEthContractInfoEntity();
		
		playerInfo.setPlayerId(plinfo.getPlayerId());
		playerInfo.setPlayerName(plinfo.getPlayerName());
		playerInfo.setPlayerProfilePic(plinfo.getPlayerProfilePic());
		playerInfo.setGameLogo(gminfo.getGameLogo());
		playerInfo.setGameName(gminfo.getGameName());
		playerInfo.setPlayerEthAccounts(new ArrayList<String>());
		
		TokenMintTransactionInfo tokenMintTransactionInfo = new TokenMintTransactionInfo();
		tokenMintTransactionInfo.setTokenMintTransactionId(createTokenTransactionBean.getTokenMintTransactionId());
		tokenMintTransactionInfo.setGameId(gminfo.getGameId());
		tokenMintTransactionInfo.setGameTransactionId(createTokenTransactionBean.getGameTransactionId());
		tokenMintTransactionInfo.setVerifyAddressUrl(verifyAddressUrl.concat(createTokenTransactionBean.getTokenMintTransactionId()));
		tokenMintTransactionInfo.setGameLandingPage(createTokenTransactionBean.getGameLandingPage());
		
		GameItemsEntity itmInfoBean = new GameItemsEntity();
		itmInfoBean.setItemId(itmbean.getItemId());
		itmInfoBean.setItemCategory(itmbean.getItemCategory());
		itmInfoBean.setItemImgUrl(itmbean.getItemImgUrl());
		itmInfoBean.setItemQuantity(itmbean.getItemQuantity());
		itmInfoBean.setItemTitle(itmbean.getItemTitle());
		
		
		ETHContractInfoEntity ethContractInfoEntity = new ETHContractInfoEntity();
		ethContractInfoEntity.setChain(ethinfo.getChain());
		ethContractInfoEntity.setContractName(ethinfo.getContractName());
		ethContractInfoEntity.setContractAddress(ethinfo.getContractAddress());
		ethContractInfoEntity.setChainId(ethinfo.getChainId());
		ethContractInfoEntity.setEtherscanContractUrl(ethinfo.getEtherscanContractUrl());
		ethContractInfoEntity.setChainCurrency(ethinfo.getChainCurrency());

		
//		TokenMintUserInfo fetchedTokenMintUserInfo = fetchTokenMintUser.getTokenMintUserInfoByGameIdPlayerId(gminfo.getGameId(),plinfo.getPlayerId());
//		TokenMintUserInfo tokenMintUserInfo = new TokenMintUserInfo();
//		tokenMintUserInfo.setTokenMintUserId(fetchedTokenMintUserInfo.getTokenMintUserId());
//		tokenMintUserInfo.setTokenMintUserLogo(fetchedTokenMintUserInfo.getTokenMintUserLogo());
//		tokenMintUserInfo.setTokenMintUserName(fetchedTokenMintUserInfo.getTokenMintUserName());
//		tokenMintUserInfo.setTokenMintUserProfileRedirectionURL(fetchedTokenMintUserInfo.getTokenMintUserProfileRedirectionURL());
		
		loadUIDTOResponse.setContractInfo(ethContractInfoEntity);
		loadUIDTOResponse.setPlayerInfo(playerInfo);
		loadUIDTOResponse.setTokenMintUserInfo(null);
		loadUIDTOResponse.setTokenMintTransactionInfo(tokenMintTransactionInfo);
		loadUIDTOResponse.setItemInfo(itmInfoBean);
		return loadUIDTOResponse;
	}

	private LoadUIDTOResponse validateLoadUIDTORequest(LoadUIDTORequest loadUIDTORequest,
			LoadUIDTOResponse loadUIDTOResponse, CreateTokenTransactionBean createTokenTransactionBean) {
		loadUIDTOResponse.setStatusCode(TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE);
		loadUIDTOResponse.setStatusDescription("Successfully Loaded the Screen");
		
		
		return loadUIDTOResponse;
	}

}
