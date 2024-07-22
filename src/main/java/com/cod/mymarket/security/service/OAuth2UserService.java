package com.cod.mymarket.security.service;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.repository.MemberRepository;
import com.cod.mymarket.security.dto.MemberContext;
import com.cod.mymarket.security.exception.OAuthTypeMatchNotFoundException;
import com.cod.mymarket.security.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String oauthId = oAuth2User.getName();

        Member member = null;
        String oauthType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        switch (oauthType) {
            case "KAKAO" -> {
                if (isNew(oauthType, oauthId)) {
                    Map attributesProperties = (Map) attributes.get("properties");
                    Map attributesKakaoAcount = (Map) attributes.get("kakao_account");
                    String nickname = (String) attributesProperties.get("nickname");
                    String username = "KAKAO_%s".formatted(oauthId);

                    member = Member.builder()
                            .username(username)
                            .password("")
                            .email("kakao@test.com")
                            .nickname(nickname)
                            .build();

                    memberRepository.save(member);
                } else {
                    member = memberRepository.findByUsername("%s_%s".formatted(oauthType, oauthId))
                            .orElseThrow(MemberNotFoundException::new);
                }
            }
            case "GOOGLE" -> {
                if (isNew(oauthType, oauthId)) {
                    String email = (String) attributes.get("email");
                    String username = "GOOGLE_%s".formatted(oauthId);
                    String nickname = (String) attributes.get("name");

                    member = Member.builder()
                            .username(username)
                            .password("")
                            .email(email)
                            .nickname(nickname)
                            .build();

                    memberRepository.save(member);
                } else {
                    member = memberRepository.findByUsername("%s_%s".formatted(oauthType, oauthId))
                            .orElseThrow(MemberNotFoundException::new);
                }
            }
            case "NAVER" -> {
                if (isNew(oauthType, oauthId)) {
                    Map response = (Map) attributes.get("response");
                    String email = (String) response.get("email");
                    String username = "NAVER_%s".formatted(oauthId);
                    String nickname = (String) response.get("nickname");

                    member = Member.builder()
                            .username(username)
                            .password("")
                            .email(email)
                            .nickname(nickname)
                            .build();

                    memberRepository.save(member);
                } else {
                    member = memberRepository.findByUsername("%s_%s".formatted(oauthType, oauthId))
                            .orElseThrow(MemberNotFoundException::new);
                }
            }
            default -> throw new OAuthTypeMatchNotFoundException();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("member"));
        return new MemberContext(member, authorities, attributes, userNameAttributeName);
    }

    private boolean isNew(String oAuthType, String oAuthId) {
        return memberRepository.findByUsername("%s_%s".formatted(oAuthType, oAuthId)).isEmpty();
    }
}