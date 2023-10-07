package com.project.springapistudy.menu;

import com.project.springapistudy.CustomPageImpl;
import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuReadResponse;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageSource messageSource;

    private final String baseUrl = "http://localhost:8080/menu";

    public String initializeTest(){
        MenuCreateRequest menuCreateRequest = MenuCreateRequest.builder()
                .name("아메리카노")
                .price(4500)
                .type(MenuType.COFFEE)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, menuCreateRequest, String.class);
        return responseEntity.getBody();
    }

    @DisplayName("메뉴 페이지 조회 테스트")
    @Test
    public void getMenuPageTest(){
        MenuType type = MenuType.COFFEE;
        String url = baseUrl + "?type="+ type.name() + "&size=3&page=0";

        ResponseEntity<CustomPageImpl<MenuReadResponse>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,new ParameterizedTypeReference<>(){});

        List<MenuReadResponse> testItems = responseEntity.getBody().getContent();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        testItems.forEach((testItem) -> assertThat(testItem.getType()).isEqualTo(type));
    }

    @DisplayName("메뉴 페이지 조회 테스트")
    @Test
    public void getMenuPageAdminTest(){
        MenuType type = MenuType.COFFEE;
        String url = baseUrl + "/admin?type="+ type.name() + "&size=3&page=0";

        ResponseEntity<CustomPageImpl<MenuReadResponse>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,new ParameterizedTypeReference<CustomPageImpl<MenuReadResponse>>(){});

        List<MenuReadResponse> testItems = responseEntity.getBody().getContent();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        testItems.forEach((testItem) -> assertThat(testItem.getType()).isEqualTo(type));
    }

    @DisplayName("메뉴 하나 조회 테스트")
    @Test
    public void getMenuItemTest(){
        String id = initializeTest();
        String url = baseUrl + "/" + id;

        ResponseEntity<MenuReadResponse> responseEntity = restTemplate.getForEntity(url, MenuReadResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(id);
    }

    @DisplayName("메뉴 수정 테스트")
    @Test
    public void modifyMenuItemTest(){
        String id = initializeTest();
        String name = "라떼";
        int price = 5000;
        MenuType type = MenuType.COFFEE;

        MenuUpdateRequest menuUpdateRequest = MenuUpdateRequest.builder()
                .id(id)
                .name(name)
                .price(price)
                .type(type)
                .build();

        restTemplate.exchange(baseUrl, HttpMethod.PUT, new HttpEntity<>(menuUpdateRequest), MenuReadResponse.class );

        ResponseEntity<MenuReadResponse> responseEntity = restTemplate.getForEntity(baseUrl + "/" + id, MenuReadResponse.class);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(responseEntity.getBody().getId()).isEqualTo(id);
        softAssertions.assertThat(responseEntity.getBody().getName()).isEqualTo(name);
        softAssertions.assertThat(responseEntity.getBody().getPrice()).isEqualTo(price);
        softAssertions.assertThat(responseEntity.getBody().getType()).isEqualTo(type);
    }

    @DisplayName("메뉴 삭제 테스트")
    @Test
    public void deleteMenuItemTest(){
        String id = initializeTest();
        String url = baseUrl + "/" + id;

        restTemplate.delete(url);
        Assertions.assertThrows(RestClientException.class , () ->restTemplate.getForEntity(url, MenuReadResponse.class));
    }

    @DisplayName("메뉴조회 메뉴없음 오류 테스트")
    @Test
    public void getMenuItemFailTest(){
        String url = baseUrl + "/" + "aa";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(messageSource.getMessage("read.fail", null, Locale.getDefault()));
    }

    @DisplayName("메뉴 긴 이름 오류 테스트")
    @Test
    public void addMenuItemLongName(){
        MenuCreateRequest menuCreateRequest = MenuCreateRequest.builder()
                .name("아메리카노".repeat(5))
                .price(4500)
                .type(MenuType.COFFEE)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, menuCreateRequest, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @DisplayName("메뉴 음수 가격 오류 테스트")
    @Test
    public void modifyMenuItemNegativePrice(){
        MenuCreateRequest menuCreateRequest = MenuCreateRequest.builder()
                .name("아메리카노")
                .price(-1)
                .type(MenuType.COFFEE)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, menuCreateRequest, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }
}
