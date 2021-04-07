package com.team_project.shop.web;

import com.team_project.shop.Service.ProductService;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.product.Informations.*;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.domain.user.UsersRepository;
import com.team_project.shop.network.request.productRequestDto;
import com.team_project.shop.network.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@Controller
public class ProductController {
    private final ProductService productService;
    private final UsersRepository usersRepository;

    @GetMapping("/seller")
    public String getCategorySelectForm(Model model, @LoginUser SessionUser user){
        if(user == null) return "index";
        model.addAttribute("loginUser",user.getName());
        return "seller/new_category_select";
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
    @GetMapping("/seller/productType={productType}")
    public String getRegistrationForm(@PathVariable String productType, Model model, @LoginUser SessionUser user){
        if(user == null) return "index";
        model.addAttribute("loginUser",user.getName());
        return "seller/new_product_"+productType;
    }

    @GetMapping("/seller/{productID}")
    public String getUpdateForm(@PathVariable Long productID,@LoginUser SessionUser user, Model model){
        if(user==null) return "index";
        //상품이 존재하지 않는 경우 생성페이지로 넘기고 alert.
        Products product = productService.findById(productID);
        List<String> categories = new ArrayList<>();
        String type = "";
        for(int i=0; i<product.getCategories().size(); i++){
            Category category = product.getCategories().get(i);
            if(i==0){
                type = category.getName().toLowerCase(Locale.ROOT);
            }
            categories.add(category.getClassification().getSubClass());
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
        return "seller/update_product_"+type;
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
        String type = product.getCategories().get(0).getName().toLowerCase(Locale.ROOT);
        List<Product_Options> options = productService.findAllByProductId(productID);
        ProductsResponseDto.ProductDetailDto dto = ProductsResponseDto.ProductDetailDto.builder()
                .product(product)
                .options(options)
                .build();
        model.addAttribute("product",dto);
        //상품 카테고리마다 html만 다른 값으로 리턴할 예정
        return "item/"+type;
    }

    @PostMapping("/meat")
    public String createMeat(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("Meat");
        productRequestDto.MeatsDto dto = productRequestDto.MeatsDto.builder().request(request).build();
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/meat/{productId}")
    public String updateMeat(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.MeatsDto dto = productRequestDto.MeatsDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Meats Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Meats inform = (Meats) option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }
        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/frozenfood")
    public String createFrozenFood(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("FrozenFood");
        productRequestDto.FrozenFoodsDto dto = productRequestDto.FrozenFoodsDto.builder().request(request).build();
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0;i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/frozenfood/{productId}")
    public String updateFrozenFood(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.FrozenFoodsDto dto = productRequestDto.FrozenFoodsDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                FrozenFoods Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                FrozenFoods inform = (FrozenFoods) option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }
        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/outer")
    public String createOuter(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("Outer");
        productRequestDto.OuterDto dto = productRequestDto.OuterDto.builder().request(request).build();
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/outer/{productId}")
    public String updateOuter(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.OuterDto dto = productRequestDto.OuterDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Outer Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Outer inform = (Outer)option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }
        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/pants")
    public String createPants(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("Pants");
        productRequestDto.PantsDto dto = productRequestDto.PantsDto.builder().request(request).build();
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/pants/{productId}")
    public String updatePants(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.PantsDto dto = productRequestDto.PantsDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Pants Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Pants inform = (Pants)option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/seafood")
    public String createSeaFoods(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("SeaFood");
        productRequestDto.SeaFoodsDto dto = productRequestDto.SeaFoodsDto.builder().request(request).build();
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/seafood/{productId}")
    public String updateSeaFoods(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.SeaFoodsDto dto = productRequestDto.SeaFoodsDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                SeaFoods Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                SeaFoods inform = (SeaFoods) option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/sportswear")
    public String createSportswear(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("Sportswear");
        Users userResult = usersRepository.findById(user.getId()).get();
        productRequestDto.SportswearDto dto = productRequestDto.SportswearDto.builder().request(request).build();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/sportswear/{productId}")
    public String updateSportswear(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.SportswearDto dto = productRequestDto.SportswearDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Sportswear Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Sportswear inform = (Sportswear)option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/top")
    public String createTop(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("Outer");
        productRequestDto.TopDto dto = productRequestDto.TopDto.builder().request(request).build();
        Users userResult = usersRepository.findById(user.getId()).get();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/top/{productId}")
    public String updateTop(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.TopDto dto = productRequestDto.TopDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Top Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Top inform = (Top)option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/underwear")
    public String createUnderwear(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("UnderWear");
        Users userResult = usersRepository.findById(user.getId()).get();
        productRequestDto.UnderWearDto dto = productRequestDto.UnderWearDto.builder().request(request).build();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "";
    }

    @PutMapping("/underwear/{productId}")
    public String updateUnderwear(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.UnderWearDto dto = productRequestDto.UnderWearDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                UnderWear Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                UnderWear inform = (UnderWear) option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/wallet")
    public String createWallet(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category category = productService.findCategory("Wallet");
        Users userResult = usersRepository.findById(user.getId()).get();
        productRequestDto.WalletDto dto = productRequestDto.WalletDto.builder().request(request).build();
        Products product = dto.getProductDto().toProductEntity(userResult, category);
        productService.saveProduct(product);
        for(int i=0; i<dto.getProductDto().Length(); i++){
            Informations inform = dto.toInformationEntity(i);
            productService.saveInformation(inform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
            productService.saveOption(option);
        }
        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/wallet/{productId}")
    public String updateWallet(@PathVariable Long productId, MultipartHttpServletRequest request){
        Products product = productService.findById(productId);
        productRequestDto.WalletDto dto = productRequestDto.WalletDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Wallet Inform = dto.toInformationEntity(i);
                productService.saveInformation(Inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,Inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Wallet inform = (Wallet)option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }

    @PostMapping("/bag")
    public String createBag(MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Category LeafCategory = productService.findCategory("Bag");
        Users userResult = usersRepository.findById(user.getId()).get();
        productRequestDto.BagDto dto = productRequestDto.BagDto.builder().request(request).build();
        Products product = dto.getProductDto().toProductEntity(userResult,LeafCategory);
        productService.saveProduct(product);

        for(int i=0;i<dto.getProductDto().Length(); i++){
            Informations bagInform = dto.toInformationEntity(i);
            productService.saveInformation(bagInform);
            Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
            Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
            Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,bagInform);
            productService.saveOption(option);
        }

        return "redirect:/api/product/seller/user";
    }

    @PutMapping("/bag/{productId}")
    public String updateBag(@PathVariable Long productId, MultipartHttpServletRequest request, @LoginUser SessionUser user){
        Products product = productService.findById(productId);
        productRequestDto.BagDto dto = productRequestDto.BagDto.builder().request(request).build();
        List<Product_Options> options = productService.findAllByProductId(productId);

        dto.getProductDto().updateProduct(product);
        for (int i=0; i<dto.getProductDto().Length();i++){
            if( i >= options.size()){
                Bag inform = dto.toInformationEntity(i);
                productService.saveInformation(inform);
                Images mainImage = productService.saveImage(dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product);
                Images detailImage = productService.saveImage(dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product);
                Product_Options option = dto.getProductDto().toProductOptionEntity(i,product,mainImage,detailImage ,inform);
                productService.saveOption(option);
            }
            else{
                Product_Options option = options.get(i);
                Bag inform = (Bag)option.getInformation();
                dto.updateInformation(i,inform);
                dto.getProductDto().updateOption(i,option,
                        productService.updateImage(option.getMainImage(),dto.getProductDto().getMainImageMap().get(i),i+"_main.jpg",product),
                        productService.updateImage(option.getDetailImage(),dto.getProductDto().getDetailImageMap().get(i),i+"_detail.jpg",product));
            }
        }

        return "redirect:/api/product/seller/user";
    }
}
