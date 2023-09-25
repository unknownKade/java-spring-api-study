package com.project.springapistudy.menu;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.domain.MenuItemDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class MenuItemTest {

    @Autowired
    MenuItemRepository menuItemRepository;

    MenuItem testItem;

    @DisplayName("메뉴 페이지 조회 테스트")
    @Test
    public void getMenuPageTest(){
        Page<MenuItemDTO> menuItemDTOs = menuItemRepository.getMenuItemsByType(MenuType.COFFEE,PageRequest.of(0, 3));
        menuItemDTOs.forEach(menuItemDTO -> System.out.println(menuItemDTO.getName()));
        assertThat(menuItemDTOs.getContent().get(0)).isNotNull();
    }

    @DisplayName("메뉴 등록 테스트")
    @BeforeEach
    @Test
    public void addMenuTest(){
        String name = "아메리카노";
        int price = 4500;
        MenuItemDTO newMenu = MenuItemDTO.builder()
                .name(name)
                .price(price)
                .type(MenuType.COFFEE)
                .build();

        testItem = menuItemRepository.save(newMenu.toEntity());

        assertThat(testItem.getName()).isEqualTo(name);
    }

    @DisplayName("메뉴 수정 테스트")
    @Test
    public void modifyMenuTest(){
        int newPrice = 4000;
        MenuItemDTO fixMenu = testItem.toDTO();
        fixMenu.setPrice(newPrice);

        MenuItem entity = menuItemRepository.save(fixMenu.toEntity());

        assertThat(entity.getPrice()).isEqualTo(newPrice);
    }

    @DisplayName("메뉴 삭제 테스트")
    @Test
    public void deleteMenuTest(){
        testItem.delete();

        MenuItem entity = menuItemRepository.save(testItem);

        assertThat(entity.isUseYn()).isFalse();
    }
}
