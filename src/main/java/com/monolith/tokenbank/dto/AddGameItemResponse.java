package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.beans.ItemInfoBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGameItemResponse extends StandardResponseDto {
    
    public AddGameItemResponse(String statusCode, String message) {
        super(statusCode, message);
    }
    
    private ItemInfoBean addedItem;
} 