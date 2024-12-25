//package com.SocialNet.SocialNetwork.Service;
//
//import com.SocialNet.SocialNetwork.DTO.JwtAuthenticationDTO;
//import com.SocialNet.SocialNetwork.DTO.RefreshTokenDTO;
//import com.SocialNet.SocialNetwork.DTO.UserCredentialDTO;
//import com.SocialNet.SocialNetwork.DTO.UserDTO.CreateUserDTO;
//import com.SocialNet.SocialNetwork.DTO.UserDTO.ShortUserInfoDTO;
//import com.SocialNet.SocialNetwork.DTO.UserDTO.UpdateUserDTO;
//import com.SocialNet.SocialNetwork.Entites.User;
//import com.SocialNet.SocialNetwork.Repository.UserRepository;
//import com.SocialNet.SocialNetwork.Security.JWTService;
//import io.jsonwebtoken.Jwt;
//import org.springframework.beans.factory.annotation.Autowired;
//import jakarta.transaction.Transactional;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.naming.AuthenticationException;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class UserService /*implements com.SocialNet.SocialNetwork.Security.UserService*/ {
//
//    private final UserRepository userRepository;
//    private final JWTService jwtService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserService(UserRepository userRepository, JWTService jwtService, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.jwtService = jwtService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public List<ShortUserInfoDTO> getUsers() {
//        List<User> users = userRepository.findAll();
//
//        return users.stream()
//                .map(user -> {
//                    ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
//                    userDTO.setUser_id(user.getId());
//                    userDTO.setUsername(user.getUsername());
//                    userDTO.setEmail(user.getEmail());
//                    userDTO.setFirstName(user.getFirstName());
//                    userDTO.setLastName(user.getLastName());
//                    userDTO.setPassword(user.getPassword());
//                    userDTO.setImageu(user.getImageu());
//
//                    return userDTO;
//                }).toList();
//    }
//
//    public ShortUserInfoDTO getUserById(int id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("User not found"));
//
//        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
//        userDTO.setUser_id(user.getId());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setFirstName(user.getFirstName());
//        userDTO.setLastName(user.getLastName());
//        userDTO.setLastName(user.getLastName());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setImageu(user.getImageu());
//
//        return userDTO;
//    }
//
//    public ShortUserInfoDTO createUser(CreateUserDTO createUserDTO) {
//        User user = new User();
//        user.setUsername(createUserDTO.getUsername());
//        user.setPassword(createUserDTO.getPassword());
//        user.setEmail(createUserDTO.getEmail());
//        user.setFirstName(createUserDTO.getFirstName());
//        user.setLastName(createUserDTO.getLastName());
//        user.setImageu(createUserDTO.getImageu());
//
//        User savedUser = userRepository.save(user);
//
//        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
//        userDTO.setUser_id(savedUser.getId());
//        userDTO.setUsername(savedUser.getUsername());
//        userDTO.setEmail(savedUser.getEmail());
//        userDTO.setFirstName(savedUser.getFirstName());
//        userDTO.setLastName(savedUser.getLastName());
//        userDTO.setImageu(savedUser.getImageu());
//
//        return userDTO; // Возвращаем DTO с созданным пользователем
//    }
//
//    public void updateUser(int id, UpdateUserDTO updateUserDTO) {
//        User userToUpdate = userRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));
//
//        if (updateUserDTO.getUsername() != null) {
//            userToUpdate.setUsername(updateUserDTO.getUsername());
//        }
//        if (updateUserDTO.getPassword() != null) {
//            userToUpdate.setPassword(updateUserDTO.getPassword());
//        }
//        if (updateUserDTO.getEmail() != null) {
//            userToUpdate.setEmail(updateUserDTO.getEmail());
//        }
//        if (updateUserDTO.getFirstName() != null) {
//            userToUpdate.setFirstName(updateUserDTO.getFirstName());
//        }
//        if (updateUserDTO.getLastName() != null) {
//            userToUpdate.setLastName(updateUserDTO.getLastName());
//        }
//        if (updateUserDTO.getImageu() != null) {
//            userToUpdate.setImageu(updateUserDTO.getImageu());
//        }
//
//        userRepository.save(userToUpdate);
//    }
//
//    public void deleteUser(int id) {
//        User userToDelete = userRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));
//
//        userRepository.delete(userToDelete);
//    }
//
//    //Все по видосам
//
//    public String addUser(CreateUserDTO createUserDTO) {
//        User user = new User();
//        user.setUsername(createUserDTO.getUsername());
//        user.setEmail(createUserDTO.getEmail());
//        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
//
//        userRepository.save(user);
//        return "User successfully added";
//    }
//
//    public JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
//        User user = findByCredentials(userCredentialDTO);
//        return  jwtService.generateJwtAuthToken(user.getEmail());
//    }
//
//    public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
//        String refreshToken = refreshTokenDTO.getRefreshToken();
//        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
//            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
//            return  jwtService.refreshBaseToken(user.getEmail(), refreshToken);
//        }
//        throw new AuthenticationException("Invalid refresh token");
//    }
//
//    private User findByCredentials(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
//        Optional<User> optionalUser = userRepository.findByEmail(userCredentialDTO.getEmail());
//        if(optionalUser.isPresent()){
//            User user = optionalUser.get();
//            if(passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword())){
//                return user;
//            }
//        }
//        throw new AuthenticationException("Email or password is not correct");
//    }
//
//    private User findByEmail(String email) throws Exception {
//        return userRepository.findByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
//    }
//
//    /*@Override
//    public JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
//        User user = findByCredentials(userCredentialDTO);
//
//        return jwtService.generateJwtAuthToken(user.getEmail());
//    }
//
//    @Override
//    public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
//        String refreshToken = refreshTokenDTO.getRefreshToken();
//        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
//            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
//            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
//        }
//        throw new AuthenticationException("Invalid refresh token");
//    }*/
//
//    /*@Override
//    public ShortUserInfoDTO getUserById(String id) {
//        return null;
//    }
//
//    @Override
//    public ShortUserInfoDTO getUserByEmail(String email) {
//        return null;
//    }
//
//    @Override
//    public String AddUser(CreateUserDTO user2) {
//        User user = new User();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "User Added"; //Очень много сомнений
//    }*/
//
////    private User findByCredentials(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
////        Optional<User> optionalUser = userRepository.findByEmail(userCredentialDTO.getEmail());
////        if(optionalUser.isPresent()) {
////            User user = optionalUser.get();
////            if(passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword())) {
////                return user;
////            }
////        }
////        throw new AuthenticationException("Email or password is not corrected");
////
////    }
////
////    private User findByEmail(String email) throws Exception {
////        return userRepository.findByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
////    }
//
//}

package com.SocialNet.SocialNetwork.Service;

import com.SocialNet.SocialNetwork.DTO.JwtAuthenticationDTO;
import com.SocialNet.SocialNetwork.DTO.RefreshTokenDTO;
import com.SocialNet.SocialNetwork.DTO.UserCredentialDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.CreateUserDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.ShortUserInfoDTO;
import com.SocialNet.SocialNetwork.DTO.UserDTO.UpdateUserDTO;
import com.SocialNet.SocialNetwork.Entites.User;
import com.SocialNet.SocialNetwork.Repository.UserRepository;
import com.SocialNet.SocialNetwork.Security.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ShortUserInfoDTO> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToShortUserInfoDTO)
                .toList();
    }

    public ShortUserInfoDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return convertToShortUserInfoDTO(user);
    }

    public ShortUserInfoDTO createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword())); // Шифрование пароля
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setImageu(createUserDTO.getImageu());

        User savedUser = userRepository.save(user);
        return convertToShortUserInfoDTO(savedUser);
    }

    public void updateUser(int id, UpdateUserDTO updateUserDTO) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

        if (updateUserDTO.getUsername() != null) {
            userToUpdate.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getPassword() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(updateUserDTO.getPassword())); // Шифрование пароля
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

    public JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
        User user = findByCredentials(userCredentialDTO);
        return jwtService.generateJwtAuthToken(user.getEmail());
    }

    public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
        String refreshToken = refreshTokenDTO.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    private ShortUserInfoDTO convertToShortUserInfoDTO(User user) {
        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
        userDTO.setUser_id(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setImageu(user.getImageu());
        return userDTO;
    }

    private User findByCredentials(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
    }
}