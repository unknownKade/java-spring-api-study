package com.project.springapistudy.menu;

import com.project.springapistudy.menu.domain.MenuItemDTO;
import com.project.springapistudy.menu.domain.MenuType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MenuItemExceptionTest {

    @Autowired
    MenuItemRepository menuItemRepository;


    @DisplayName("메뉴이름 길이 오류 테스트")
    @Test
    public void addMenuFailTest(){
        String name = "a".repeat(30);
        int price = 4000;

        MenuItemDTO newMenu = MenuItemDTO.builder()
                .name(name)
                .price(price)
                .type(MenuType.COFFEE)
                .build();

        assertThrows(DataIntegrityViolationException.class,()-> menuItemRepository.save(newMenu.toEntity()));
    }
}
