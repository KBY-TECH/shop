package com.team_project.shop.Service;

import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ImagesRepository imagesRepository;
    private final Product_OptionsRepository optionsRepository;
    private final ProductsRepository productsRepository;

    @Transactional
    public Long save(Users user, MultipartHttpServletRequest request){
        Category LeafCategory = categoryRepository.findByName(request.getParameter("CategoryName"));
        Products product = Products.builder()
                .user(user)
                .name(request.getParameter("productName"))
                .category(LeafCategory)
                .build();
        productsRepository.save(product);

        String[] optionNames = request.getParameterValues("optionName");
        String[] optionPrices = request.getParameterValues("price");
        String[] optionStocks = request.getParameterValues("stock");
        List<MultipartFile> mainImages = request.getFiles("mainImage");
        List<MultipartFile> detailImages = request.getFiles("detailImage");
        for(int i=0; i<optionNames.length; i++){
            Product_Options option = Product_Options.builder()
                    .optionName(optionNames[i])
                    .product(product)
                    .mainImage(saveMainImage(mainImages.get(i),optionNames[i],product))
                    .detailImage(saveDetailImage(detailImages.get(i),optionNames[i],product))
                    .price(Long.parseLong(optionPrices[i]))
                    .stock(Long.parseLong(optionStocks[i]))
                    .build();

            optionsRepository.save(option);
        }

        return product.getId();
    }

//    @Transactional
//    public Long update(Long productId, MultipartHttpServletRequest request){
//        Products product = productsRepository.findById(productId).get();
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보
//
//    }
//
//    @Transactional
//    public Long StopSelling(Long productId){
//        //상품 판매중지시에는 상품 데이터를 삭제하지 않는다.
//        //대부분의 쇼핑몰에서 모든 옵션이 품절 상태인 채로 일정 시간 경과되면 완전 삭제됨
//
//
//    }

    private Images saveMainImage(MultipartFile file, String optionName, Products product){
        return saveImage(file,optionName + "_main.jpg",product);
    }

    private Images saveDetailImage(MultipartFile file, String optionName, Products product){
        return saveImage(file,optionName+"_detail.jpg",product);
    }


    private Images saveImage(MultipartFile file, String fileName,Products product){
        String path = System.getProperty("user.dir") + "\\bin\\main\\static\\images\\" + product.getId().toString();
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String fURL = path + "\\" + fileName;
        File destFile = new File(fURL);
        BufferedImage bImage;
        try {
            bImage = ImageIO.read(file.getInputStream());
            BufferedImage result = new BufferedImage(bImage.getWidth(), bImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(bImage, 0, 0, Color.WHITE,null);
            ImageIO.write(result, "jpg", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Images image = Images.builder()
                .imageName(fileName)
                .imageURL("/images//"+product.getId().toString()+"/" +fileName)
                .build();
        imagesRepository.save(image);
        return image;
    }

}
