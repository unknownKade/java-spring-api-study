package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuCreateRequest {

    @NotEmpty(message = "{menu.name.empty}")
    @Size(max= 20, message = "{menu.name.long}")
    String name;

    @Positive(message = "{menu.price.negative}")
    @Max(value = Integer.MAX_VALUE -1, message = "{menu.price.max}")
    int price;

    MenuType type;
}
