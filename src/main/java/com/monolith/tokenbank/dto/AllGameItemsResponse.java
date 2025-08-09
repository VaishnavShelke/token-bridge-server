package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.beans.ItemInfoBean;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllGameItemsResponse extends StandardResponseDto {
    public AllGameItemsResponse(String statusCode, String message) {
        super(statusCode, message);
    }

    private List<ItemInfoBean> items;
} 