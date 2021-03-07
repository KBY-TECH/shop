package com.team_project.shop.web;

import com.team_project.shop.Service.ProductService;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.domain.product.Category;
import com.team_project.shop.domain.product.Images;
import com.team_project.shop.domain.product.Informations.Informations;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Products;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.network.request.productSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@Controller
public class ProductController {
    private final ProductService productService;

/*
* 상품 등록시 카테고리 선택 페이지 -> 카테고리별 상품 등록 페이지 이동.
* 카테고리별 생성 POST 함수를 작성, ProductSaveRequestDto 또한 카테고리별 생성.
* RequestDto의 ToEntity함수로 엔티티 생성하여 Product와 Product_Options 상속의
* 엔티티 생성 -> 서비스의 save함수의 인자로 사용
* */

    @PostMapping("/meat")
    public String createMeat(@RequestBody productSaveRequestDto.MeatsDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Meat");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/frozenfood")
    public String createFrozenFood(@RequestBody productSaveRequestDto.FrozenFoodsDto dto, @LoginUser Users user){
        Category category = productService.findCategory("FrozenFood");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0;i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/outer")
    public String createOuter(@RequestBody productSaveRequestDto.OuterDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Outer");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/pants")
    public String createPants(@RequestBody productSaveRequestDto.PantsDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Pants");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/seafoods")
    public String createSeaFoods(@RequestBody productSaveRequestDto.SeaFoodsDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Seafoods");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/sportswear")
    public String createSportswear(@RequestBody productSaveRequestDto.SportswearDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Sportswear");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/top")
    public String createTop(@RequestBody productSaveRequestDto.TopDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Outer");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/underwear")
    public String createUnderWear(@RequestBody productSaveRequestDto.UnderWearDto dto, @LoginUser Users user){
        Category category = productService.findCategory("UnderWear");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/wallet")
    public String createWallet(@RequestBody productSaveRequestDto.WalletDto dto, @LoginUser Users user){
        Category category = productService.findCategory("Wallet");
        Products product = dto.getProductDto().toProductEntity(user, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEnity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PostMapping("/bag")
    public String createBag(@RequestBody productSaveRequestDto.BagDto dto, @LoginUser Users user){
        Category category = productService.findCategory("BackPack");
        Products product = dto.getProductDto().toProductEntity(user,category);
        productService.saveProduct(product);
        for(int i=0;i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEnity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i, product, mainImage, detailImage, inform);
            productService.saveOption(option);
        }
        return "";
    }


}
