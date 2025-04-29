package com.shopping.mallmate.dto.user;

import com.shopping.mallmate.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModal {

    private String id;

    private String name;

    private String email;

    private String role;

}