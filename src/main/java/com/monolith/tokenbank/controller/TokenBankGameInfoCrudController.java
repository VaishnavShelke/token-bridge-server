package com.monolith.tokenbank.controller;

import com.monolith.tokenbank.dto.AllGamesResponse;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenbank.dto.OnboardGameResponse;
import com.monolith.tokenbank.service.TokenBankGameInfoCrudService;
import com.monolith.tokenbank.service.TokenBankUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.monolith.tokenbank.helper.TokenBankConstants.STATUS_CODE_INTERNAL_ERROR;
import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@RestController
@RequestMapping("/tokenbank/game")
public class TokenBankGameInfoCrudController {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankGameInfoCrudController.class);


    @Autowired
    TokenBankGameInfoCrudService tokenBankGameInfoCrudService;


    @PostMapping(path = "")
    public ResponseEntity<OnboardGameResponse> onboardGame(@RequestBody OnBoardGameRequest onBoardGameRequest){
        logger.info("{} :: Received onboard game request : {}",TOKEN_BANK_PREPEND, onBoardGameRequest.getUsername());
        try {
            OnboardGameResponse response = tokenBankGameInfoCrudService.onboardGame(onBoardGameRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error onboarding game: {}",TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            OnboardGameResponse response = new OnboardGameResponse(STATUS_CODE_INTERNAL_ERROR,"Error onboarding game"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(path = "")
    public ResponseEntity<OnboardGameResponse> editGameConfig(@RequestBody OnBoardGameRequest onBoardGameRequest){
        logger.info("{} :: Received edit game request : {}",TOKEN_BANK_PREPEND, onBoardGameRequest.getUsername());
        try {
            OnboardGameResponse response = tokenBankGameInfoCrudService.editGame(onBoardGameRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error in edit game: {}",TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            OnboardGameResponse response = new OnboardGameResponse(STATUS_CODE_INTERNAL_ERROR,"Error in editing game"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping(path = "")
    public ResponseEntity<OnboardGameResponse> deleteGame(@RequestBody OnBoardGameRequest onBoardGameRequest){
        logger.info("{} :: Received delete game request : {}",TOKEN_BANK_PREPEND, onBoardGameRequest.getUsername());
        try {
            OnboardGameResponse response = tokenBankGameInfoCrudService.deleteGame(onBoardGameRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error deleting game: {}",TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            OnboardGameResponse response = new OnboardGameResponse(STATUS_CODE_INTERNAL_ERROR,"Error deleting game"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AllGamesResponse> getAllGames() {
        logger.info("{} :: Received request to get all games", TOKEN_BANK_PREPEND);
        try {
            AllGamesResponse response = tokenBankGameInfoCrudService.getAllGames();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error getting all games: {}", TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            AllGamesResponse response = new AllGamesResponse(STATUS_CODE_INTERNAL_ERROR, "Error getting all games: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
