package com.team_project.shop;

import com.team_project.shop.Service.ProductService;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import com.team_project.shop.network.request.productSaveRequestDto;
import com.team_project.shop.web.ProductController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
    ProductController productcontroller;

    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    @Rollback(false)
    public void dto테스트() throws Exception{
        //Given
        Category category1 = Category.builder()
                .name("frozenfood")
                .build();
        categoryRepository.save(category1);
        
        Users user = Users.builder()
                .name("userName")
                .email("mail")
                .picture("picture")
                .role(Role.USER)
                .build();
        usersRepository.save(user);

        MultipartFile file = new MockMultipartFile("mainImage",
                new FileInputStream(
                        new File("C:\\Users\\niveu\\Desktop\\Spring study\\JDBC1.PNG")));
        MultipartFile file2 = new MockMultipartFile("detailImage",
                new FileInputStream(
                        new File("C:\\Users\\niveu\\Desktop\\Spring study\\Untitled Diagram.png")));
        List<MultipartFile> mainImages = new ArrayList<>();
        mainImages.add(file);
        mainImages.add(file);
        List<MultipartFile> detailImages = new ArrayList<>();
        detailImages.add(file2);
        detailImages.add(file2);




        //When
        productSaveRequestDto.FrozenFoodsDto dto =
                productSaveRequestDto.FrozenFoodsDto.builder()
                        .productDto(productSaveRequestDto.ProductOptionsDto.builder()
                                .productName("냉동식품1")
                                .optionName(new String[]{"옵션1", "옵션2"})
                                .mainImage(mainImages)
                                .detailImage(detailImages)
                                .price(new String[]{"1000","2000"})
                                .stock(new String[]{"50","30"})
                                .build())
                        .foodsDto(productSaveRequestDto.FoodsDto.builder()
                                .producer(new String[]{"한국","한국"})
                                .qualityMaintenanceDate(new String[]{"1년","1년"})
                                .importedFood(new String[]{"해당없음","해당없음"})
                                .precaution(new String[]{"컨텐츠 참조","컨텐츠 참조"})
                                .consumerCounselingPhoneNum(new String[]{"쿠팡고객센터 1577-7011","쿠팡고객센터 1577-7011"})


                                .build())
                        .foodType(new String[]{"만두","만두"})
                        .capacityByPackingUnit(new String[]{"1kg, 1개","2kg, 1개"})
                        .materialContent(new String[]{"컨텐츠 참조","컨텐츠 참조"})
                        .nutritionalIngredients(new String[]{"해당없음","해당없음"})
                        .geneticallyModified(new String[]{"해당없음", "해당없음"})
                        .build();

        //Then
        productcontroller.createFrozenFood(dto,user);
        List<Product_Options> results= optionsRepository.findAllByProductId(1L);
        System.out.println("result : \n"+results);
        Assert.assertNotNull(results);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void dto테스트2() throws Exception{
        //Given
        Category category1 = Category.builder()
                .name("BackPack")
                .build();
        categoryRepository.save(category1);

        Users user = Users.builder()
                .name("userName")
                .email("mail")
                .picture("picture")
                .role(Role.USER)
                .build();
        usersRepository.save(user);

        MultipartFile file = new MockMultipartFile("mainImage",
                new FileInputStream(
                        new File("C:\\Users\\niveu\\Desktop\\Spring study\\JDBC1.PNG")));
        MultipartFile file2 = new MockMultipartFile("detailImage",
                new FileInputStream(
                        new File("C:\\Users\\niveu\\Desktop\\Spring study\\Untitled Diagram.png")));
        List<MultipartFile> mainImages = new ArrayList<>();
        mainImages.add(file);
        mainImages.add(file);
        List<MultipartFile> detailImages = new ArrayList<>();
        detailImages.add(file2);
        detailImages.add(file2);

        //When
        productSaveRequestDto.BagDto backPackDto =
                productSaveRequestDto.BagDto.builder()
                        .productDto(productSaveRequestDto.ProductOptionsDto.builder()
                                .productName("가방1")
                                .optionName(new String[]{"옵션1", "옵션2"})
                                .mainImage(mainImages)
                                .detailImage(detailImages)
                                .price(new String[]{"1000","2000"})
                                .stock(new String[]{"50","30"})
                                .build())
                        .accessoriesDto(productSaveRequestDto.AccessoriesDto.builder()
                                .kind(new String[]{"크로스백","백팩"})
                                .material(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .size(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .producer(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .madeIn(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .precautions(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .qualityAssuranceStandard(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .afterServiceAddress(new String[]{"상품 상세 페이지 참조","상품 상세 페이지 참조"})
                                .build())
                        .color(new String[]{"하양","빨강"})
                        .build();

        //Then
        productcontroller.createBag(backPackDto,user);
        List<Product_Options> results= optionsRepository.findAllByProductId(1L);
        System.out.println("result : \n"+results);
        Assert.assertNotNull(results);

    }
}
