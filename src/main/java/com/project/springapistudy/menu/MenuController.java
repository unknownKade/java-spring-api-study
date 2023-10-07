package com.project.springapistudy.menu;

import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuReadRequest;
import com.project.springapistudy.menu.dto.MenuReadResponse;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/menu")
@RestController
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public Page<MenuReadResponse> getMenuList(@ModelAttribute MenuReadRequest menuListReadRequest){
        return menuService.getMenuList(menuListReadRequest, false);
    }

    @GetMapping("/admin")
    public Page<MenuReadResponse> geMenuListAdmin(@ModelAttribute MenuReadRequest menuListReadRequest){
        return menuService.getMenuList(menuListReadRequest, true);
    }

    @GetMapping("/{id}")
    public MenuReadResponse getMenuItem(@PathVariable String id){
        return menuService.getMenuItem(id, false);
    }

    @PostMapping
    public String addMenuItem(@RequestBody @Valid MenuCreateRequest menuCreateRequest){
        return menuService.addMenuItem(menuCreateRequest);
    }

    @PutMapping
    public void modifyMenuItem(@RequestBody @Valid MenuUpdateRequest menuUpdateRequest){
        menuService.modifyMenuItem(menuUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable String id){
        menuService.deleteMenuItem(id);
    }
}
