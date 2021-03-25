package com.team_project.shop.network.response;

import com.team_project.shop.domain.product.Category;
import com.team_project.shop.domain.product.Informations.Informations;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Products;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;


public class ProductsResponseDto {

    @Getter
    static public class CategoryProductItemDto{
        private Long id;
        private String productName;
        private String imageURL;
        private String price;
        private String state;
        @Builder
        public CategoryProductItemDto(Products product, Product_Options option){
            this.id=product.getId();
            this.productName = product.getName();
            this.imageURL = option.getMainImage().getImageURL();
            this.price = option.getPrice().toString();
            this.state = option.getState().toText();
        }
    }

    @Getter
    static public class CategoryProductListDto{
        private List<String> categories;
        private List<CategoryProductItemDto> products;

        @Builder
        public CategoryProductListDto(List<Category> categories,List<CategoryProductItemDto> items){
            this.categories = new ArrayList<>();
            for(Category category : categories){
                this.categories.add(category.getName());
            }
            this.products = items;
        }
    }

    @Getter
    static public class ProductDetailDto{
        private String productName;
        private String sellerName;
        private List<String> categories;
        private List<Product_Options> options;

        @Builder
        public ProductDetailDto(Products product, List<Product_Options> options) {
            this.productName = product.getName();
            this.sellerName = product.getUser().getName();
            this.categories = new ArrayList<>();
            for(Category category : product.getCategories()){
                this.categories.add(category.getName());
            }
            this.options = options;
        }
    }

    @Getter
    static public class UserProducts{
        private Long productID;
        private String productName;
        private List<String> categories;
        private LocalDateTime created;

        @Builder
        public UserProducts(Products product){
            this.productID = product.getId();
            this.productName = product.getName();
            this.categories = new ArrayList<>();
            for(Category category : product.getCategories()){
                this.categories.add(category.getName());
            }
            this.created = product.getCreated();
        }
    }

    @Getter
    static public class ProductOptions{
        private Long id;
        private String optionName;
        private String state;
        private String price;
        private String stock;
        private String mainImageURL;
        private String detailImageURL;
        private Informations information;
        @Builder
        public ProductOptions(Product_Options option){
            this.id=option.getId();
            this.optionName = option.getOptionName();
            this.state = option.getState().toText();
            this.price = option.getPrice().toString();
            this.stock = option.getStock().toString();
            this.mainImageURL = option.getMainImage().getImageURL();
            this.detailImageURL = option.getDetailImage().getImageURL();
            this.information = option.getInformation();
        }
    }
}
