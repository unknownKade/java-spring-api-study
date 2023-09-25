package com.project.springapistudy.menu;

import com.project.springapistudy.CustomPageImpl;
import com.project.springapistudy.menu.domain.MenuItemDTO;
import com.project.springapistudy.menu.domain.MenuType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageSource messageSource;
    private MenuItemDTO testItem;
    private final String baseUrl = "http://localhost:8080/menu";


    @DisplayName("메뉴 페이지 조회 테스트")
    @Test
    public void getMenuPageTest(){
        String url = baseUrl + "/COFFEE/0/3";

        ResponseEntity<CustomPageImpl<MenuItemDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<MenuItemDTO>>() {});

        MenuItemDTO testItem = (MenuItemDTO) responseEntity.getBody().getContent().get(0);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testItem.getType()).isEqualTo(testItem.getType());
    }

    @DisplayName("메뉴 하나 조회 테스트")
    @Test
    public void getMenuItemTest(){
        String url = baseUrl + "/" + testItem.getId();

        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.getForEntity(url, MenuItemDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(testItem.getName());
    }

    @BeforeEach
    @DisplayName("메뉴 추가 테스트")
    @Test
    public void addMenuItemTest(){
        String name = "아메리카노";
        int price = 4500;
        MenuItemDTO newMenu = MenuItemDTO.builder()
                .name(name)
                .price(price)
                .type(MenuType.COFFEE)
                .build();

        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.postForEntity(baseUrl, newMenu, MenuItemDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
        testItem = responseEntity.getBody();
    }

    @DisplayName("메뉴 수정 테스트")
    @Test
    public void modifyMenuItemTest(){
        String name = "라떼";

        testItem.setName("라떼");
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.PUT, new HttpEntity<>(testItem), MenuItemDTO.class );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
    }

    @DisplayName("메뉴 하나 조회 테스트")
    @Test
    public void deleteMenuItemTest(){
        String url = baseUrl + "/" + testItem.getId();
        restTemplate.delete(url);
    }

    @DisplayName("메뉴조회 메뉴없음 오류 테스트")
    @Test
    public void getMenuItemFailTest(){
        String url = baseUrl + "/" + "aa";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(messageSource.getMessage("read.fail", null, Locale.getDefault()));
    }

    @DisplayName("메뉴 이름 누락 오류 테스트")
    @Test
    public void addMenuItemNoItem(){
        testItem.setName(null);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, testItem, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @DisplayName("메뉴 긴 이름 오류 테스트")
    @Test
    public void addMenuItemLongName(){
        testItem.setName("아메리카노".repeat(5));

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, testItem, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @DisplayName("메뉴수정 음수 가격 오류 테스트")
    @Test
    public void modifyMenuItemNegativePrice(){
        testItem.setPrice(-1);

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.PUT, new HttpEntity<>(testItem), String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }
}
