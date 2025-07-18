package com.shopping.mallmate.dto.auth;

import com.shopping.mallmate.entity.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignInRequest {

    private String email;

    private String password;

    private USER_ROLE role;

}
