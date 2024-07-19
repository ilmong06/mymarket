package com.cod.mymarket.base.initData;

import com.cod.mymarket.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevInitData implements BeforeInitData {
    @Bean
    CommandLineRunner initData(MemberService memberService) {
        return args -> {
            beforeInit();

            // member init
            String password = "1234";
            memberService.join("admin", password, "admin@test.com", "admin","asdf","null");
            memberService.join("user1", password, "user1@test.com", "user1","asdf","null");



        };
    }
}
