package com.cod.mymarket.base.initData;

import com.cod.mymarket.member.service.MemberService;
import com.cod.mymarket.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
            productService.create("타이틀1", "1 설명입니다.", 100000);
            productService.create("타이틀2", "2 설명입니다.", 20000);
            productService.create("타이틀3", "3 설명입니다.", 3000000);
            productService.create("타이틀4", "4 설명입니다.", 40000);

        };
    }
}
