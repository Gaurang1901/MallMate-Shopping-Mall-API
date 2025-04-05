package com.shopping.mallmate.dto.category;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateUpdateRequest {

    private String id;

    @NotNull(message = "Name is required")
    private String name;

}
