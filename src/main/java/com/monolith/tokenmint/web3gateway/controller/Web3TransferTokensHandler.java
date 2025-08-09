package com.monolith.tokenmint.web3gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.GameItemsOnChainInfoDAO;
import com.monolith.shared.dao.OperatorInfoDAO;
import com.monolith.tokenmint.entities.ETHContractInfo;
import com.monolith.tokenmint.entities.GameItemsOnChainInfoBean;
import com.monolith.tokenmint.entities.ItemInfoBean;
import com.monolith.tokenmint.beans.OnChainTxnInfo;
import com.monolith.tokenmint.entities.OperatorInfoBean;
import com.monolith.tokenmint.create.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.create.dto.Web3ContractArguments;
import com.monolith.tokenmint.create.dto.Web3GtwTransferTokenRequest;

@Service
public class Web3TransferTokensHandler {
	
	@Autowired
	OperatorInfoDAO operatorInfoDAO;
	
	@Autowired
	GameItemsOnChainInfoDAO gameItemsOnChainInfoDAO;

	public Web3GtwTransferTokenRequest makeTransferTokenRequest(CreateTokenTransactionBean cttbean) {
		Web3GtwTransferTokenRequest  web3ttr = new Web3GtwTransferTokenRequest();
		
		
		ETHContractInfo ethcontinfo = cttbean.getEthContractInfo();
		web3ttr.setContractABI(ethcontinfo.getContractAbi());
		web3ttr.setContractAddress(ethcontinfo.getContractAddress());
		web3ttr.setProviderId(ethcontinfo.getProviderId());
		
		ItemInfoBean iibean = cttbean.getItemInfoBean();
		Web3ContractArguments web3Args = new Web3ContractArguments();
		GameItemsOnChainInfoBean gameChainBean = gameItemsOnChainInfoDAO.getGameOnChainInfoById(cttbean.getGameId(),iibean.getItemId());
		web3Args.setFrom(gameChainBean.getTokenIssuerAddress());
		web3Args.setTo(cttbean.getRecieptientAddress());
		web3Args.setId(gameChainBean.getItemContractId());
		web3Args.setValue(iibean.getItemQuantity());
		String hexTxnId = cttbean.getTokenMintTransactionIdHex(); 
		web3Args.setData(hexTxnId);
		web3ttr.setWeb3ContractArguments(web3Args);
		web3ttr.setContractChainId(ethcontinfo.getChainId());
		web3ttr.setContractId(ethcontinfo.getEthContractId());
		web3ttr.setContractName(ethcontinfo.getContractName());
		
		OperatorInfoBean operatorInfoBean = operatorInfoDAO.getOperatorInfo(cttbean.getGameId(), ethcontinfo.getOperatorId());
		
		web3ttr.setContractOperatorAddress(operatorInfoBean.getOperatorAddress());
		web3ttr.setTokenMintTransactionId(cttbean.getTokenMintTransactionId());
		
		OnChainTxnInfo octi = new OnChainTxnInfo();
		octi.setContractAddress(web3ttr.getContractAddress());
		octi.setContractChainId(web3ttr.getContractChainId());
		octi.setContractOperatorAddress(web3ttr.getContractOperatorAddress());
		octi.setTokenMintTransactionId(web3ttr.getTokenMintTransactionId());
		octi.setWeb3ContractArguments(web3Args);
		cttbean.setOnChainTxnInfo(octi);
		return web3ttr;
	}
	
	
	

}
