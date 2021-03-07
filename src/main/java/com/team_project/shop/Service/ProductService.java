package com.team_project.shop.Service;

import com.team_project.shop.domain.product.*;
import com.team_project.shop.domain.product.Informations.Informations;
import com.team_project.shop.domain.product.Informations.InformationsRepository;
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
    private final InformationsRepository informationsRepository;

    @Transactional
    public Category findCategory(String categoryName){
        return categoryRepository.findByName(categoryName);
    }

    @Transactional
    public void saveProduct(Products product){
        productsRepository.save(product);
    }

    @Transactional
    public void saveOption(Product_Options option){
        optionsRepository.save(option);
    }

    @Transactional
    public void saveInformation(Informations inform) { informationsRepository.save(inform); }

    //Controller에서 처리할 예정.
//    @Transactional
//    public Long update(Long productId, MultipartHttpServletRequest request){
//        //productId는 URL을 통해 받는다.
//        Products product = productsRepository.findById(productId).get();
//        product.update(request.getParameter("productName"));
//
//        List<Product_Options> options = optionsRepository.findAllByProductId(productId);
//        //수정가능한 목록: 상품명, 상품 옵션, 검색어, 대표이미지, 상품 기본 정보, 판매상태
//        Category LeafCategory = categoryRepository.findByName(request.getParameter("CategoryName"));
//
//        String[] optionNames = request.getParameterValues("optionName");
//        List<MultipartFile> mainImages = request.getFiles("mainImage");
//        List<MultipartFile> detailImages = request.getFiles("detailImage");
//        String[] optionPrices = request.getParameterValues("price");
//        String[] optionStocks = request.getParameterValues("stock");
//        String[] optionState = request.getParameterValues("optionState");
//
//        for (int i=0; i<options.size();i++){
//            Product_Options option = options.get(i);
//            option.update(optionNames[i],
//                    updateImage(option.getMainImage(),mainImages.get(i),i+"_main.jpg",product),
//                    updateImage(option.getDetailImage(),detailImages.get(i),i+"_detail.jpg",product),
//                    Long.parseLong(optionPrices[i]),
//                    Long.parseLong(optionStocks[i]),
//                    optionState[i]);
//        }
//
//        if (options.size()<optionNames.length) {
//            int i = optionNames.length-1;
//            Product_Options option = Product_Options.builder()
//                    .optionName(optionNames[i])
//                    .product(product)
//                    .mainImage(saveImage(mainImages.get(i),i+"_main.jpg",product))
//                    .detailImage(saveImage(detailImages.get(i),i+"_detail.jpg",product))
//                    .price(Long.parseLong(optionPrices[i]))
//                    .stock(Long.parseLong(optionStocks[i]))
//                    .build();
//            optionsRepository.save(option);
//        }
//        return product.getId();
//    }

    /* 상품 조회 함수
    * 상세 페이지에서 옵션을 선택하면 옵션에 맞는 정보가 출력된다.
    * 1. 미리 모든 옵션 정보를 받아 hidden을 이용하여 구현
    * 2. 옵션 선택시 Controller를 통해 리다이렉션
    * */



    public Images saveImage(MultipartFile file, String fileName,Products product){
        String path = System.getProperty("user.dir") + "\\bin\\main\\static\\images\\" + product.getId().toString();
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
        writeImage(file,path,fileName);
        Images image = Images.builder()
                .imageName(fileName)
                .imageURL("/images/"+product.getId().toString()+"/" +fileName)
                .build();
        imagesRepository.save(image);

        return image;
    }

    private Images updateImage(Images image, MultipartFile file, String fileName,Products product){
        String path = System.getProperty("user.dir")
                + "\\bin\\main\\static\\images\\"
                + product.getId().toString();
        deleteImage(System.getProperty("user.dir")+"/bin/main/static"+ image.getImageURL(),image);
        writeImage(file,path,fileName);
        image.update(fileName,"/images/"+product.getId().toString()+"/" +fileName);
        return image;
    }

    private void deleteImage(String URL, Images image){
        File File = new File(URL);
        if(File.exists()) {
            if(File.delete()) {
                System.out.println("삭제 성공");
            }
            else {
                System.out.println("삭제 실패");
            }
        }
        else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }

    private void writeImage(MultipartFile file, String path, String fileName){
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
    }

}
