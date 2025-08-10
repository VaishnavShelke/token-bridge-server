package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.entities.TokenMintTransaction;
import com.monolith.tokenmint.repository.TokenMintTransactionRepository;

@Service
public class TokenMintTransactionDAO {
	private static final Logger logger = LoggerFactory.getLogger(TokenMintTransactionDAO.class);

	@Autowired
	private TokenMintTransactionRepository tokenMintTransactionRepository;
	
	public boolean saveTransactionToDB(String gameId,CreateTokenTransactionBean cttbean) {
		logger.info("Saving TransactionInfo to DB GameId {} and TransactionId {}",gameId,cttbean.getTokenMintTransactionId());
		String tablename = cttbean.getTableName();
		if(!saveTxnToDb( gameId, cttbean, tablename)) {
			logger.error("Could Not save Transaction to DB");
			return false;
		}else {
			logger.info("Transaction Saved to db successfully");
			return true;
		}
	}

	private boolean saveTxnToDb(String gameId, CreateTokenTransactionBean cttbean,String tablename) {
		try {
			TokenMintTransaction transaction = new TokenMintTransaction();
			transaction.setTokenMintTransactionId(cttbean.getTokenMintTransactionId());
			transaction.setTransactionSummaryJson(Utility.getJsonFromObject(cttbean));
			
			tokenMintTransactionRepository.save(transaction);
			logger.info("Transaction saved successfully to table {} ",tablename);
			return true;
			
		}catch (Exception e) {
			logger.error("Error while saving to the database {}",e.getMessage());
			return false;
		}
	}

	public boolean updateTransactionToDB(String gameId, CreateTokenTransactionBean cttbean) {
		logger.info("Updating TransactionInfo to DB GameId {} and TransactionId {}",gameId,cttbean.getTokenMintTransactionId());
		String tablename = cttbean.getTableName();
		if(!updateTxnToDb( gameId, cttbean, tablename)) {
			logger.error("Could Not save Transaction to DB");
			return false;
		}else {
			logger.info("Transaction Saved to db successfully");
			return true;
		}
	}

	private boolean updateTxnToDb(String gameId, CreateTokenTransactionBean cttbean, String tablename) {
		try {
			String transactionId = cttbean.getTokenMintTransactionId();
			if (tokenMintTransactionRepository.existsById(transactionId)) {
				TokenMintTransaction transaction = new TokenMintTransaction();
				transaction.setTokenMintTransactionId(transactionId);
				transaction.setTransactionSummaryJson(Utility.getJsonFromObject(cttbean));
				
				tokenMintTransactionRepository.save(transaction);
				logger.info("Transaction UPDATED successfully to table {} ",tablename);
				return true;
			} else {
				logger.error("Could not UPDATE transaction to table {} - transaction not found",tablename);
				return false;
			}
			
		}catch (Exception e) {
			logger.error("Error while UPDATING to the database {}",e.getMessage());
			return false;
		}
	}
}
