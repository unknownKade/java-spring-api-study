package com.project.springapistudy.menu.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItemDTO {
    String id;
    @NotEmpty(message = "{menu.name.empty}")
    @Size(max= 20, message = "{menu.name.long}")
    String name;
    @Positive(message = "{menu.price.negative}")
    @Max(value = Integer.MAX_VALUE -1, message = "{menu.price.max}")
    int price;
    MenuType type;

    @Builder
    public MenuItemDTO(String id, String name, int price, MenuType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public MenuItem toEntity(){
        return MenuItem.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .type(this.type)
                .useYn(true)
                .build();
    }
}
