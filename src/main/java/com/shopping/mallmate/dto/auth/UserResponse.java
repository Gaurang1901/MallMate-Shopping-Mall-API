package com.shopping.mallmate.dto.auth;

import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.entity.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String email;

    private USER_ROLE role;

    private String name;

    public UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getName()
        );
    }
}
