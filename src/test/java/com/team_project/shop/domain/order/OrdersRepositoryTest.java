package com.team_project.shop.domain.order;

import com.team_project.shop.domain.Address.AddressRepository;
import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.cart.CartsRepository;
import com.team_project.shop.domain.coupon.CouponRepository;
import com.team_project.shop.domain.middleEntity.OrderDetailsRepository;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import org.junit.After;
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
public class OrdersRepositoryTest {
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
    Orders order1 = Orders.builder()
            .user(user)
            .deliveryFee(500l)
            .totalPrice(1500l)
            .totalPayment(2000l)
            .build();

    Orders order2 = Orders.builder()
            .user(seller)
            .deliveryFee(0l)
            .totalPrice(1000l)
            .totalPayment(1000l)
            .build();

    public void saveDefaultEntity() {
        usersRepository.save(user);
        usersRepository.save(seller);
        categoryRepository.save(category);
        productsRepository.save(product);
        productOptionsRepository.save(productOption1);
        productOptionsRepository.save(productOption2);
        cartsRepository.save(Carts.builder().user(user).productOption(productOption1).quantity(2l).build());
        cartsRepository.save(Carts.builder().user(user).productOption(productOption2).quantity(1l).build());
    }

    @Test
    public void 주문저장() {
        //given
        saveDefaultEntity();
        ordersRepository.save(order1);

        //when
        List<Orders> ordersList = ordersRepository.findAll();

        //then
        Orders savedOrder = ordersList.get(0);
        assertThat(savedOrder.getId()).isEqualTo(order1.getId());
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(savedOrder.getTotalPayment()).isEqualTo(2000l);
        assertThat(savedOrder.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void 사용자주문조회() {
        //given
        saveDefaultEntity();
        ordersRepository.save(order1);

        //when
        List<Optional<Orders>> orderList1 = ordersRepository.findByUserName(user.getName());
        List<Optional<Orders>> orderList2 = ordersRepository.findByUserName(seller.getName());

        //then
        assertThat(orderList1.size()).isEqualTo(1);
        assertThat(orderList1.get(0).get().getId()).isEqualTo(order1.getId());
        assertThat(orderList2.size()).isEqualTo(0);
    }

    @Test
    public void 주문삭제() {
        //given
        saveDefaultEntity();
        ordersRepository.save(order1);
        ordersRepository.save(order2);

        //when
        List<Orders> ordersList = ordersRepository.findAll();
        int ordersListSize = ordersList.size();
        ordersRepository.deleteById(ordersList.get(0).getId());

        //then
        ordersList = ordersRepository.findAll();
        assertThat(ordersList.size()).isEqualTo(ordersListSize-1);
    }
}