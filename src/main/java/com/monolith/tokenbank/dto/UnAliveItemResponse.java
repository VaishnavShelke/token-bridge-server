package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.entities.ItemInfoBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnAliveItemResponse extends StandardResponseDto {
    
    public UnAliveItemResponse(String statusCode, String message) {
        super(statusCode, message);
    }
    
    private ItemInfoBean updatedItem;
} 