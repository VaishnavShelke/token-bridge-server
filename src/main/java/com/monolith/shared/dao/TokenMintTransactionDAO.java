package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.create.beans.CreateTokenTransactionBean;

@Service
public class TokenMintTransactionDAO {
	private static final Logger logger = LoggerFactory.getLogger(TokenMintTransactionDAO.class);

	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;
	
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
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ");
			sb.append(tablename);
			sb.append(" (tokenmint_transaction_id,transaction_summary_json)");
			sb.append(" VALUES ( ");
			sb.append(" ?, ?)");
			

			String insertQueryString = sb.toString();
			Object[] params = {
					cttbean.getTokenMintTransactionId(),Utility.getJsonFromObject(cttbean)
			};
			
			int row = globalJdbcTemplate.update(insertQueryString,params);
			if(row == 1) {
				logger.info("Transaction saved sucessfullly to table {} ",tablename);
				return true;
			}else {
				logger.error("Could not save transaction to table {} ",tablename);
				return false;
			}
			
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
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE ");
			sb.append(tablename);
			sb.append(" SET `transaction_summary_json` = ? ");
			sb.append(" WHERE `tokenmint_transaction_id` = ? ");
			
			String updateQueryString = sb.toString();
			Object[] params = {
					Utility.getJsonFromObject(cttbean),
					cttbean.getTokenMintTransactionId()
			};
			
			int row = globalJdbcTemplate.update(updateQueryString,params);
			if(row == 1) {
				logger.info("Transaction UPDATED sucessfullly to table {} ",tablename);
				return true;
			}else {
				logger.error("Could not UPDATE transaction to table {} ",tablename);
				return false;
			}
			
		}catch (Exception e) {
			logger.error("Error while UPDATING to the database {}",e.getMessage());
			return false;
		}
	}
}
