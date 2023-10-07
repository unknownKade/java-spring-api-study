package com.project.springapistudy.menu;

import com.project.springapistudy.common.DBException;
import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuReadResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MenuRepositoryTest {

    @Autowired
    MenuItemRepository menuItemRepository;

    public MenuItem initializeTest(){
        MenuCreateRequest menuCreateRequest = MenuCreateRequest.builder()
                .name("아메리카노")
                .price(4500)
                .type(MenuType.COFFEE)
                .build();

        return menuItemRepository.save(new MenuItem(menuCreateRequest));
    }

    @DisplayName("메뉴 페이지 조회 테스트")
    @Test
    public void getMenuPageTest(){
        MenuItem testItem = initializeTest();

        MenuItem menuItem = menuItemRepository.getMenuItemByIdAndUseYn(testItem.getId(), false)
                .orElseThrow( () -> new DBException.DataNotFound(testItem.getId()));
        MenuReadResponse menuReadResponse = MenuReadResponse.fromEntity(menuItem);

        assertThat(menuReadResponse).isNotNull();
    }

    @DisplayName("메뉴 등록 테스트")
    @Test
    public void addMenuTest(){
        MenuItem testItem = initializeTest();

        assertThat(testItem.getName()).isEqualTo("아메리카노");
    }

    @DisplayName("메뉴이름 길이 오류 테스트")
    @Test
    public void addMenuFailTest(){
        String name = "a".repeat(30);

        MenuCreateRequest menuCreateRequest = MenuCreateRequest.builder()
                .name(name)
                .price(4500)
                .type(MenuType.COFFEE)
                .build();

        assertThrows(DataIntegrityViolationException.class,()-> menuItemRepository.save(new MenuItem(menuCreateRequest)));
    }
}
