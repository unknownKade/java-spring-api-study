package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.domain.MenuType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuReadResponse {

    String id;

    String name;

    int price;

    MenuType type;

    public static MenuReadResponse fromEntity(MenuItem menuItem){
        return new MenuReadResponse(menuItem.getId(), menuItem.getName(), menuItem.getPrice(), menuItem.getType());
    }
}
