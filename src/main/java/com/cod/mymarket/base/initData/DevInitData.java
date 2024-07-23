package com.cod.mymarket.base.initData;

import com.cod.mymarket.member.service.MemberService;
import com.cod.mymarket.product.entity.ProductType;
import com.cod.mymarket.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;


@Configuration
@Profile("dev")
public class DevInitData implements BeforeInitData {
    @Bean
    CommandLineRunner initData(MemberService memberService, ProductService productService) {
        return args -> {
            beforeInit();

            // member init
            String password = "1234";
            memberService.join("admin", password, "admin@test.com", "admin","asdf","null");
            memberService.join("user1", password, "user1@test.com", "user1","asdf","null");



            // product init
            productService.create("타이틀1", "1 TOP 설명입니다.", 25000, ProductType.TOP, "product/product1.jpg", List.of("product/product1.jpg","product/product1.jpg"), null,12,"은은한컬러_주문폭주",  List.of("product/color-gray.jpg","product/color-red.jpg") ,"product/new.jpg");
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

        };
    }
}