package com.monolith.tokenbank.service;

import com.monolith.shared.dao.ItemInfoDAO;
import com.monolith.tokenbank.dto.AddGameItemRequest;
import com.monolith.tokenbank.dto.AddGameItemResponse;
import com.monolith.tokenbank.dto.AllGameItemsResponse;
import com.monolith.tokenbank.dto.UnAliveItemRequest;
import com.monolith.tokenbank.dto.UnAliveItemResponse;
import com.monolith.tokenbank.helper.TokenBankConstants;
import com.monolith.tokenbank.helper.TokenBankPojoHelper;
import com.monolith.tokenbank.helper.TokenBankUserCredsHelper;
import com.monolith.tokenbank.helper.ValidationUtils;
import com.monolith.tokenmint.entities.GameItemsEntity;
import com.monolith.tokenmint.beans.ItemStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;
import com.monolith.tokenbank.helper.TokenBankInGameAssetsServiceHelper;

@Service
public class TokenBankInGameAssetsService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankInGameAssetsService.class);

    @Autowired
    private ItemInfoDAO itemInfoDAO;

    @Autowired
    private TokenBankUserCredsHelper tokenBankUserCredsHelper;

    public AllGameItemsResponse getAllItemsForGame(String gameId) {
        logger.info("{} :: Received request to get all items for game: {}", TOKEN_BANK_PREPEND, gameId);
        ValidationUtils.validateGetAllItemsForGameInput(gameId);
        List<GameItemsEntity> items = itemInfoDAO.getAllItemsForGame(gameId);
        logger.info("{}Successfully retrieved {} items for game: {}", TOKEN_BANK_PREPEND, items.size(), gameId);
        AllGameItemsResponse response = new AllGameItemsResponse(TokenBankConstants.STATUS_CODE_SUCCESS, "Fetched All Items Successfully");
        response.setItems(items);
        return response;
    }

    public AddGameItemResponse addGameItem(AddGameItemRequest request) {
        logger.info("{} :: Received request to add item for game: {} by user: {}", TOKEN_BANK_PREPEND, request.getGameId(), request.getUsername());
        
        // Validate the request (itemId validation removed since it's auto-generated)
        ValidationUtils.validateAddGameItemRequest(request);
        
        // Keep this validation - ensure user is admin for the game
        tokenBankUserCredsHelper.validateUserIsAdminForGame(request.getUsername(), request.getGameId());
        
        // Generate a unique item ID using helper method
        String generatedItemId = TokenBankInGameAssetsServiceHelper.generateUniqueItemId(request.getGameId(), itemInfoDAO);

        // Get the next contract_item_id for this game
        Integer maxContractItemId = itemInfoDAO.getMaxContractItemIdForGame(request.getGameId());
        Integer nextContractItemId = maxContractItemId + 1;
        
        // Create the item with the generated ID and auto-incremented contract_item_id
        GameItemsEntity itemInfo = new GameItemsEntity();
        itemInfo.setItemStatus(ItemStatus.ALIVE);
        TokenBankPojoHelper.getItemInfoBeanFromAddGameItemRequest(itemInfo, request, generatedItemId);
        itemInfo.setContractItemId(nextContractItemId); // Override with auto-incremented value

        
        // Save the item
        itemInfoDAO.saveGameItem(itemInfo);
        
        logger.info("{} :: Successfully added item {} for game: {} with contract_item_id: {}", TOKEN_BANK_PREPEND, generatedItemId, request.getGameId(), nextContractItemId);
        
        AddGameItemResponse response = new AddGameItemResponse(TokenBankConstants.STATUS_CODE_SUCCESS, "Game item added successfully");
        response.setAddedItem(itemInfo);
        return response;
    }

    public UnAliveItemResponse unAliveItem(UnAliveItemRequest request) {
        logger.info("{} :: Received request to unAlive item {} for game: {} by user: {}", TOKEN_BANK_PREPEND, request.getItemId(), request.getGameId(), request.getUsername());
        
        // Validate the request
        ValidationUtils.validateUnAliveItemRequest(request);
        
        // Ensure user is admin for the game
        tokenBankUserCredsHelper.validateUserIsAdminForGame(request.getUsername(), request.getGameId());
        
        // Check if the item exists
        GameItemsEntity existingItem = itemInfoDAO.getItemInfoFromItemIdForGame(request.getGameId(), request.getItemId());
        if (existingItem == null) {
            logger.error("{} :: Item {} not found for game: {}", TOKEN_BANK_PREPEND, request.getItemId(), request.getGameId());
            throw new RuntimeException("Item not found");
        }
        
        // Check if the item is already DEAD
        if (existingItem.getItemStatus() == ItemStatus.DEAD) {
            logger.warn("{} :: Item {} is already DEAD for game: {}", TOKEN_BANK_PREPEND, request.getItemId(), request.getGameId());
            UnAliveItemResponse response = new UnAliveItemResponse(TokenBankConstants.STATUS_CODE_SUCCESS, "Item is already DEAD");
            response.setUpdatedItem(existingItem);
            return response;
        }
        
        // Update the item status to DEAD
        boolean updateSuccess = itemInfoDAO.updateItemStatusToDead(request.getGameId(), request.getItemId());
        
        if (!updateSuccess) {
            logger.error("{} :: Failed to update item {} status to DEAD for game: {}", TOKEN_BANK_PREPEND, request.getItemId(), request.getGameId());
            throw new RuntimeException("Failed to update item status");
        }
        
        // Get the updated item
        GameItemsEntity updatedItem = itemInfoDAO.getItemInfoFromItemIdForGame(request.getGameId(), request.getItemId());
        
        logger.info("{} :: Successfully updated item {} status to DEAD for game: {}", TOKEN_BANK_PREPEND, request.getItemId(), request.getGameId());
        
        UnAliveItemResponse response = new UnAliveItemResponse(TokenBankConstants.STATUS_CODE_SUCCESS, "Item status updated to DEAD successfully");
        response.setUpdatedItem(updatedItem);
        return response;
    }

} 