package com.team_project.shop.domain.middleEntity;

import com.team_project.shop.domain.Address.AddressRepository;
import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.cart.CartsRepository;
import com.team_project.shop.domain.coupon.CouponRepository;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.order.OrdersRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailsRepositoryTest {
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
            .user(user)
            .deliveryFee(0l)
            .totalPrice(1000l)
            .totalPayment(1000l)
            .build();
    OrderDetails orderDetail1 = OrderDetails.builder()
            .order(order1)
            .productOption(productOption1)
            .quantity(2l)
            .build();
    OrderDetails orderDetail2 = OrderDetails.builder()
            .order(order1)
            .productOption(productOption2)
            .quantity(3l)
            .build();

    OrderDetails orderDetail3 = OrderDetails.builder()
            .order(order2)
            .productOption(productOption2)
            .quantity(3l)
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
        ordersRepository.save(order1);
        ordersRepository.save(order2);

    }

    @Test
    public void 주문상세저장() {
        //given
        saveDefaultEntity();
        orderDetailsRepository.save(orderDetail1);

        //when
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        List<Orders> ordersList = ordersRepository.findAll();

        //then
        OrderDetails savedDetail = orderDetailsList.get(0);
        assertThat(savedDetail.getOrder().getId()).isEqualTo(orderDetail1.getOrder().getId());
        assertThat(savedDetail.getProductOption().getId()).isEqualTo(orderDetail1.getProductOption().getId());
        assertThat(savedDetail.getQuantity()).isEqualTo(orderDetail1.getQuantity());
        assertThat(ordersList.get(0).getOrderDetails().get(0).getId()).isEqualTo(savedDetail.getId());
    }

    @Test
    public void 주문으로주문상세조회() {
        //given
        saveDefaultEntity();
        orderDetailsRepository.save(orderDetail1);
        orderDetailsRepository.save(orderDetail2);
        orderDetailsRepository.save(orderDetail3);

        //when
        List<Optional<Orders>> orderList = ordersRepository.findByUserName(user.getName());
        //order1 order2순으로 주문되어있음
        Orders savedOrder1 = orderList.get(0).get();
        Orders savedOrder2 = orderList.get(1).get();

        //then
        assertThat(savedOrder1.getOrderDetails().size()).isEqualTo(2);
        assertThat(savedOrder2.getOrderDetails().size()).isEqualTo(1);
        assertThat(savedOrder2.getOrderDetails().get(0).getId()).isEqualTo(orderDetail3.getId());

    }

    //주문상세삭제는 없음 그냥 주문삭제임 그럼 주문이 삭제되면 주문상세도 삭제되나 확인
    //주문1 = 2개의 상세  주문2 = 1개의 상세가짐 주문1 삭제되었을 때 총 상세 갯수확인함
    @Test
    public void 주문삭제시주문상세도삭제() {
        //when
        saveDefaultEntity();
        orderDetailsRepository.save(orderDetail1);
        orderDetailsRepository.save(orderDetail2);
        orderDetailsRepository.save(orderDetail3);

        //given
        List<Orders> ordersList = ordersRepository.findAll();
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        int orderIndex0DetailSize = ordersList.get(0).getOrderDetails().size();
        int orderDetailsSize = orderDetailsList.size();
        ordersRepository.deleteById(ordersList.get(0).getId());

        //then
        orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList.size()).isEqualTo(orderDetailsSize - orderIndex0DetailSize);
    }

}