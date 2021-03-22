package com.team_project.shop.Service;

import com.team_project.shop.domain.Address.Address;
import com.team_project.shop.domain.Address.AddressRepository;
import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.cart.CartsRepository;
import com.team_project.shop.domain.coupon.CouponRepository;
import com.team_project.shop.domain.middleEntity.OrderDetailsRepository;
import com.team_project.shop.domain.order.OrderStatus;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.order.OrdersRepository;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import com.team_project.shop.network.request.OrdersSaveRequestDto;
import com.team_project.shop.network.request.OrdersUpdateRequestDto;
import com.team_project.shop.network.response.OrdersResponseDto;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.InstanceOfAssertFactories.array;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Autowired private OrderService orderService;
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private CartsRepository cartsRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private UsersRepository usersRepository;
    @Autowired private ProductsRepository productsRepository;
    @Autowired private Product_OptionsRepository productOptionsRepository;
    @Autowired private OrderDetailsRepository orderDetailsRepository;
    @Autowired private CouponRepository couponRepository;
    @Autowired private AddressRepository addressRepository;

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
    Carts cart1 = Carts.builder().user(user).productOption(productOption1).quantity(2l).build();
    Carts cart2 = Carts.builder().user(user).productOption(productOption2).quantity(1l).build();

    public void saveDefaultEntity() {
        usersRepository.save(user);
        usersRepository.save(seller);
        categoryRepository.save(category);
        productsRepository.save(product);
        productOptionsRepository.save(productOption1);
        productOptionsRepository.save(productOption2);
        cartsRepository.save(cart1);
        cartsRepository.save(cart2);
    }

    @Test
    public void save테스트() {
        saveDefaultEntity();
        OrdersSaveRequestDto dto = OrdersSaveRequestDto.builder()
                .totalPrice(1000l).deliveryFee(500l).totalPayment(1500l).destination("경기도").cartId(Arrays.asList(cart1.getId(), cart2.getId())).couponId(0l).build();

        Long result = orderService.save(user, dto);

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(result).isGreaterThan(0);
        assertThat(ordersList.size()).isEqualTo(1);
        assertThat(ordersList.get(0).getOrderDetails().size()).isEqualTo(2);
    }

    @Test
    public void update테스트() {
        saveDefaultEntity();
        OrdersSaveRequestDto saveRequestDtodto = OrdersSaveRequestDto.builder()
                .totalPrice(1000l).deliveryFee(500l).totalPayment(1500l).destination("경기도").cartId(Arrays.asList(cart1.getId(), cart2.getId())).couponId(0l).build();
        Long orderId = orderService.save(user, saveRequestDtodto);
        OrdersUpdateRequestDto updateRequestDto = OrdersUpdateRequestDto.builder().orderStatus(OrderStatus.CANCEL).build();

        List<Orders> ordersList = ordersRepository.findAll();
        OrderStatus status = ordersList.get(0).getOrderStatus();
        orderService.update(orderId, updateRequestDto);

        ordersList = ordersRepository.findAll();
        assertThat(ordersList.get(0).getOrderStatus()).isNotEqualByComparingTo(status);
        assertThat(ordersList.get(0).getOrderStatus()).isEqualByComparingTo(OrderStatus.CANCEL);
    }

    @Test
    public void delete테스트() {
        saveDefaultEntity();
        OrdersSaveRequestDto saveRequestDtodto = OrdersSaveRequestDto.builder()
                .totalPrice(1000l).deliveryFee(500l).totalPayment(1500l).destination("경기도").cartId(Arrays.asList(cart1.getId(), cart2.getId())).couponId(0l).build();
        Long orderId = orderService.save(user, saveRequestDtodto);

        List<Orders> ordersList = ordersRepository.findAll();
        int ordersListSize = ordersList.size();
        orderService.delete(orderId);

        ordersList = ordersRepository.findAll();
        assertThat(ordersList.size()).isEqualTo(ordersListSize - 1);
    }

    @Test
    public void findById테스트() {
        saveDefaultEntity();
        OrdersSaveRequestDto saveRequestDtodto = OrdersSaveRequestDto.builder()
                .totalPrice(1000l).deliveryFee(500l).totalPayment(1500l).destination("경기도").cartId(Arrays.asList(cart1.getId(), cart2.getId())).couponId(0l).build();
        Long orderId = orderService.save(user, saveRequestDtodto);

        OrdersResponseDto responseDto = orderService.findById(orderId);

        assertThat(responseDto.getId()).isEqualTo(orderId);
        assertThat(responseDto.getTotalPrice()).isEqualTo(1000);
    }
}