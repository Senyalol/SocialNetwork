package com.SocialNet.SocialNetwork.Controller;

import com.SocialNet.SocialNetwork.Service.UserService;
import com.SocialNet.SocialNetwork.DTO.UserDTO.CreateUserDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.ShortUserInfoDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.UpdateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

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
    public ResponseEntity<ShortUserInfoDTO> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<ShortUserInfoDTO> addUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        ShortUserInfoDTO createdUser = userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
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
            return handleError(e);
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
            return handleError(e);
        }
    }

    // Метод для обработки ошибок
    private ResponseEntity<Void> handleError(Exception e) {
        // Логика логирования ошибки
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}