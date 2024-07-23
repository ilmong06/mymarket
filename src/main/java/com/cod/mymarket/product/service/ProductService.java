package com.cod.mymarket.product.service;

import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.entity.ProductType;
import com.cod.mymarket.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    public Page<Product> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));

        return productRepository.findAllByKeyword(kw, pageable);
    }

    public void create(String title, String description, int price, MultipartFile thumbnail, List<MultipartFile> detailImgs, ProductType type, String option, int point,String details,List<MultipartFile> colors,MultipartFile detailicon) {
       //썸네일
        String thumbnailRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(genFileDirPath + "/" + thumbnailRelPath);

        thumbnailFile.getParentFile().mkdirs();

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 상세이미지
        List<String> detailImgRelPaths = new ArrayList<>();
        for (MultipartFile detailImg : detailImgs) {
            String detailImgRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
            File detailImgFile = new File(genFileDirPath + "/" + detailImgRelPath);
            detailImgFile.getParentFile().mkdirs();

            try {
                detailImg.transferTo(detailImgFile);
                detailImgRelPaths.add(detailImgRelPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //컬러
        List<String> colorRelPaths = new ArrayList<>();
        for (MultipartFile color : colors) {
            String colorRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
            File colorFile = new File(genFileDirPath + "/" + colorRelPath);
            colorFile.getParentFile().mkdirs();

            try {
                color.transferTo(colorFile);
                colorRelPaths.add(colorRelPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //디테일아이콘
        String detailiconRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
        File detailiconFile = new File(genFileDirPath + "/" + detailiconRelPath);

        detailiconFile.getParentFile().mkdirs();

        try {
            detailicon.transferTo(detailiconFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        int calculatedPoint = calculatePoint(price);

        Product p = Product.builder()
                .title(title)
                .description(description)
                .price(price)
                .thumbnailImg(thumbnailRelPath)
                .detailImgs(detailImgRelPaths)
                .productType(type)
                .point(calculatedPoint)
                .option(option)
                .details(details)
                .colorImages(colorRelPaths)
                .detailicon(detailiconRelPath)
                .build();
        productRepository.save(p);
    }

    public void create(String title, String description, int price, ProductType type, String thumbnailRelPath, List<String> detailImgRelPaths,String option, int point,String details,List<String> colorRelPaths,String detailiconRelPath) {
        int calculatedPoint = calculatePoint(price);

        Product p = Product.builder()
                .title(title)
                .description(description)
                .price(price)
                .thumbnailImg(thumbnailRelPath)
                .detailImgs(detailImgRelPaths)
                .productType(type)
                .point(calculatedPoint)
                .details(details)
                .colorImages(colorRelPaths)
                .detailicon(detailiconRelPath)
                .build();
        productRepository.save(p);
    }

    private int calculatePoint(int price) {
        return (int) (price * 0.003);
    }

    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public List<Product> getList() {
        return productRepository.findAll();
    }

    // 특정 ProductType의 상품만 반환
    public Page<Product> getByProductType(int page, ProductType type) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
        return productRepository.findAllByProductType(type, pageable);
    }
}