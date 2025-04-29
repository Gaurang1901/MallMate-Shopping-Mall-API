package com.shopping.mallmate.dto.user.address;

import com.shopping.mallmate.dto.store.StoreModal;
import com.shopping.mallmate.entity.Address;
import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModal {

    private String id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String userId;

    public static AddressModal fromEntity(Address address) {
        return new AddressModal(
                address.getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getPincode(),
                address.getUser().getId()
        );
    }

}
