package com.project.springapistudy.menu;

import com.project.springapistudy.common.DBException;
import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.domain.MenuItemDTO;
import com.project.springapistudy.menu.domain.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuItemRepository menuItemRepository;
    public Page<MenuItemDTO> getMenuList(MenuType menuType, PageRequest pageRequest){
        return menuItemRepository.getMenuItemsByType(menuType, pageRequest);
    }
    public MenuItemDTO getMenuItem(String id) throws RuntimeException {
        return menuItemRepository.getMenuItemByIdAndUseYnIsTrue(id)
                .orElseThrow(()-> new DBException.DataNotFound(id))
                .toDTO();
    }
    public MenuItemDTO addMenuItem(MenuItemDTO menuItemDTO){
        if(menuItemDTO.getId() != null) throw new DataIntegrityViolationException(menuItemDTO.getId());
        return menuItemRepository.save(menuItemDTO.toEntity()).toDTO();
    }

    public MenuItemDTO modifyMenuItem(MenuItemDTO menuItemDTO){
        if(menuItemRepository.getMenuItemByIdAndUseYnIsTrue(menuItemDTO.getId()).isEmpty()) throw new DBException.DataNotFound(menuItemDTO.getId());
        return menuItemRepository.save(menuItemDTO.toEntity()).toDTO();
    }

    public void deleteMenuItem(String id){
        MenuItem menuItem = menuItemRepository.getMenuItemByIdAndUseYnIsTrue(id)
                .orElseThrow(()-> new DBException.DataNotFound(id));

        menuItem.delete();

        menuItemRepository.save(menuItem);
    }

}
