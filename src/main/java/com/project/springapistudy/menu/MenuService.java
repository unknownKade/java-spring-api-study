package com.project.springapistudy.menu;

import com.project.springapistudy.common.DBException;
import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuReadRequest;
import com.project.springapistudy.menu.dto.MenuReadResponse;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    @Transactional(readOnly = true)
    public Page<MenuReadResponse> getMenuList(MenuReadRequest menuListReadRequest, boolean isAdmin){
        return menuItemRepository.getMenuItemsByType(menuListReadRequest.getType(), isAdmin, PageRequest.of(menuListReadRequest.getPage(), menuListReadRequest.getSize()));
    }

    @Transactional(readOnly = true)
    public MenuReadResponse getMenuItem(String id, boolean isAdmin) throws RuntimeException {
        MenuItem menuItem = menuItemRepository.getMenuItemByIdAndUseYn(id, !isAdmin)
                .orElseThrow(()-> new DBException.DataNotFound(id));
        return MenuReadResponse.fromEntity(menuItem);
    }

    public String addMenuItem(MenuCreateRequest menuCreateRequest){
        return menuItemRepository.save(new MenuItem(menuCreateRequest)).getId();
    }

    public void modifyMenuItem(MenuUpdateRequest menuUpdateRequest){
        MenuItem menuItem = menuItemRepository.getMenuItemByIdAndUseYn(menuUpdateRequest.getId(), true)
                .orElseThrow(() -> new DBException.DataNotFound(menuUpdateRequest.getId()));

        menuItem.update(menuUpdateRequest);
    }

    public void deleteMenuItem(String id){
        MenuItem menuItem = menuItemRepository.getMenuItemByIdAndUseYn(id, true)
                .orElseThrow(()-> new DBException.DataNotFound(id));

        menuItem.delete();
    }
}
