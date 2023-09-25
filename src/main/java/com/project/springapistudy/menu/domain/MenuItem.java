package com.project.springapistudy.menu.domain;

import com.project.springapistudy.JpaAuditing;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name="menuitem")
public class MenuItem extends JpaAuditing {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    int price;
    @Enumerated(EnumType.STRING)
    MenuType type;

    boolean useYn;

    protected MenuItem() {

    }
    @Builder
    protected MenuItem(String id, String name, int price, MenuType type, boolean useYn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.useYn = useYn;
    }


    public MenuItemDTO toDTO(){
        return MenuItemDTO.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .type(this.type)
                .build();
    }

    public void delete(){
        this.useYn = false;
    }
}
