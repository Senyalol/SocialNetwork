//package com.SocialNet.SocialNetwork.Security;
//
//import com.SocialNet.SocialNetwork.DTO.JwtAuthenticationDTO;
//import com.SocialNet.SocialNetwork.DTO.RefreshTokenDTO;
//import com.SocialNet.SocialNetwork.DTO.UserCredentialDTO;
//import com.SocialNet.SocialNetwork.DTO.UserDTO.CreateUserDTO;
//import com.SocialNet.SocialNetwork.DTO.UserDTO.ShortUserInfoDTO;
//
//import javax.naming.AuthenticationException;
//
////import javax.swing.text.ChangedCharSetException;
//
//public interface UserService {
//
//    JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException;
//    JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;
//
//    ShortUserInfoDTO getUserById(String id);
//    ShortUserInfoDTO getUserByEmail(String email);
//    String AddUser(CreateUserDTO user);
//}
