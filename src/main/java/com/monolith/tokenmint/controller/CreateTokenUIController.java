package com.monolith.tokenmint.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.dto.LoadUIDTORequest;
import com.monolith.tokenmint.dto.LoadUIDTOResponse;
import com.monolith.tokenmint.dto.VerifyAddressRequest;
import com.monolith.tokenmint.dto.VerifyAddressResponse;
import com.monolith.tokenmint.service.LoadUIServiceHandler;
import com.monolith.tokenmint.service.VerifyAddressHandler;


@RestController
@RequestMapping("/tokenmint/server")
public class CreateTokenUIController {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateTokenUIController.class);

	@Autowired
	LoadUIServiceHandler loadUIServiceHandler;
	
	@Autowired
	VerifyAddressHandler verifyAddressHandler;
	
	
	@CrossOrigin
	@PostMapping(path = "/load-ui/{tokenmint_trans_id}")
	public ResponseEntity<LoadUIDTOResponse> provideUiLoadData(@PathVariable String tokenmint_trans_id,@RequestBody LoadUIDTORequest loadUIDTORequest){
		
		logger.info("**************************************** Load TokenMint UI Request **************************************");
		logger.info("====> LoadUIRequest {}",Utility.getJsonFromObject(loadUIDTORequest));
		CreateTokenTransactionBean createTokenTransactionBean  = new CreateTokenTransactionBean();
		createTokenTransactionBean.setTokenMintTransactionId(tokenmint_trans_id);
		
		LoadUIDTOResponse loadUIDTOResponse = loadUIServiceHandler.getLoadUIResponse(loadUIDTORequest,createTokenTransactionBean);
		logger.info("====> LoadUIResponse {}",Utility.getJsonFromObject(loadUIDTOResponse));
		logger.info("**************************************** Load TokenMint UI Response *************************************");
		return new ResponseEntity<>(loadUIDTOResponse,HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(path = "/verify-address/{tokenmint_trans_id}")
	public ResponseEntity<VerifyAddressResponse> verifyAddress(@PathVariable String tokenmint_trans_id,@RequestBody VerifyAddressRequest verifyAddressRequest){
		
		logger.info("**************************************** Verify Address Request **************************************");
		logger.info("====> VerifyAddressRequest {}",Utility.getJsonFromObject(verifyAddressRequest));
		CreateTokenTransactionBean createTokenTransactionBean  = new CreateTokenTransactionBean();
		createTokenTransactionBean.setTokenMintTransactionId(tokenmint_trans_id);
		
		VerifyAddressResponse verifyAddressResponse = verifyAddressHandler.verifyAddress(verifyAddressRequest,createTokenTransactionBean);
		logger.info("====> VerifyAddressResponse {}",Utility.getJsonFromObject(verifyAddressResponse));
		logger.info("**************************************** Verify Address Response **************************************");
		return new ResponseEntity<>(verifyAddressResponse,HttpStatus.OK);
	}
}
