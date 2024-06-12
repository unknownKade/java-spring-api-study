package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuReadRequest {

    MenuType type;

    @Positive(message = "{pageable.page.negative}")
    @NotEmpty(message="{pageable.page.empty}")
    int page;

    @Positive(message = "{pageable.size.negative}")
    int size;
}
