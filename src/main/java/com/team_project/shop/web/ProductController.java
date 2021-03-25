package com.team_project.shop.web;

import com.team_project.shop.Service.ProductService;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.product.Informations.*;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import com.team_project.shop.network.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@Controller
public class ProductController {
    private final ProductService productService;
    private final UsersRepository usersRepository;

    @GetMapping("/seller")
    public String getRegistrationForm(@LoginUser SessionUser user){
        if(user == null) return "index";

        return "seller/new_product";
    }
    @GetMapping("/seller/user")
    public String getSellerProductList(@LoginUser SessionUser user, Model model){
        if(user == null) return "index";
        Users userEntity = usersRepository.findById(user.getId()).get();
        List<Products> results = productService.findAllByUserId(userEntity.getId());
        List<ProductsResponseDto.UserProducts> dto = new ArrayList<>();
        for(Products product: results){
             dto.add(ProductsResponseDto.UserProducts.builder()
                    .product(product)
                    .build());
        }
        model.addAttribute("products",dto);
        return "seller/product_list";
    }
    @GetMapping("/seller/{productID}")
    public String getUpdateForm(@PathVariable Long productID,@LoginUser SessionUser user, Model model){
        if(user==null) return "index";
        //상품이 존재하지 않는 경우 생성페이지로 넘기고 alert.
        Products product = productService.findById(productID);
        List<String> categories = new ArrayList<>();
        for(Category category : product.getCategories()){
            categories.add(category.getName());
        }
        List<Product_Options> options = productService.findAllByProductId(productID);
        List<ProductsResponseDto.ProductOptions> dto = new ArrayList<>();
        for(Product_Options option : options){
            dto.add(ProductsResponseDto.ProductOptions.builder()
                    .option(option)
                    .build());
        }
        model.addAttribute("productID",product.getId());
        model.addAttribute("productName",product.getName());
        model.addAttribute("categories",categories);
        model.addAttribute("options",dto);
        model.addAttribute("optionSize",options.size());
        return "seller/update_product";
    }

    @GetMapping("/categories/{categoryId}")
    public String findByCategory(@PathVariable Long categoryId, Model model){
        Category category = productService.findCategory(categoryId);
        List<Products> products = category.getProducts();
        List<ProductsResponseDto.CategoryProductItemDto> itemDto = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        while(category.getParent()!=null){
            category = category.getParent();
            categories.add(category);
        }
        for(Products product : products){
            itemDto.add(ProductsResponseDto.CategoryProductItemDto.builder()
                    .product(product)
                    .option(productService.findByProductId(product.getId()))
                    .build());
        }
        ProductsResponseDto.CategoryProductListDto dto =
                ProductsResponseDto.CategoryProductListDto.builder()
                        .categories(categories)
                        .items(itemDto)
                        .build();
        model.addAttribute("product",dto);
        return "item/category_list";
    }

    @GetMapping("/{productID}")
    public String getProductDetail(@PathVariable Long productID, Model model){
        Products product = productService.findById(productID);
        List<Product_Options> options = productService.findAllByProductId(productID);
        ProductsResponseDto.ProductDetailDto dto = ProductsResponseDto.ProductDetailDto.builder()
                .product(product)
                .options(options)
                .build();
        model.addAttribute("product",dto);
        //상품 카테고리마다 html만 다른 값으로 리턴할 예정
        return "item/bag";
    }
//    //TODO : Post, Put 매핑에 대해 enum function으로 간소화해보기
//    @PostMapping("/meat")
//    public String createMeat(@RequestBody productSaveRequestDto.MeatsDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Meat");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/meat/{productId}")
//    public String updateMeat(@PathVariable Long productId, @RequestBody productUpdateRequestDto.MeatsDto dto){
//        Category category = productService.findCategory("Meat");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            Meats inform = (Meats)option.getInformation();
//            inform.update(dto.getMeatPart()[i],dto.getFoodsDto().getProducer()[i],
//                    dto.getOrigin()[i],dto.getFoodsDto().getQualityMaintenanceDate()[i],
//                    dto.getIndication()[i],dto.getFoodsDto().getImportedFood()[i],
//                    dto.getComposition()[i],dto.getStorageMethod()[i], dto.getFoodsDto().getPrecaution()[i],
//                    dto.getFoodsDto().getConsumerCounselingPhoneNum()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/frozenfood")
//    public String createFrozenFood(@RequestBody productSaveRequestDto.FrozenFoodsDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("FrozenFood");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0;i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/frozenfood/{productId}")
//    public String updateFrozenFood(@PathVariable Long productId, @RequestBody productUpdateRequestDto.FrozenFoodsDto dto){
//        Category category = productService.findCategory("FrozenFood");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            FrozenFoods inform = (FrozenFoods)option.getInformation();
//            inform.update(dto.getFoodType()[i],dto.getFoodsDto().getProducer()[i],
//                    dto.getFoodsDto().getQualityMaintenanceDate()[i],
//                    dto.getCapacityByPackingUnit()[i],dto.getMaterialContent()[i],
//                    dto.getNutritionalIngredients()[i],dto.getGeneticallyModified()[i],
//                    dto.getFoodsDto().getPrecaution()[i],dto.getFoodsDto().getImportedFood()[i],
//                    dto.getFoodsDto().getConsumerCounselingPhoneNum()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/outer")
//    public String createOuter(@RequestBody productSaveRequestDto.OuterDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Outer");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/outer/{productId}")
//    public String updateOuter(@PathVariable Long productId, @RequestBody productUpdateRequestDto.OuterDto dto){
//        Category category = productService.findCategory("Outer");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            Outer inform = (Outer)option.getInformation();
//            inform.update(dto.getClothesDto().getMaterial()[i],dto.getClothesDto().getColor()[i],
//                    dto.getClothesDto().getSize()[i],dto.getClothesDto().getProducer()[i],
//                    dto.getClothesDto().getMadeIn()[i],dto.getClothesDto().getPrecautions()[i],
//                    dto.getClothesDto().getManufacturedDate()[i],dto.getClothesDto().getQualityAssuranceStandard()[i],
//                    dto.getClothesDto().getAfterServiceAddress()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/pants")
//    public String createPants(@RequestBody productSaveRequestDto.PantsDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Pants");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/pants/{productId}")
//    public String updatePants(@PathVariable Long productId, @RequestBody productUpdateRequestDto.PantsDto dto){
//        Category category = productService.findCategory("Pants");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            Pants inform = (Pants) option.getInformation();
//            inform.update(dto.getClothesDto().getMaterial()[i],dto.getClothesDto().getColor()[i],
//                    dto.getClothesDto().getSize()[i],dto.getClothesDto().getProducer()[i],
//                    dto.getClothesDto().getMadeIn()[i],dto.getClothesDto().getPrecautions()[i],
//                    dto.getClothesDto().getManufacturedDate()[i],dto.getClothesDto().getQualityAssuranceStandard()[i],
//                    dto.getClothesDto().getAfterServiceAddress()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/seafoods")
//    public String createSeaFoods(@RequestBody productSaveRequestDto.SeaFoodsDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Seafoods");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/seafoods/{productId}")
//    public String updateSeaFoods(@PathVariable Long productId, @RequestBody productUpdateRequestDto.SeaFoodsDto dto){
//        Category category = productService.findCategory("SeaFoods");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            SeaFoods inform = (SeaFoods) option.getInformation();
//            inform.update(dto.getFoodsDto().getProducer()[i], dto.getFoodsDto().getQualityMaintenanceDate()[i],
//                    dto.getFoodsDto().getImportedFood()[i],dto.getFoodsDto().getPrecaution()[i],
//                    dto.getFoodsDto().getConsumerCounselingPhoneNum()[i],dto.getFoodName()[i],
//                    dto.getFoodType()[i], dto.getCapacityByPackingUnit()[i],dto.getMaterialContent()[i],
//                    dto.getNutritionalIngredients()[i],dto.getGeneticallyModified()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/sportswear")
//    public String createSportswear(@RequestBody productSaveRequestDto.SportswearDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Sportswear");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/sportswear/{productId}")
//    public String updateSportswear(@PathVariable Long productId, @RequestBody productUpdateRequestDto.SportswearDto dto){
//        Category category = productService.findCategory("Sportswear");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            Sportswear inform = (Sportswear) option.getInformation();
//            inform.update(dto.getClothesDto().getMaterial()[i],dto.getClothesDto().getColor()[i],
//                    dto.getClothesDto().getSize()[i],dto.getClothesDto().getProducer()[i],
//                    dto.getClothesDto().getMadeIn()[i],dto.getClothesDto().getPrecautions()[i],
//                    dto.getClothesDto().getManufacturedDate()[i],dto.getClothesDto().getQualityAssuranceStandard()[i],
//                    dto.getClothesDto().getAfterServiceAddress()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/top")
//    public String createTop(@RequestBody productSaveRequestDto.TopDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Outer");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/top/{productId}")
//    public String updateTop(@PathVariable Long productId, @RequestBody productUpdateRequestDto.TopDto dto){
//        Category category = productService.findCategory("Top");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            Top inform = (Top) option.getInformation();
//            inform.update(dto.getClothesDto().getMaterial()[i],dto.getClothesDto().getColor()[i],
//                    dto.getClothesDto().getSize()[i],dto.getClothesDto().getProducer()[i],
//                    dto.getClothesDto().getMadeIn()[i],dto.getClothesDto().getPrecautions()[i],
//                    dto.getClothesDto().getManufacturedDate()[i],dto.getClothesDto().getQualityAssuranceStandard()[i],
//                    dto.getClothesDto().getAfterServiceAddress()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/underwear")
//    public String createUnderWear(@RequestBody productSaveRequestDto.UnderWearDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("UnderWear");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/underwear/{productId}")
//    public String updateUnderwear(@PathVariable Long productId, @RequestBody productUpdateRequestDto.UnderWearDto dto){
//        Category category = productService.findCategory("UnderWear");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            UnderWear inform = (UnderWear) option.getInformation();
//            inform.update(dto.getClothesDto().getMaterial()[i],dto.getClothesDto().getColor()[i],
//                    dto.getClothesDto().getSize()[i],dto.getClothesDto().getProducer()[i],
//                    dto.getClothesDto().getMadeIn()[i],dto.getClothesDto().getPrecautions()[i],
//                    dto.getClothesDto().getManufacturedDate()[i],dto.getClothesDto().getQualityAssuranceStandard()[i],
//                    dto.getClothesDto().getAfterServiceAddress()[i]);
//        }
//
//        return "";
//    }
//
//    @PostMapping("/wallet")
//    public String createWallet(@RequestBody productSaveRequestDto.WalletDto dto, @LoginUser Users user){
//        Category category = productService.findCategory("Wallet");
//        Products product = dto.getProductDto().toProductEntity(user, category);
//        productService.saveProduct(product);
//        for(int i=0; i<dto.getProductDto().Length(); i++){
//            Informations inform = dto.toInformationEntity(i);
//            productService.saveInformation(inform);
//            Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//            productService.saveOption(option);
//        }
//        return "";
//    }
//
//    @PutMapping("/wallet/{productId}")
//    public String updateWallet(@PathVariable Long productId, @RequestBody productUpdateRequestDto.WalletDto dto){
//        Category category = productService.findCategory("Wallet");
//        Products product = productService.findById(productId);
//        product.update(dto.getProductDto().getProductName());
//        List<Product_Options> options = productService.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        for (int i=0; i<=dto.getProductDto().Length();i++){
//            if( i >= options.size()){
//                Informations inform = dto.toInformationEntity(i);
//                productService.saveInformation(inform);
//                Images mainImage = productService.saveImage(dto.getProductDto().getMainImage().get(i),i+"_main.jpg",product);
//                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImage().get(i),i+"_detail.jpg",product);
//                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
//                productService.saveOption(option);
//            }
//            Product_Options option = options.get(i);
//            option.update(dto.getProductDto().getOptionName()[i],
//                    productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImage().get(i), i+"_main.jpg",product),
//                    productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImage().get(i), i+"_detail.jpg",product),
//                    Long.parseLong(dto.getProductDto().getPrice()[i]),
//                    Long.parseLong(dto.getProductDto().getStock()[i]),
//                    dto.getProductDto().getStock()[i]);
//            Wallet inform = (Wallet) option.getInformation();
//            inform.update(dto.getAccessoriesDto().getKind()[i], dto.getAccessoriesDto().getMaterial()[i],
//                    dto.getAccessoriesDto().getSize()[i],dto.getAccessoriesDto().getProducer()[i],
//                    dto.getAccessoriesDto().getMadeIn()[i],dto.getAccessoriesDto().getPrecautions()[i],
//                    dto.getAccessoriesDto().getQualityAssuranceStandard()[i], dto.getAccessoriesDto().getAfterServiceAddress()[i]);
//        }
//
//        return "";
//    }

    @PostMapping("/bag")
    public String createBag(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category LeafCategory = productService.findCategory("Bag");
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = Products.builder()
                .name(request.getParameter("productName"))
                .user(userResult)
                .category(LeafCategory)
                .build();
        productService.saveProduct(product);

        String[] optionNames = request.getParameterValues("optionName");
        String[] optionPrices = request.getParameterValues("price");
        String[] optionStocks = request.getParameterValues("stock");
        List<MultipartFile> mainImages = request.getFiles("mainImage");
        List<MultipartFile> detailImages = request.getFiles("detailImage");

        String[] kinds = request.getParameterValues("kind");
        String[] materials = request.getParameterValues("material");
        String[] colors = request.getParameterValues("color");
        String[] sizes = request.getParameterValues("size");
        String[] producers = request.getParameterValues("producer");
        String[] madeIn = request.getParameterValues("madeIn");
        String[] precautions = request.getParameterValues("precautions");
        String[] qualityAssuranceStandards = request.getParameterValues("qualityAssuranceStandard");
        String[] afterServiceAddresses = request.getParameterValues("afterServiceAddress");

        for(int i=0; i<optionNames.length; i++){
            Bag inform = Bag.builder()
                    .kind(kinds[i])
                    .material(materials[i])
                    .color(colors[i])
                    .size(sizes[i])
                    .producer(producers[i])
                    .madeIn(madeIn[i])
                    .precautions(precautions[i])
                    .qualityAssuranceStandard(qualityAssuranceStandards[i])
                    .afterServiceAddress(afterServiceAddresses[i])
                    .build();
            productService.saveInformation(inform);
            Product_Options option = Product_Options.builder()
                    .optionName(optionNames[i])
                    .product(product)
                    .mainImage(productService.saveImage(mainImages.get(i),i+"_main.jpg",product))
                    .detailImage(productService.saveImage(detailImages.get(i),i+"_detail.jpg",product))
                    .price(Long.parseLong(optionPrices[i]))
                    .stock(Long.parseLong(optionStocks[i]))
                    .information(inform)
                    .build();
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/bag/{productId}")
    public String updateBag(@PathVariable Long productId, MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Products product = productService.findById(productId);
        product.update(request.getParameter("productName"));

        //TODO : ParameterValues 중복 코드 간소화
        String[] optionNames = request.getParameterValues("optionName");
        String[] optionPrices = request.getParameterValues("price");
        String[] optionStocks = request.getParameterValues("stock");
        List<MultipartFile> mainImages = request.getFiles("mainImage");
        String[] mainImageKeys = request.getParameterValues("mainImageKey");
        List<MultipartFile> detailImages = request.getFiles("detailImage");
        String[] detailImageKeys = request.getParameterValues("detailImageKey");
        String[] optionState = request.getParameterValues("state");
        String[] kinds = request.getParameterValues("kind");
        String[] materials = request.getParameterValues("material");
        String[] colors = request.getParameterValues("color");
        String[] sizes = request.getParameterValues("size");
        String[] producers = request.getParameterValues("producer");
        String[] madeIn = request.getParameterValues("madeIn");
        String[] precautions = request.getParameterValues("precautions");
        String[] qualityAssuranceStandards = request.getParameterValues("qualityAssuranceStandard");
        String[] afterServiceAddresses = request.getParameterValues("afterServiceAddress");

        List<Product_Options> options = productService.findAllByProductId(productId);
        HashMap<Integer, MultipartFile> mainImageMap = new HashMap<>();
        for(int i=0;i<mainImages.size();i++){
            var key = Integer.parseInt(mainImageKeys[i])-1;
            mainImageMap.put(key,mainImages.get(i));
        }
        HashMap<Integer,MultipartFile> detailImageMap = new HashMap<>();
        for(int i=0;i<detailImages.size();i++){
            var key = Integer.parseInt(detailImageKeys[i])-1;
            detailImageMap.put(key,detailImages.get(i));
        }


        for (int i=0; i<optionNames.length;i++){
            if( i >= options.size()){
                Bag inform = Bag.builder()
                        .kind(kinds[i])
                        .material(materials[i])
                        .color(colors[i])
                        .size(sizes[i])
                        .producer(producers[i])
                        .madeIn(madeIn[i])
                        .precautions(precautions[i])
                        .qualityAssuranceStandard(qualityAssuranceStandards[i])
                        .afterServiceAddress(afterServiceAddresses[i])
                        .build();
                productService.saveInformation(inform);
                Product_Options option = Product_Options.builder()
                        .optionName(optionNames[i])
                        .product(product)
                        .mainImage(productService.saveImage(mainImageMap.get(i),i+"_main.jpg",product))
                        .detailImage(productService.saveImage(detailImageMap.get(i),i+"_detail.jpg",product))
                        .price(Long.parseLong(optionPrices[i]))
                        .stock(Long.parseLong(optionStocks[i]))
                        .information(inform)
                        .build();
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Bag inform = (Bag)option.getInformation();
                inform.update(kinds[i],materials[i],colors[i],sizes[i],producers[i],madeIn[i],
                        precautions[i],qualityAssuranceStandards[i],afterServiceAddresses[i]);
                option.update(optionNames[i],
                        productService.updateImage(option.getMainImage(),mainImageMap.get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),detailImageMap.get(i),i+"_detail.jpg",product),
                        Long.parseLong(optionPrices[i]),
                        Long.parseLong(optionStocks[i]),
                        optionState[i]);
            }
        }

        return "redirect:/api/product/seller/user";
    }
}
