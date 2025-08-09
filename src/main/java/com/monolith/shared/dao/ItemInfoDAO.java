package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.entities.ItemInfoBean;
import com.monolith.tokenmint.repository.ItemInfoRepository;

@Service
public class ItemInfoDAO {

    private static final Logger logger = LoggerFactory.getLogger(ItemInfoDAO.class);

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    public ItemInfoBean getItemInfoFromItemIdForGame(String gameId, String itemId) {
        try {
            return itemInfoRepository.findByGameIdAndItemId(gameId, itemId).orElse(null);
        } catch (Exception e) {
            logger.error("Error While fetching ItemInfo for game {} and item id {} error :: {}", gameId, itemId, e.getMessage());
            return null;
        }
    }

    public List<ItemInfoBean> getAllItemsForGame(String gameId) {
        try {
            return itemInfoRepository.findByGameId(gameId);
        } catch (Exception e) {
            logger.error("Error while fetching all items for game {} error :: {}", gameId, e.getMessage());
            return null;
        }
    }

    public void saveGameItem(ItemInfoBean itemInfo) {
        try {
            itemInfoRepository.save(itemInfo);
            logger.info("Successfully saved game item: {} for game: {}", itemInfo.getItemId(), itemInfo.getGameId());
        } catch (Exception e) {
            logger.error("Failed to save game item: {} for game: {} :: {}", itemInfo.getItemId(), itemInfo.getGameId(), e.getMessage());
            throw new RuntimeException("Failed to save game item");
        }
    }

    public Integer getMaxContractItemIdForGame(String gameId) {
        try {
            Integer maxContractItemId = itemInfoRepository.findMaxContractItemIdByGameId(gameId);
            return maxContractItemId != null ? maxContractItemId : 0;
        } catch (Exception e) {
            logger.error("Error while fetching max contract_item_id for game {} error :: {}", gameId, e.getMessage());
            return 0; // Default to 0 if no items exist or error occurs
        }
    }

    /**
     * Generates the next incremental 5-digit item ID for a specific game
     * Format: 00001, 00002, 00003, etc.
     * 
     * @param gameId The game ID to generate the item ID for
     * @return A 5-digit zero-padded incremental item ID
     */
    public String getNextIncrementalItemId(String gameId) {
        try {
            // Query to get the highest numeric item_id for the given game
            Integer maxItemId = itemInfoRepository.findMaxNumericItemIdByGameId(gameId);
            
            // If no items exist or maxItemId is null, start from 1
            int nextItemId = (maxItemId != null) ? maxItemId + 1 : 1;
            
            // Ensure the ID doesn't exceed 5 digits (max 99999)
            if (nextItemId > 99999) {
                logger.error("Maximum 5-digit item ID limit reached for game: {}", gameId);
                throw new RuntimeException("Maximum item ID limit (99999) reached for game: " + gameId);
            }
            
            // Format as 5-digit zero-padded string
            String formattedItemId = String.format("%05d", nextItemId);
            
            logger.info("Generated next incremental item ID: {} for game: {}", formattedItemId, gameId);
            return formattedItemId;
            
        } catch (Exception e) {
            logger.error("Error while generating next incremental item ID for game {} error :: {}", gameId, e.getMessage());
            throw new RuntimeException("Failed to generate incremental item ID for game: " + gameId, e);
        }
    }

    /**
     * Updates the status of a specific item to DEAD
     * 
     * @param gameId The game ID
     * @param itemId The item ID to update
     * @return true if the update was successful, false otherwise
     */
    public boolean updateItemStatusToDead(String gameId, String itemId) {
        try {
            int rowsAffected = itemInfoRepository.updateItemStatus(gameId, itemId, com.monolith.tokenmint.beans.ItemStatus.DEAD);
            
            if (rowsAffected > 0) {
                logger.info("Successfully updated item status to DEAD for item: {} in game: {}", itemId, gameId);
                return true;
            } else {
                logger.warn("No rows affected when updating item: {} in game: {} to DEAD status", itemId, gameId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error while updating item status to DEAD for item: {} in game: {} error :: {}", itemId, gameId, e.getMessage());
            throw new RuntimeException("Failed to update item status to DEAD for item: " + itemId + " in game: " + gameId, e);
        }
    }
}
