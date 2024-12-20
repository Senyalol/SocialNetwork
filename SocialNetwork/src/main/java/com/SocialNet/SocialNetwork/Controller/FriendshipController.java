package com.SocialNet.SocialNetwork.Controller;

import com.SocialNet.SocialNetwork.Service.FriendshipService;
import com.SocialNet.SocialNetwork.DTO.FriendshipDTO.CreateFriendshipDTO;
import com.SocialNet.SocialNetwork.DTO.FriendshipDTO.ShortFriendshipDTO;
import com.SocialNet.SocialNetwork.DTO.FriendshipDTO.UpdateFriendshipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;
import java.util.List;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping
    public List<ShortFriendshipDTO> getAllFriendships() {
        return friendshipService.getFriendships();
    }

    @GetMapping("/{id}")
    public ShortFriendshipDTO getFriendshipById(@PathVariable int id) {
        return friendshipService.getFriendshipById(id);
    }

    @PostMapping
    public void createFriendship(@RequestBody CreateFriendshipDTO createFriendshipDTO) {
        friendshipService.createFriendship(createFriendshipDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateFriendship(@PathVariable("id") int id, @Valid @RequestBody UpdateFriendshipDTO updateFriendshipDTO) {
        try {
            friendshipService.updateFriendship(id, updateFriendshipDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable("id") int id) {
        try {
            friendshipService.deleteFriendship(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}