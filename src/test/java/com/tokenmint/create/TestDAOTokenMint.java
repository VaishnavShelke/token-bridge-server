package com.tokenmint.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.monolith.MonolithApplication;
import com.monolith.shared.dao.ConfigResourceDAO;
import com.monolith.shared.dao.EthContractInfoDAO;
import com.monolith.shared.dao.GameConfigDAO;
import com.monolith.shared.dao.GameItemsOnChainInfoDAO;
import com.monolith.shared.dao.ItemInfoDAO;
import com.monolith.shared.dao.OperatorInfoDAO;
import com.monolith.tokenmint.create.dao.GameInfoDAO;

@SpringBootTest
@ContextConfiguration(classes = MonolithApplication.class)
public class TestDAOTokenMint {

	@Autowired
	GameConfigDAO gameConfigDAO;
	
	@Autowired
	ItemInfoDAO itemInfoDAO;
	
	@Autowired
	GameInfoDAO gameInfoDAO;
	
	@Autowired
	EthContractInfoDAO ethContractInfoDAO;
	
	@Autowired
	ConfigResourceDAO configResourceDAO;
	
	@Autowired
	OperatorInfoDAO operatorInfoDAO;
	
	@Autowired
	GameItemsOnChainInfoDAO gameItemsOnChainInfoDAO;
	
	@Test
	public void testMain() {
		try {
//			System.out.println(gameConfigDAO.getGameInstannceDataSource("1001"));
//			System.out.println(itemInfoDAO.getItemInfoFromItemIdForGame("1001", "1001_10005"));
//			System.out.println(gameInfoDAO.getGameInfoByGameId("1001"));
//			System.out.println(ethContractInfoDAO.getEthContractInfoForGame("1001", "1001_50001"));
//			System.out.println(configResourceDAO.getRedisConfigByNameAndProduct("TokenMint","REDIS_CONFIG"));
//			System.out.println(operatorInfoDAO.getOperatorInfo("1001", "1001_50001_001"));
//			System.out.println(gameItemsOnChainInfoDAO.getGameOnChainInfoById("1001", "1001_10001"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
