package com.shopping.mallmate.dto.user.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressByUserId {

    private List<AddressModal> addressModal;

    private String message;
    private int status;

}
