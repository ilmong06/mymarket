package com.cod.mymarket.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberForm {
    @Size(min = 1, max = 25)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String address;
    private String coupons;
}
