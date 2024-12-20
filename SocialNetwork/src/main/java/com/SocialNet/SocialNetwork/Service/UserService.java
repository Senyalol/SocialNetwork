package com.SocialNet.SocialNetwork.Service;

import com.SocialNet.SocialNetwork.DTO.UserDTO.CreateUserDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.ShortUserInfoDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.UpdateUserDTO;
import com.SocialNet.SocialNetwork.Entites.User;
import com.SocialNet.SocialNetwork.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ShortUserInfoDTO> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
                    userDTO.setUser_id(user.getId());
                    userDTO.setUsername(user.getUsername());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setPassword(user.getPassword());
                    userDTO.setImageu(user.getImageu());

                    return userDTO;
                }).toList();
    }

    public ShortUserInfoDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
        userDTO.setUser_id(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setImageu(user.getImageu());

        return userDTO;
    }

    public ShortUserInfoDTO createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setImageu(createUserDTO.getImageu());

        User savedUser = userRepository.save(user);

        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
        userDTO.setUser_id(savedUser.getId());
        userDTO.setUsername(savedUser.getUsername());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setFirstName(savedUser.getFirstName());
        userDTO.setLastName(savedUser.getLastName());
        userDTO.setImageu(savedUser.getImageu());

        return userDTO; // Возвращаем DTO с созданным пользователем
    }

    public void updateUser(int id, UpdateUserDTO updateUserDTO) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

        if (updateUserDTO.getUsername() != null) {
            userToUpdate.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getPassword() != null) {
            userToUpdate.setPassword(updateUserDTO.getPassword());
        }
        if (updateUserDTO.getEmail() != null) {
            userToUpdate.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getFirstName() != null) {
            userToUpdate.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            userToUpdate.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getImageu() != null) {
            userToUpdate.setImageu(updateUserDTO.getImageu());
        }

        userRepository.save(userToUpdate);
    }

    public void deleteUser(int id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

        userRepository.delete(userToDelete);
    }
}