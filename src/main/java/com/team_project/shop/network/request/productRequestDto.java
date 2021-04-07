package com.team_project.shop.network.request;

import com.team_project.shop.domain.product.Category;
import com.team_project.shop.domain.product.Images;
import com.team_project.shop.domain.product.Informations.*;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Products;
import com.team_project.shop.domain.user.Users;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;


public class productRequestDto {

    @Getter
    static public class ProductOptionsDto{
        private String productName;
        private String[] optionName;
        private HashMap<Integer, MultipartFile> mainImageMap;
        private HashMap<Integer, MultipartFile> detailImageMap;
        private String[] price;
        private String[] stock;
        private String[] state;

        @Builder
        public ProductOptionsDto(MultipartHttpServletRequest request){
            this.productName = request.getParameter("productName");
            this.optionName = request.getParameterValues("optionName");
            this.price = request.getParameterValues("price");
            this.stock = request.getParameterValues("stock");
            this.state = request.getParameterValues("state");
            List<MultipartFile> mainImages = request.getFiles("mainImage");
            String[] mainImageKeys = request.getParameterValues("mainImageKey");
            List<MultipartFile> detailImages = request.getFiles("detailImage");
            String[] detailImageKeys = request.getParameterValues("detailImageKey");
            this.mainImageMap = new HashMap<>();
            for(int i=0;i<mainImages.size();i++){
                var key = Integer.parseInt(mainImageKeys[i])-1;
                this.mainImageMap.put(key,mainImages.get(i));
            }
            this.detailImageMap = new HashMap<>();
            for(int i=0;i<detailImages.size();i++){
                var key = Integer.parseInt(detailImageKeys[i])-1;
                this.detailImageMap.put(key,detailImages.get(i));
            }
        }

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

        public void updateProduct(Products product){
            product.update(productName);
        }

        public void updateOption(int id, Product_Options option, Images mainImage, Images detailImage){
            option.update(optionName[id], mainImage, detailImage, Long.parseLong(price[id]),Long.parseLong(stock[id]),state[id]);
        }

        public int Length(){
            return optionName.length;
        }
    }

    @Getter
    static public class AccessoriesDto{
        private String[] kind;
        private String[] material;
        private String[] size;
        private String[] producer;
        private String[] madeIn;
        private String[] precautions;
        private String[] qualityAssuranceStandard;
        private String[] afterServiceAddress;

        @Builder
        public AccessoriesDto(MultipartHttpServletRequest request){
            this.kind = request.getParameterValues("kind");
            this.material = request.getParameterValues("material");
            this.size = request.getParameterValues("size");
            this.producer = request.getParameterValues("producer");
            this.madeIn = request.getParameterValues("madeIn");
            this.precautions = request.getParameterValues("precautions");
            this.qualityAssuranceStandard = request.getParameterValues("qualityAssuranceStandard");
            this.afterServiceAddress = request.getParameterValues("afterServiceAddress");
        }
    }


    @Getter
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

        @Builder
        public ClothesDto(MultipartHttpServletRequest request){
            this.material = request.getParameterValues("material");
            this.color = request.getParameterValues("color");
            this.size = request.getParameterValues("size");
            this.producer = request.getParameterValues("producer");
            this.madeIn = request.getParameterValues("madeIn");
            this.precautions = request.getParameterValues("precautions");
            this.manufacturedDate = request.getParameterValues("manufacturedDate");
            this.qualityAssuranceStandard = request.getParameterValues("qualityAssuranceStandard");
            this.afterServiceAddress = request.getParameterValues("afterServiceAddress");
        }
    }

    @Getter
    static public class FoodsDto{
        private String[] producer;
        private String[] qualityMaintenanceDate;
        private String[] importedFood;
        private String[] precaution;
        private String[] consumerCounselingPhoneNum;
        private String[] capacityByPackingUnit;

        @Builder
        public FoodsDto(MultipartHttpServletRequest request){
            this.producer = request.getParameterValues("producer");
            this.qualityMaintenanceDate = request.getParameterValues("qualityMaintenanceDate");
            this.importedFood = request.getParameterValues("importedFood");
            this.precaution = request.getParameterValues("precautions");
            this.consumerCounselingPhoneNum = request.getParameterValues("consumerCounselingPhoneNum");
            this.capacityByPackingUnit = request.getParameterValues("capacityByPackingUnit");
        }
    }

    @Getter
    static public class FrozenFoodsDto{
        private ProductOptionsDto productDto;
        private FoodsDto foodsDto;
        private String[] foodType;
        private String[] materialContent;
        private String[] nutritionalIngredients;
        private String[] geneticallyModified;

        @Builder
        public FrozenFoodsDto(MultipartHttpServletRequest request) {
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.foodsDto = FoodsDto.builder().request(request).build();
            this.foodType = request.getParameterValues("foodType");
            this.materialContent = request.getParameterValues("materialContent");
            this.nutritionalIngredients = request.getParameterValues("nutritionalIngredients");
            this.geneticallyModified = request.getParameterValues("geneticallyModified");
        }

        public FrozenFoods toInformationEntity(int id){
            return FrozenFoods.builder()
                    .producer(foodsDto.getProducer()[id])
                    .qualityMaintenanceDate(foodsDto.getQualityMaintenanceDate()[id])
                    .importedFood(foodsDto.getImportedFood()[id])
                    .precaution(foodsDto.getPrecaution()[id])
                    .consumerCounselingPhoneNum(foodsDto.getConsumerCounselingPhoneNum()[id])
                    .foodType(foodType[id])
                    .capacityByPackingUnit(foodsDto.getCapacityByPackingUnit()[id])
                    .materialContent(materialContent[id])
                    .nutritionalIngredients(nutritionalIngredients[id])
                    .geneticallyModified(geneticallyModified[id])
                    .build();
        }

        public void updateInformation(int id, FrozenFoods information){
            information.update(
                    foodType[id],
                    foodsDto.getProducer()[id],
                    foodsDto.getQualityMaintenanceDate()[id],
                    foodsDto.getCapacityByPackingUnit()[id],
                    materialContent[id],
                    nutritionalIngredients[id],
                    geneticallyModified[id],
                    foodsDto.getPrecaution()[id],
                    foodsDto.getImportedFood()[id],
                    foodsDto.getConsumerCounselingPhoneNum()[id]
            );
        }
    }

    @Getter
    static public class MeatsDto{
        private ProductOptionsDto productDto;
        private FoodsDto foodsDto;
        private String[] meatPart;
        private String[] origin;
        private String[] indication;
        private String[] composition;
        private String[] storageMethod;

        @Builder
        public MeatsDto(MultipartHttpServletRequest request){
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.foodsDto = FoodsDto.builder().request(request).build();
            this.meatPart = request.getParameterValues("meatPart");
            this.origin = request.getParameterValues("origin");
            this.indication = request.getParameterValues("indication");
            this.composition = request.getParameterValues("composition");
            this.storageMethod = request.getParameterValues("storageMethod");
        }

        public Meats toInformationEntity(int id){
            return Meats.builder()
                    .producer(foodsDto.getProducer()[id])
                    .qualityMaintenanceDate(foodsDto.getQualityMaintenanceDate()[id])
                    .capacityByPackingUnit(foodsDto.getCapacityByPackingUnit()[id])
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

        public void updateInformation(int id, Meats information){
            information.update(
                    meatPart[id],
                    foodsDto.getCapacityByPackingUnit()[id],
                    foodsDto.getProducer()[id],
                    origin[id],
                    foodsDto.getQualityMaintenanceDate()[id],
                    indication[id],
                    foodsDto.getImportedFood()[id],
                    composition[id],
                    storageMethod[id],
                    foodsDto.getPrecaution()[id],
                    foodsDto.getConsumerCounselingPhoneNum()[id]
            );
        }
    }

    @Getter
    static public class SeaFoodsDto{
        private ProductOptionsDto productDto;
        private FoodsDto foodsDto;
        private String[] foodName;
        private String[] foodType;
        private String[] materialContent;
        private String[] nutritionalIngredients;
        private String[] geneticallyModified;

        @Builder
        public SeaFoodsDto(MultipartHttpServletRequest request) {
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.foodsDto = FoodsDto.builder().request(request).build();
            this.foodName = request.getParameterValues("foodName");
            this.foodType = request.getParameterValues("foodType");
            this.materialContent = request.getParameterValues("materialContent");
            this.nutritionalIngredients = request.getParameterValues("nutritionalIngredients");
            this.geneticallyModified = request.getParameterValues("geneticallyModified");
        }

        public SeaFoods toInformationEntity(int id){
            return SeaFoods.builder()
                    .producer(foodsDto.getProducer()[id])
                    .qualityMaintenanceDate(foodsDto.getQualityMaintenanceDate()[id])
                    .importedFood(foodsDto.getImportedFood()[id])
                    .precaution(foodsDto.getPrecaution()[id])
                    .consumerCounselingPhoneNum(foodsDto.getConsumerCounselingPhoneNum()[id])
                    .foodName(foodName[id])
                    .foodType(foodType[id])
                    .capacityByPackingUnit(foodsDto.getCapacityByPackingUnit()[id])
                    .materialContent(materialContent[id])
                    .nutritionalIngredients(nutritionalIngredients[id])
                    .geneticallyModified(geneticallyModified[id])
                    .build();
        }

        public void updateInformation(int id, SeaFoods information){
            information.update(
                    foodsDto.getProducer()[id],
                    foodsDto.getQualityMaintenanceDate()[id],
                    foodsDto.getImportedFood()[id],
                    foodsDto.getPrecaution()[id],
                    foodsDto.getConsumerCounselingPhoneNum()[id],
                    foodName[id],
                    foodType[id],
                    foodsDto.getCapacityByPackingUnit()[id],
                    materialContent[id],
                    nutritionalIngredients[id],
                    geneticallyModified[id]
            );

        }
    }


    @Getter
    static public class OuterDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;

        @Builder
        public OuterDto(MultipartHttpServletRequest request){
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.clothesDto = ClothesDto.builder().request(request).build();
        }

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

        public void updateInformation(int id, Outer information){
            information.update(
                    clothesDto.getMaterial()[id],
                    clothesDto.getColor()[id],
                    clothesDto.getSize()[id],
                    clothesDto.getProducer()[id],
                    clothesDto.getMadeIn()[id],
                    clothesDto.getPrecautions()[id],
                    clothesDto.getManufacturedDate()[id],
                    clothesDto.getQualityAssuranceStandard()[id],
                    clothesDto.getAfterServiceAddress()[id]
            );
        }
    }


    @Getter
    static public class PantsDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;

        @Builder
        public PantsDto(MultipartHttpServletRequest request){
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.clothesDto = ClothesDto.builder().request(request).build();
        }

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

        public void updateInformation(int id, Pants information){
            information.update(
                    clothesDto.getMaterial()[id],
                    clothesDto.getColor()[id],
                    clothesDto.getSize()[id],
                    clothesDto.getProducer()[id],
                    clothesDto.getMadeIn()[id],
                    clothesDto.getPrecautions()[id],
                    clothesDto.getManufacturedDate()[id],
                    clothesDto.getQualityAssuranceStandard()[id],
                    clothesDto.getAfterServiceAddress()[id]
            );
        }
    }


    @Getter
    static public class SportswearDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;

        @Builder
        public SportswearDto(MultipartHttpServletRequest request){
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.clothesDto = ClothesDto.builder().request(request).build();
        }

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

        public void updateInformation(int id, Sportswear information){
            information.update(
                    clothesDto.getMaterial()[id],
                    clothesDto.getColor()[id],
                    clothesDto.getSize()[id],
                    clothesDto.getProducer()[id],
                    clothesDto.getMadeIn()[id],
                    clothesDto.getPrecautions()[id],
                    clothesDto.getManufacturedDate()[id],
                    clothesDto.getQualityAssuranceStandard()[id],
                    clothesDto.getAfterServiceAddress()[id]
            );
        }
    }


    @Getter
    static public class TopDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;

        @Builder
        public TopDto(MultipartHttpServletRequest request){
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.clothesDto = ClothesDto.builder().request(request).build();
        }

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

        public void updateInformation(int id, Top information){
            information.update(
                    clothesDto.getMaterial()[id],
                    clothesDto.getColor()[id],
                    clothesDto.getSize()[id],
                    clothesDto.getProducer()[id],
                    clothesDto.getMadeIn()[id],
                    clothesDto.getPrecautions()[id],
                    clothesDto.getManufacturedDate()[id],
                    clothesDto.getQualityAssuranceStandard()[id],
                    clothesDto.getAfterServiceAddress()[id]
            );
        }
    }


    @Getter
    static public class UnderWearDto{
        private ProductOptionsDto productDto;
        private ClothesDto clothesDto;

        @Builder
        public UnderWearDto(MultipartHttpServletRequest request){
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.clothesDto = ClothesDto.builder().request(request).build();
        }

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

        public void updateInformation(int id, UnderWear information){
            information.update(
                    clothesDto.getMaterial()[id],
                    clothesDto.getColor()[id],
                    clothesDto.getSize()[id],
                    clothesDto.getProducer()[id],
                    clothesDto.getMadeIn()[id],
                    clothesDto.getPrecautions()[id],
                    clothesDto.getManufacturedDate()[id],
                    clothesDto.getQualityAssuranceStandard()[id],
                    clothesDto.getAfterServiceAddress()[id]
            );
        }
    }


    @Getter
    static public class BagDto{
        private ProductOptionsDto productDto;
        private AccessoriesDto accessoriesDto;
        private String[] color;

        @Builder
        public BagDto(MultipartHttpServletRequest request) {
            this.productDto =ProductOptionsDto.builder().request(request).build();
            this.accessoriesDto = AccessoriesDto.builder().request(request).build();
            this.color = request.getParameterValues("color");
        }

        public Bag toInformationEntity(int id){
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
        public void updateInformation(int id, Bag information){
            information.update(
                    accessoriesDto.getKind()[id],
                    accessoriesDto.getMaterial()[id],
                    color[id],
                    accessoriesDto.getSize()[id],
                    accessoriesDto.getProducer()[id],
                    accessoriesDto.getMadeIn()[id],
                    accessoriesDto.getPrecautions()[id],
                    accessoriesDto.getQualityAssuranceStandard()[id],
                    accessoriesDto.getAfterServiceAddress()[id]
            );
        }

    }


    @Getter
    static public class WalletDto{
        private ProductOptionsDto productDto;
        private AccessoriesDto accessoriesDto;

        @Builder
        public WalletDto(MultipartHttpServletRequest request) {
            this.productDto = ProductOptionsDto.builder().request(request).build();
            this.accessoriesDto = AccessoriesDto.builder().request(request).build();
        }

        public Wallet toInformationEntity(int id){
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

        public void updateInformation(int id, Wallet information){
            information.update(
                    accessoriesDto.getKind()[id],
                    accessoriesDto.getMaterial()[id],
                    accessoriesDto.getSize()[id],
                    accessoriesDto.getProducer()[id],
                    accessoriesDto.getMadeIn()[id],
                    accessoriesDto.getPrecautions()[id],
                    accessoriesDto.getQualityAssuranceStandard()[id],
                    accessoriesDto.getAfterServiceAddress()[id]
            );
        }
    }
}
