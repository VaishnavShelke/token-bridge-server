package com.monolith.tokenmint.web3gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.RedisMockDAO;
import com.monolith.shared.dao.TokenMintTransactionDAO;
import com.monolith.shared.redis.RedisClient;
import com.monolith.shared.redis.RedisClientFactory;
import com.monolith.shared.utils.Constants;
import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.beans.OnChainTxnInfo;
import com.monolith.tokenmint.create.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.create.service.GameServerInteractionHandler;

@Service
public class TransferEventPacketInHandler {

	private static final Logger logger = LoggerFactory.getLogger(TransferEventPacketInHandler.class);
	
	
	@Autowired
	RedisClientFactory redisClientFactory;
	
	@Autowired
	GameServerInteractionHandler gameServerInteractionHandler;
	
	@Autowired
	RedisMockDAO redisMockDAO;
	
	@Autowired
	TokenMintTransactionDAO tokeMintTransactionDAO;
	
	@Autowired
	Environment env;
	
	public void handlerIncomingPacket(TransferEventPacketIn transferEventPacketIn) {
		logger.info("TransferEventPacketIn :: {}",transferEventPacketIn);
		String tokenmintTxnId = transferEventPacketIn.getTokenMintTransactionId();
		tokenmintTxnId = tokenmintTxnId.substring(2);
		tokenmintTxnId = String.valueOf(Long.parseLong(tokenmintTxnId, 16));
		logger.info("TransactionId :: {}",tokenmintTxnId);
		String redisKey = Constants.REDIS_KEY_TOKEN_MINT + tokenmintTxnId;
		
		CreateTokenTransactionBean cttbean = null;
		if("Y".equals(env.getProperty(Constants.MOCK_REDIS_PROPERTY))) {
			logger.debug("!!!! APPLICATION.PROP says Mock Redis Is ::Y:: getting from MySQL DB");
			String value = redisMockDAO.getRedisValue(redisKey);
			cttbean = Utility.parseJsonToObject(value, CreateTokenTransactionBean.class);
		}else {
			RedisClient<Object> redisClient = redisClientFactory.getRedisClientForEntity(RedisClientFactory.ENTITY_TOKEN_MINT);
			cttbean = (CreateTokenTransactionBean)redisClient.getValue(redisKey);
			logger.info("Fetched CreateTokenTransactionBean from redis {}",cttbean);	
		}
		
		if(cttbean == null) {
			logger.error("!!! FATAL FATAL FATAL !!!!!!!!!! Could Not find TTransaction for redisKey :: {} ",redisKey);
		}else {
			OnChainTxnInfo octi = cttbean.getOnChainTxnInfo();
			
			TransactionRecieptInfo txnRecInfo = transferEventPacketIn.getTransactionReciept();
			octi.setBlockNumber(txnRecInfo.getBlockNumber());
			octi.setTransactionHash(txnRecInfo.getTransactionHash());
			octi.setTxnSuccessfull("Y");
			
			gameServerInteractionHandler.sendEthTokenTransferStatus(cttbean);
			
			boolean savedtodb = tokeMintTransactionDAO.updateTransactionToDB(cttbean.getGameId(), cttbean);
			if(!savedtodb) {
				logger.error("Couldn't save the transaction to db");
			}
			
			logger.info("IncomingPacket Processed Successfully");
		}
		
		
		
	}


}
