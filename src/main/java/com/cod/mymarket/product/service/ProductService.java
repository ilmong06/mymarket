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

    public void create(String title, String description, int price, MultipartFile thumbnail, ProductType type) {
        String thumbnailRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(genFileDirPath + "/" + thumbnailRelPath);

        thumbnailFile.getParentFile().mkdirs();

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Product p = Product.builder()
                .title(title)
                .description(description)
                .price(price)
                .thumbnailImg(thumbnailRelPath)
                .productType(type)
                .build();
        productRepository.save(p);
    }

    public void create(String title, String description, int price, ProductType type,String thumbnailRelPath) {
        Product p = Product.builder()
                .title(title)
                .description(description)
                .price(price)
                .thumbnailImg(thumbnailRelPath)
                .productType(type)
                .build();
        productRepository.save(p);
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