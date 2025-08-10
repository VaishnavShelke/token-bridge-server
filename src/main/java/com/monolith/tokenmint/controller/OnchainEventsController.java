package com.monolith.tokenmint.controller;

import com.monolith.tokenmint.dto.TransferEventPacketIn;
import com.monolith.tokenmint.service.TransferEventPacketInHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokenmint/server/internal/eventlistener")
public class OnchainEventsController {
	
	private static final Logger logger = LoggerFactory.getLogger(OnchainEventsController.class);
	
	
	@Autowired
	TransferEventPacketInHandler transferEventPacketInHandler;
	
	@CrossOrigin
	@PostMapping(path = "/transferevent")
	public ResponseEntity<Object> provideUiLoadData(@RequestBody TransferEventPacketIn transferEventPacketIn){
		
		logger.info("**************************************** TransferEventPacketIn **************************************");
		transferEventPacketInHandler.handlerIncomingPacket(transferEventPacketIn);
		logger.info("**************************************** TransferEvenetPacketOut *************************************");
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}


}
