package com.project.springapistudy.menu;

import com.project.springapistudy.menu.domain.MenuItemDTO;
import com.project.springapistudy.menu.domain.MenuType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/menu")
@RestController
public class MenuController {
    private final MenuService menuService;
    @GetMapping("/{type}/{page}/{size}")
    public Page<MenuItemDTO> getMenuList(@PathVariable("type") MenuType type, @PathVariable("page") int page, @PathVariable(value = "size")int size){
        return menuService.getMenuList(type, PageRequest.of(page, size));
    }
    @GetMapping("/{id}")
    public MenuItemDTO getMenuItem(@PathVariable("id") String id){
        return menuService.getMenuItem(id);
    }
    @PostMapping
    public MenuItemDTO addMenuItem(@RequestBody @Valid MenuItemDTO menuItemDTO){
        return menuService.addMenuItem(menuItemDTO);
    }
    @PutMapping
    public MenuItemDTO modifyMenuItem(@RequestBody @Valid MenuItemDTO menuItemDTO){
        return menuService.modifyMenuItem(menuItemDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable("id")String id){
        menuService.deleteMenuItem(id);
    }

}
