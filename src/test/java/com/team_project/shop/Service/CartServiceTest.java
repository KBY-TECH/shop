package com.team_project.shop.Service;

import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.cart.CartsRepository;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import com.team_project.shop.network.request.CartsSaveRequestDto;
import com.team_project.shop.network.request.CartsUpdateRequestDto;
import com.team_project.shop.network.response.CartsResponseDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTest {
    @Autowired CartService cartService;
    @Autowired CartsRepository cartsRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired UsersRepository usersRepository;
    @Autowired ProductsRepository productsRepository;
    @Autowired Product_OptionsRepository productOptionsRepository;

    @After
    public void cleanup(){
        cartsRepository.deleteAll();
        productOptionsRepository.deleteAll();
        productsRepository.deleteAll();
        categoryRepository.deleteAll();
        usersRepository.deleteAll();
    }
    Users seller = Users.builder()
            .name("seller")
            .email("sellerEmail")
            .picture("sellerPic")
            .role(Role.USER)
            .build();
    Users user  = Users.builder()
            .name("testUser")
            .email("testEmail")
            .picture("testPic")
            .role(Role.USER)
            .build();

    Category category = Category.builder()
            .name("testCategory")
            .build();

    Products product = Products.builder()
            .name("testProduct")
            .category(category)
            .user(seller)
            .build();
    Product_Options productOption1 = Product_Options.builder()
            .optionName("color")
            .product(product)
            .price(100l)
            .stock(10l)
            .build();

    Product_Options productOption2 = Product_Options.builder()
            .optionName("size")
            .product(product)
            .price(100l)
            .stock(10l)
            .build();
    public void saveDefaultEntity() {
        usersRepository.save(user);
        usersRepository.save(seller);
        categoryRepository.save(category);
        productsRepository.save(product);
        productOptionsRepository.save(productOption1);
        productOptionsRepository.save(productOption2);
    }

    @Test
    public void save테스트() {
        saveDefaultEntity();
        CartsSaveRequestDto dto = CartsSaveRequestDto.builder().productOptionId(productOption1.getId()).quantity(1l).build();

        Long result = cartService.save(user,dto);

        assertThat(result).isNotEqualTo(0);
        assertThat(cartsRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void update테스트() {
        saveDefaultEntity();
        CartsSaveRequestDto saveDto = CartsSaveRequestDto.builder().productOptionId(productOption1.getId()).quantity(1l).build();
        Long cartId = cartService.save(user, saveDto);
        CartsUpdateRequestDto updateDto = CartsUpdateRequestDto.builder().quantity(2l).build();

        Long result = cartService.update(cartId, updateDto);

        assertThat(result).isNotEqualTo(0);
        assertThat(cartsRepository.findAll().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    public void delete테스트() {
        saveDefaultEntity();
        CartsSaveRequestDto saveDto = CartsSaveRequestDto.builder().productOptionId(productOption1.getId()).quantity(1l).build();
        Long cartId = cartService.save(user, saveDto);

        Long result = cartService.delete(cartId);

        assertThat(result).isNotEqualTo(0);
        assertThat(cartsRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void findById테스트() {
        saveDefaultEntity();
        CartsSaveRequestDto saveDto = CartsSaveRequestDto.builder().productOptionId(productOption1.getId()).quantity(1l).build();
        Long cartId = cartService.save(user, saveDto);

        CartsResponseDto dto = cartService.findById(cartId);

        assertThat(dto.getId()).isEqualTo(cartId);
        assertThat(dto.getUser().getId()).isEqualTo(user.getId());
        assertThat(dto.getQuantity()).isEqualTo(1);

    }
}