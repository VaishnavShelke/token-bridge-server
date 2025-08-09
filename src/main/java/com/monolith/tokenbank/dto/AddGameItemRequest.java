package com.monolith.tokenbank.dto;

import lombok.Data;
import com.monolith.tokenmint.beans.ItemStatus;

@Data
public class AddGameItemRequest {
    private String username;
    private String gameId;
    // itemId removed - will be auto-generated
    private Integer contractItemId;
    private String itemCategory;
    private String itemTitle;
    private String itemDescription;
    private String itemImgUrl;
    private ItemStatus itemStatus; // Optional, defaults to ALIVE if not provided
} 