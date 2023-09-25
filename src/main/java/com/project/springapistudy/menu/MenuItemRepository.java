package com.project.springapistudy.menu;

import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.domain.MenuItemDTO;
import com.project.springapistudy.menu.domain.MenuType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, String> {

    Optional<MenuItem> getMenuItemByIdAndUseYnIsTrue(String id);
    @Query("select new com.project.springapistudy.menu.domain.MenuItemDTO(m.id, m.name, m.price, m.type) from MenuItem m where m.useYn = true and (:type is null or m.type = :type)")
    Page<MenuItemDTO> getMenuItemsByType(MenuType type, Pageable pageable);

}
