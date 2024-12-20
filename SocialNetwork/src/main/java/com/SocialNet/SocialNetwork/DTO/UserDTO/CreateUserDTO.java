package com.SocialNet.SocialNetwork.DTO.UserDTO;
import lombok.Data;

@Data
public class CreateUserDTO {
    private Integer user_id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String imageu;
}
