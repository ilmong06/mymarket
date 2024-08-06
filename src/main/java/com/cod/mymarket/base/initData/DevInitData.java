package com.cod.mymarket.base.initData;

import com.cod.mymarket.coupon.entity.Coupon;
import com.cod.mymarket.coupon.service.CouponService;
import com.cod.mymarket.member.service.MemberService;
import com.cod.mymarket.product.entity.ProductType;
import com.cod.mymarket.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Configuration
@Profile("dev")
public class DevInitData implements BeforeInitData {
    private final CouponService couponService;

    public DevInitData(CouponService couponService) {
        this.couponService = couponService;
    }

    @Bean
    CommandLineRunner initData(MemberService memberService, ProductService productService) {
        return args -> {
            beforeInit();
            // Create and save coupons
            Coupon coupon1 = couponService.createCoupon("WELCOME10", BigDecimal.valueOf(0.10), LocalDate.now().plusDays(30));
            Coupon coupon2 = couponService.createCoupon("SUMMER20", BigDecimal.valueOf(0.20), LocalDate.now().plusDays(60));
            Coupon coupon3 = couponService.createCoupon("FALL15", BigDecimal.valueOf(0.15), LocalDate.now().plusDays(90));


            // member init
            String password = "1234";
            memberService.join("admin", password, "admin@test.com", "admin","asdf","WELCOME10");
            memberService.join("user1", password, "user1@test.com", "user1","asdf","WELCOME10");


            // Add coupons to members
            memberService.addCouponToMember("admin", coupon1.getId());
            memberService.addCouponToMember("admin", coupon2.getId());
            memberService.addCouponToMember("user1", coupon1.getId());
            memberService.addCouponToMember("user1", coupon3.getId());
            // product init
            productService.create("타이틀1", "1 TOP 설명입니다.", 25000, ProductType.TOP, "product/product1.jpg", List.of("product/product1.jpg","product/product1.jpg"), List.of("s","d"),12,"은은한컬러_주문폭주",  List.of("product/color-gray.jpg","product/color-red.jpg") ,"product/new.jpg");
            productService.create("타이틀2", "2 BOTTOM 설명입니다.", 20000, ProductType.BOTTOM, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀3", "3 DRESS 설명입니다.", 3000000, ProductType.DRESS, "product/product1.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀4", "4 OUTER 설명입니다.", 40000, ProductType.OUTER, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");

            productService.create("타이틀5", "5 TOP 설명입니다.", 100000, ProductType.TOP, "product/product1.jpg",List.of("product/product1.jpg"), null,12,"#고객감사_이벤트 #베스트제품",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀6", "6 BOTTOM 설명입니다.", 20000, ProductType.BOTTOM, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀7", "7 DRESS 설명입니다.", 3000000, ProductType.DRESS, "product/product1.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀8", "8 OUTER 설명입니다.", 40000, ProductType.OUTER, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");

            productService.create("타이틀9", "9 TOP 설명입니다.", 100000, ProductType.TOP, "product/product1.jpg", List.of("product/product1.jpg") ,null,12,"#고퀄리티 #md소장",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀10", "10 BOTTOM 설명입니다.", 20000, ProductType.BOTTOM, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀11", "11 DRESS 설명입니다.", 3000000, ProductType.DRESS, "product/product1.jpg",List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀12", "12 OUTER 설명입니다.", 40000, ProductType.OUTER, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");

            productService.create("타이틀13", "13 TOP 설명입니다.", 100000, ProductType.TOP, "product/product1.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀14", "14 BOTTOM 설명입니다.", 20000, ProductType.BOTTOM, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀15", "15 DRESS 설명입니다.", 3000000, ProductType.DRESS, "product/product1.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.create("타이틀16", "16 OUTER 설명입니다.", 40000, ProductType.OUTER, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");

            productService.create("타이틀17", "4 BAG&SHOES 설명입니다.", 40000, ProductType.BS, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");

            productService.createSaleProduct("타이틀18", "4 BAG&SHOES 설명입니다.", 40000, 23,ProductType.TOP, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.createSaleProduct("타이틀19", "4 BAG&SHOES 설명입니다.", 40000, 23,ProductType.BOTTOM, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.createSaleProduct("타이틀20", "4 BAG&SHOES 설명입니다.", 40000, 23,ProductType.DRESS, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.createSaleProduct("타이틀21", "4 BAG&SHOES 설명입니다.", 40000, 23,ProductType.OUTER, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.createSaleProduct("타이틀22", "4 BAG&SHOES 설명입니다.", 40000, 23,ProductType.BS, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");
            productService.createSaleProduct("타이틀23", "4 BAG&SHOES 설명입니다.", 40000, 23,ProductType.TOP, "product/product2.jpg", List.of("product/product1.jpg"), null,12,"은은한컬러_주문폭주",List.of("product/color-gray.jpg") ,"product/new.jpg");

        };
    }
}