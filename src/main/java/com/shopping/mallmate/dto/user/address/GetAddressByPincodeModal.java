package com.shopping.mallmate.dto.user.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressByPincodeModal {

    private AddressModal addressModal;
    private String message;
    private int status;
}
