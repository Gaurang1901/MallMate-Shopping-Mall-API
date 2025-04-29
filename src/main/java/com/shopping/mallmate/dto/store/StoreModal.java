package com.shopping.mallmate.dto.store;

import com.shopping.mallmate.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreModal {

    private String id;

    private String name;

    private String description;

    private String userId;

    private String categoryId;

    private String phoneNumber;

    private String InstagramLink;

    private String FacebookLink;

    private String TwitterLink;

    private List<String> images;

    private List<ProductResponseForStores> products;


    public static StoreModal fromEntity(Store store) {
        return new StoreModal(
                store.getId(),
                store.getName(),
                store.getDescription(),
                store.getUser().getId(),
                store.getCategory().getId(),
                store.getPhoneNumber(),
                store.getInstagramLink(),
                store.getFacebookLink(),
                store.getTwitterLink(),
                store.getImage(),
                store.getProducts().stream().map(ProductResponseForStores::fromEntity).toList()
        );
    }

}
