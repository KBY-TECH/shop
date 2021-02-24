package com.team_project.shop;

import com.team_project.shop.Service.ProductService;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTests {

    @Autowired
    ProductService productservice;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    Product_OptionsRepository optionsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    @Rollback(false)
    public void 카테고리_상품() throws Exception{
        //Given
        Category category1 = Category.builder()
                .name("카테고리1")
                .build();
        categoryRepository.save(category1);
        Category category2 = Category.builder()
                .name("카테고리2")
                .build();
        category2.setParent(category1);
        categoryRepository.save(category2);
        Category category3 = Category.builder()
                .name("카테고리3")
                .build();
        category3.setParent(category2);
        categoryRepository.save(category3);
        //When
        Products product = Products.builder()
                .name("상품")
                .category(category3)
                .build();
        productsRepository.save(product);

        //Then
        Assert.assertEquals(categoryRepository.findByName("카테고리1"), productsRepository.findById(product.getId()).get().getCategories().get(2));

        //상품에 최하위 계층 카테고리 연결시 최상위 계층 카테고리도 저장되어 있는 것을 확인.
    }

    @Test
    @Transactional
    @Rollback(false)
    public void 상품등록() throws Exception{
        //Given: 테스트할 상황
        Category category1 = Category.builder()
                .name("카테고리1")
                .build();
        categoryRepository.save(category1);
        Category category2 = Category.builder()
                .name("카테고리2")
                .build();
        category2.setParent(category1);
        categoryRepository.save(category2);
        Category category3 = Category.builder()
                .name("카테고리3")
                .build();
        category3.setParent(category2);
        categoryRepository.save(category3);

        MultipartFile file = new MockMultipartFile("mainImage",new FileInputStream(new File("C:\\Users\\niveu\\Desktop\\Spring study\\JDBC1.PNG")));
        MultipartFile file2 = new MockMultipartFile("detailImage",new FileInputStream(new File("C:\\Users\\niveu\\Desktop\\Spring study\\Untitled Diagram.png")));
        Users user = Users.builder()
                .name("userName")
                .email("mail")
                .picture("picture")
                .role(Role.USER)
                .build();
        usersRepository.save(user);

        //When: 테스트 대상
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.addParameter("CategoryName","카테고리3");
        request.addParameter("productName","상품이름");
        request.addParameter("optionName","옵션1");
        request.addParameter("price","1000");
        request.addParameter("stock","50");
        request.addFile(file);
        request.addFile(file2);

        Long productId = productservice.save(user,request);

        //Then: 결과검증
        Products result = productsRepository.findById(productId).get();
        List<Product_Options> options = optionsRepository.findAllByProductId(result.getId());
        System.out.println("Option List: \n"+options);

        Assert.assertEquals("0_main.jpg",options.get(0).getMainImage().getImageName() );

    }

    @Test
    @Transactional
    @Rollback(false)
    public void 상품수정() throws Exception{
        //Given
        Category category1 = Category.builder()
                .name("카테고리1")
                .build();
        categoryRepository.save(category1);
        Category category2 = Category.builder()
                .name("카테고리2")
                .build();
        category2.setParent(category1);
        categoryRepository.save(category2);
        Category category3 = Category.builder()
                .name("카테고리3")
                .build();
        category3.setParent(category2);
        categoryRepository.save(category3);
        MultipartFile file = new MockMultipartFile("mainImage",
                new FileInputStream(
                        new File("C:\\Users\\niveu\\Desktop\\Spring study\\JDBC1.PNG")));
        MultipartFile file2 = new MockMultipartFile("detailImage",
                new FileInputStream(
                        new File("C:\\Users\\niveu\\Desktop\\Spring study\\Untitled Diagram.png")));
        Users user = Users.builder()
                .name("userName")
                .email("mail")
                .picture("picture")
                .role(Role.USER)
                .build();
        usersRepository.save(user);

        Products product = Products.builder()
                .name("상품이름")
                .user(user)
                .category(category3)
                .build();
        productsRepository.save(product);

        Images mainImage0 = Images.builder()
                .imageName("0_main.jpg")
                .imageURL("/images/0/0_main.jpg")
                .build();
        imagesRepository.save(mainImage0);
        Images detailImage0 = Images.builder()
                .imageName("0_detail.jpg")
                .imageURL("/images/0/0_detail.jpg")
                .build();
        imagesRepository.save(detailImage0);
        Product_Options option = Product_Options.builder()
                .optionName("옵션1")
                .product(product)
                .mainImage(mainImage0)
                .detailImage(detailImage0)
                .price(1000L)
                .stock(50L)
                .build();

        optionsRepository.save(option);
        //When
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.addParameter("productName","상품이름2");
        request.addParameter("optionName","옵션1.2","옵션2");
        request.addParameter("price","200","300");
        request.addParameter("stock","10","20");
        request.addParameter("optionState","OUTOFSTOCK","ONSALE");
        request.addFile(file);
        request.addFile(file2);
        request.addFile(file);
        request.addFile(file2);
        Long productId = productservice.update(product.getId(),request);
        Products result = productsRepository.findById(productId).get();
        System.out.println(result);
        List<Product_Options> options = optionsRepository.findAllByProductId(productId);
        System.out.println("OPTION LIST:\n"+options);
        //Then
        Assert.assertNotNull(options.get(1));
    }
}
