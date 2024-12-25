package com.SocialNet.SocialNetwork.DTO;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {

    private String token;
    private String refreshToken;

}
