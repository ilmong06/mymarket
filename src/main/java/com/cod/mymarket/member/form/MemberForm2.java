package com.cod.mymarket.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberForm2 {

    @NotBlank
    private String address;
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
}
