package com.SocialNet.SocialNetwork.Controller;

import com.SocialNet.SocialNetwork.Service.UserService;
import com.SocialNet.SocialNetwork.DTO.UserDTO.CreateUserDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.ShortUserInfoDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.UpdateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<ShortUserInfoDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ShortUserInfoDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<ShortUserInfoDTO> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        ShortUserInfoDTO createdUser = userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // Возвращаем созданного пользователя
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            userService.updateUser(id, updateUserDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}