package com.team_project.shop.domain.cart;

import com.team_project.shop.domain.Address.AddressRepository;
import com.team_project.shop.domain.coupon.CouponRepository;
import com.team_project.shop.domain.middleEntity.OrderDetailsRepository;
import com.team_project.shop.domain.order.OrdersRepository;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartsRepositoryTest {

    @Autowired CartsRepository cartsRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired UsersRepository usersRepository;
    @Autowired ProductsRepository productsRepository;
    @Autowired Product_OptionsRepository productOptionsRepository;
    @Autowired OrderDetailsRepository orderDetailsRepository;
    @Autowired OrdersRepository ordersRepository;
    @Autowired CouponRepository couponRepository;
    @Autowired AddressRepository addressRepository;

    //단위 테스트가 끝날 때마다 수행
    //foreign key 고려해서 삭제시키기
    @After
    public void cleanup(){
        orderDetailsRepository.deleteAll();
        ordersRepository.deleteAll();
        cartsRepository.deleteAll();
        productOptionsRepository.deleteAll();
        productsRepository.deleteAll();
        categoryRepository.deleteAll();

        couponRepository.deleteAll();
        addressRepository.deleteAll();
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
    public void 장바구니저장(){
        //given
        saveDefaultEntity();
        cartsRepository.save(Carts.builder().user(user).productOption(productOption1).quantity(2l).build());
        cartsRepository.save(Carts.builder().user(user).productOption(productOption2).quantity(1l).build());


        //when
        List<Carts> cartsList =cartsRepository.findAll();

        //then
        Carts cart1 = cartsList.get(0);
        Carts cart2 = cartsList.get(1);
        assertThat(cart1.getUser().getId()).isEqualTo(user.getId());
        assertThat(cart1.getProductOption().getId()).isEqualTo(productOption1.getId());
        assertThat(cart1.getQuantity()).isEqualTo(2l);
        System.out.println("확인1");

        assertThat(cart2.getUser().getId()).isEqualTo(user.getId());
        assertThat(cart2.getProductOption().getId()).isEqualTo(productOption2.getId());
        assertThat(cart2.getQuantity()).isEqualTo(1l);
        System.out.println("확인2");
    }

    @Test
    public void 사용자장바구니조회(){
        //given
        saveDefaultEntity();
        cartsRepository.save(Carts.builder().user(user).productOption(productOption1).quantity(2l).build());
        cartsRepository.save(Carts.builder().user(user).productOption(productOption2).quantity(1l).build());


        //when
        List<Carts> cartsList1 = cartsRepository.findByUser(user);    //두개 카트에 넣음
        List<Carts> cartsList2 = cartsRepository.findByUser(seller);  //카트에 넣은거 없음

        //then
        assertThat(cartsList1.size()).isEqualTo(2l);
        assertThat(cartsList2.size()).isEqualTo(0l);
    }

    @Test
    public void 장바구니삭제() {
        //given
        saveDefaultEntity();
        cartsRepository.save(Carts.builder().user(user).productOption(productOption1).quantity(2l).build());
        cartsRepository.save(Carts.builder().user(user).productOption(productOption2).quantity(1l).build());

        //when
        List<Carts> cartsList = cartsRepository.findAll();
        int cartsListSize = cartsList.size();
        cartsRepository.deleteById(cartsList.get(0).getId());

        //then
        cartsList = cartsRepository.findAll();
        assertThat(cartsList.size()).isEqualTo(cartsListSize-1);
    }
}