package com.shopping.mallmate.dto.auth;

import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.entity.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private int status;

    private String token;

    private User user;

    private USER_ROLE role;

    private String msg;
}
