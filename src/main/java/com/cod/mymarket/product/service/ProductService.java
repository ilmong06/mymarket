package com.cod.mymarket.product.service;

import com.cod.mymarket.product.bottom.entity.Bottom;
import com.cod.mymarket.product.entity.*;
import com.cod.mymarket.product.repository.ProductRepository;
import com.cod.mymarket.product.top.entity.Top;
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

    public void create(String title, String description, int price, MultipartFile thumbnail, String type) {
        String thumbnailRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(genFileDirPath + "/" + thumbnailRelPath);

        thumbnailFile.getParentFile().mkdirs();

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Product product;
        switch (type.toUpperCase()) {
            case "TOP":
                product = Top.builder()
                        .title(title)
                        .description(description)
                        .price(price)
                        .thumbnailImg(thumbnailRelPath)
                        .build();
                break;
            case "BOTTOM":
                product = Bottom.builder()
                        .title(title)
                        .description(description)
                        .price(price)
                        .thumbnailImg(thumbnailRelPath)
                        .build();
                break;

            default:
                throw new IllegalArgumentException("Invalid product type: " + type);
        }

        productRepository.save(product);
    }

    public void create(String title, String description, int price, String type) {
        Product product;
        switch (type.toUpperCase()) {
            case "TOP":
                product = Top.builder()
                        .title(title)
                        .description(description)
                        .price(price)
                        .thumbnailImg("product/product1.jpg")
                        .build();
                break;
            case "BOTTOM":
                product = Bottom.builder()
                        .title(title)
                        .description(description)
                        .price(price)
                        .thumbnailImg("product/product1.jpg")
                        .build();
                break;

            default:
                throw new IllegalArgumentException("Invalid product type: " + type);
        }

        productRepository.save(product);
    }

    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        return product.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getList() {
        return productRepository.findAll();
    }
    public Page<Product> getProductsByType(String productType, Pageable pageable) {
        Page<Product> products = productRepository.findByProductType(productType, pageable);

        // productType 필드를 설정
        products.forEach(product -> product.setProductType(productType));

        return products;
    }
}

