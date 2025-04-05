package com.shopping.mallmate.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStoresResponse {

    private List<StoreModal> stores;

    private String message;
    private int status;
}
