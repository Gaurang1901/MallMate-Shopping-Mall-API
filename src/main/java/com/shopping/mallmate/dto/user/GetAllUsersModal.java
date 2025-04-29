package com.shopping.mallmate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUsersModal {

    private List<UserModal> users;

    private String message;

    private int status;
}
