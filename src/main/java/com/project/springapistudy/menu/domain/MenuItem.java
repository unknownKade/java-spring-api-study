package com.project.springapistudy.menu.domain;

import com.project.springapistudy.common.JpaAuditing;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="menuitem")
public class MenuItem extends JpaAuditing {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    int price;

    @Enumerated(EnumType.STRING)
    MenuType type;

    boolean useYn;

    public MenuItem(MenuCreateRequest menuCreateRequest) {
        name = menuCreateRequest.getName();
        price = menuCreateRequest.getPrice();
        type = menuCreateRequest.getType();
        useYn = true;
    }

    public void update(MenuUpdateRequest menuUpdateRequest){
        id = menuUpdateRequest.getId();
        name = menuUpdateRequest.getName();
        price = menuUpdateRequest.getPrice();
        type = menuUpdateRequest.getType();
    }

    public void delete(){
        useYn = false;
    }
}
