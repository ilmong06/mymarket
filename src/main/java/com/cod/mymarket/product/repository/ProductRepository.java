package com.cod.mymarket.product.repository;

import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.entity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query("""
            select distinct p
            from Product p
            where p.title like %:kw%
            or p.description like %:kw%
            """)
    Page<Product> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

    Page<Product> findAllByProductType(ProductType productType, Pageable pageable);
    List<Product> findAllByIdIn(List<Long> ids);

    @Query("SELECT p FROM Product p ORDER BY p.createdDate DESC")
    List<Product> findTopByOrderByCreatedDateDesc(@Param("limit") int limit);
    @Query("SELECT p FROM Product p ORDER BY p.createdDate DESC")
    List<Product> findLatestProducts(Pageable pageable);
    @Query("SELECT p FROM Product p ORDER BY p.hitCount DESC")
    Page<Product> findAllByHitCountDesc(Pageable pageable);

    Page<Product> findAllByDiscountedPriceNotNull(Pageable pageable);
}