package com.project.springapistudy.menu;

import com.project.springapistudy.menu.domain.MenuItem;
import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.dto.MenuReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, String> {

    Optional<MenuItem> getMenuItemByIdAndUseYn(String id, boolean useYn);

    @Query("select new com.project.springapistudy.menu.dto.MenuReadResponse(m.id, m.name, m.price, m.type) from MenuItem m where m.useYn != :useYn and (:type is null or m.type = :type)")
    Page<MenuReadResponse> getMenuItemsByType(MenuType type, boolean useYn, Pageable pageable);

}
