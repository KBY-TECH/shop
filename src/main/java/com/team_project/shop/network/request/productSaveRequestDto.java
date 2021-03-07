package com.team_project.shop.network.request;

import com.team_project.shop.domain.product.Category;
import com.team_project.shop.domain.product.Images;
import com.team_project.shop.domain.product.Informations.*;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Products;
import com.team_project.shop.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class productSaveRequestDto {

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class ProductOptionsDto{
        private String productName;
        private String[] optionName;
        private List<MultipartFile> mainImage;
        private List<MultipartFile> detailImage;
        private String[] price;
        private String[] stock;

        public Products toProductEntity(Users user, Category category){
            return Products.builder()
                    .user(user)
                    .name(productName)
                    .category(category)
                    .build();
        }

        public Product_Options toProductOptionEntity(int id, Products product, Images mainImage, Images detailImage, Informations information){
            return Product_Options.builder()
                    .optionName(optionName[id])
                    .product(product)
                    .mainImage(mainImage)
                    .detailImage(detailImage)
                    .price(Long.parseLong(price[id]))
                    .stock(Long.parseLong(stock[id]))
                    .information(information)
                    .build();
        }

        public int Length(){
            return optionName.length;
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class AccessoriesDto{
        private String[] kind;
        private String[] material;
        private String[] size;
        private String[] producer;
        private String[] madeIn;
        private String[] precautions;
        private String[] qualityAssuranceStandard;
        private String[] afterServiceAddress;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class ClothesDto{
        private String[] material;
        private String[] color;
        private String[] size;
        private String[] producer;
        private String[] madeIn;
        private String[] precautions;
        private String[] manufacturedDate;
        private String[] qualityAssuranceStandard;
        private String[] afterServiceAddress;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class FoodsDto{
        private String[] producer;
        private String[] qualityMaintenanceDate;
        private String[] importedFood;
        private String[] precaution;
        private String[] consumerCounselingPhoneNum;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class FrozenFoodsDto{
        private ProductOptionsDto productDto;
        private FoodsDto foodsDto;
        private String[] foodType;
        private String[] capacityByPackingUnit;
        private String[] materialContent;
        private String[] nutritionalIngredients;
        private String[] geneticallyModified;
        public FrozenFoods toInformationEntity(int id){
            return FrozenFoods.builder()
                    .producer(foodsDto.getProducer()[id])
                    .qualityMaintenanceDate(foodsDto.getQualityMaintenanceDate()[id])
                    .importedFood(foodsDto.getImportedFood()[id])
                    .precaution(foodsDto.getPrecaution()[id])
                    .consumerCounselingPhoneNum(foodsDto.getConsumerCounselingPhoneNum()[id])
                    .foodType(foodType[id])
                    .capacityByPackingUnit(capacityByPackingUnit[id])
                    .materialContent(materialContent[id])
                    .nutritionalIngredients(nutritionalIngredients[id])
                    .geneticallyModified(geneticallyModified[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class MeatsDto{
        private ProductOptionsDto productDto;
        private FoodsDto foodsDto;
        private String[] meatPart;
        private String[] origin;
        private String[] indication;
        private String[] composition;
        private String[] storageMethod;
        public Meats toInformationEntity(int id){
            return Meats.builder()
                    .producer(foodsDto.getProducer()[id])
                    .qualityMaintenanceDate(foodsDto.getQualityMaintenanceDate()[id])
                    .importedFood(foodsDto.getImportedFood()[id])
                    .precaution(foodsDto.getPrecaution()[id])
                    .consumerCounselingPhoneNum(foodsDto.getConsumerCounselingPhoneNum()[id])
                    .meatPart(meatPart[id])
                    .origin(origin[id])
                    .indication(indication[id])
                    .composition(composition[id])
                    .storageMethod(storageMethod[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class SeaFoodsDto{
        private ProductOptionsDto productDto;
        private FoodsDto foodsDto;
        private String[] foodName;
        private String[] foodType;
        private String[] capacityByPackingUnit;
        private String[] materialContent;
        private String[] nutritionalIngredients;
        private String[] geneticallyModified;
        public SeaFoods toInformationEntity(int id){
            return SeaFoods.builder()
                    .producer(foodsDto.getProducer()[id])
                    .qualityMaintenanceDate(foodsDto.getQualityMaintenanceDate()[id])
                    .importedFood(foodsDto.getImportedFood()[id])
                    .precaution(foodsDto.getPrecaution()[id])
                    .consumerCounselingPhoneNum(foodsDto.getConsumerCounselingPhoneNum()[id])

                    .foodType(foodType[id])
                    .capacityByPackingUnit(capacityByPackingUnit[id])
                    .materialContent(materialContent[id])
                    .nutritionalIngredients(nutritionalIngredients[id])
                    .geneticallyModified(geneticallyModified[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class OuterDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;
        public Outer toInformationEntity(int id){
            return Outer.builder()
                    .material(clothesDto.getMaterial()[id])
                    .color(clothesDto.getColor()[id])
                    .size(clothesDto.getSize()[id])
                    .producer(clothesDto.getProducer()[id])
                    .madeIn(clothesDto.getMadeIn()[id])
                    .precautions(clothesDto.getPrecautions()[id])
                    .manufacturedDate(clothesDto.getManufacturedDate()[id])
                    .qualityAssuranceStandard(clothesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(clothesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class PantsDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;
        public Pants toInformationEntity(int id){
            return Pants.builder()
                    .material(clothesDto.getMaterial()[id])
                    .color(clothesDto.getColor()[id])
                    .size(clothesDto.getSize()[id])
                    .producer(clothesDto.getProducer()[id])
                    .madeIn(clothesDto.getMadeIn()[id])
                    .precautions(clothesDto.getPrecautions()[id])
                    .manufacturedDate(clothesDto.getManufacturedDate()[id])
                    .qualityAssuranceStandard(clothesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(clothesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class SportswearDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;
        public Sportswear toInformationEntity(int id){
            return Sportswear.builder()
                    .material(clothesDto.getMaterial()[id])
                    .color(clothesDto.getColor()[id])
                    .size(clothesDto.getSize()[id])
                    .producer(clothesDto.getProducer()[id])
                    .madeIn(clothesDto.getMadeIn()[id])
                    .precautions(clothesDto.getPrecautions()[id])
                    .manufacturedDate(clothesDto.getManufacturedDate()[id])
                    .qualityAssuranceStandard(clothesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(clothesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class TopDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;
        public Top toInformationEntity(int id){
            return Top.builder()
                    .material(clothesDto.getMaterial()[id])
                    .color(clothesDto.getColor()[id])
                    .size(clothesDto.getSize()[id])
                    .producer(clothesDto.getProducer()[id])
                    .madeIn(clothesDto.getMadeIn()[id])
                    .precautions(clothesDto.getPrecautions()[id])
                    .manufacturedDate(clothesDto.getManufacturedDate()[id])
                    .qualityAssuranceStandard(clothesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(clothesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class UnderWearDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;
        public UnderWear toInformationEntity(int id){
            return UnderWear.builder()
                    .material(clothesDto.getMaterial()[id])
                    .color(clothesDto.getColor()[id])
                    .size(clothesDto.getSize()[id])
                    .producer(clothesDto.getProducer()[id])
                    .madeIn(clothesDto.getMadeIn()[id])
                    .precautions(clothesDto.getPrecautions()[id])
                    .manufacturedDate(clothesDto.getManufacturedDate()[id])
                    .qualityAssuranceStandard(clothesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(clothesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class BagDto{
        private ProductOptionsDto productDto;
        private AccessoriesDto accessoriesDto;
        private String[] color;

        public Bag toInformationEnity(int id){
            return Bag.builder()
                    .kind(accessoriesDto.getKind()[id])
                    .material(accessoriesDto.getMaterial()[id])
                    .color(color[id])
                    .size(accessoriesDto.getSize()[id])
                    .producer(accessoriesDto.getProducer()[id])
                    .madeIn(accessoriesDto.getMadeIn()[id])
                    .precautions(accessoriesDto.getPrecautions()[id])
                    .qualityAssuranceStandard(accessoriesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(accessoriesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @Data
    static public class WalletDto{
        private ProductOptionsDto productDto;
        private AccessoriesDto accessoriesDto;
        public Wallet toInformationEnity(int id){
            return Wallet.builder()
                    .kind(accessoriesDto.getKind()[id])
                    .material(accessoriesDto.getMaterial()[id])
                    .size(accessoriesDto.getSize()[id])
                    .producer(accessoriesDto.getProducer()[id])
                    .madeIn(accessoriesDto.getMadeIn()[id])
                    .precautions(accessoriesDto.getPrecautions()[id])
                    .qualityAssuranceStandard(accessoriesDto.getQualityAssuranceStandard()[id])
                    .afterServiceAddress(accessoriesDto.getAfterServiceAddress()[id])
                    .build();
        }
    }
}
