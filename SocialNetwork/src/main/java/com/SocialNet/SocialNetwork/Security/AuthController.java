package com.SocialNet.SocialNetwork.Security;

import com.SocialNet.SocialNetwork.DTO.JwtAuthenticationDTO;
import com.SocialNet.SocialNetwork.DTO.RefreshTokenDTO;
import com.SocialNet.SocialNetwork.DTO.UserCredentialDTO;
import com.SocialNet.SocialNetwork.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody UserCredentialDTO userCredentialDTO) {
        try{
            JwtAuthenticationDTO jwtAuthenticationDTO = userService.signIn(userCredentialDTO);
            return ResponseEntity.ok(jwtAuthenticationDTO);
        }
        catch(AuthenticationException e){
            throw new RuntimeException("Authentication failed" + e.getCause());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDTO refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        return userService.refreshToken(refreshTokenDTO);
    }

}
