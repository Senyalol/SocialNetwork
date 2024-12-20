package com.SocialNet.SocialNetwork.Service;

import com.SocialNet.SocialNetwork.DTO.FriendshipDTO.CreateFriendshipDTO;
import com.SocialNet.SocialNetwork.DTO.FriendshipDTO.ShortFriendshipDTO;
import com.SocialNet.SocialNetwork.DTO.FriendshipDTO.UpdateFriendshipDTO;
import com.SocialNet.SocialNetwork.Entites.Friendship;
import com.SocialNet.SocialNetwork.Repository.FriendShipRepository;
import com.SocialNet.SocialNetwork.Entites.User;
import com.SocialNet.SocialNetwork.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class FriendshipService {

    private final FriendShipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendShipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public List<ShortFriendshipDTO> getFriendships() {
        List<Friendship> friendships = friendshipRepository.findAll();

        return friendships.stream()
                .map(friendship -> {
                    ShortFriendshipDTO friendshipDTO = new ShortFriendshipDTO();
                    friendshipDTO.setFriendship_id(friendship.getId());
                    friendshipDTO.setUser_id(friendship.getUser().getId());
                    friendshipDTO.setFriend_id(friendship.getFriend().getId());
                    friendshipDTO.setStatus(friendship.getStatus());

                    return friendshipDTO;
                }).toList();
    }

    public ShortFriendshipDTO getFriendshipById(int id) {
        Friendship friendship = friendshipRepository.findById(id);

        ShortFriendshipDTO friendshipDTO = new ShortFriendshipDTO();
        friendshipDTO.setFriendship_id(friendship.getId());
        friendshipDTO.setUser_id(friendship.getUser().getId());
        friendshipDTO.setFriend_id(friendship.getFriend().getId());
        friendshipDTO.setStatus(friendship.getStatus());

        return friendshipDTO;
    }

    public void createFriendship(CreateFriendshipDTO createFriendshipDTO) {
        Friendship friendship = new Friendship();
        User user = userRepository.findById(createFriendshipDTO.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        User friend = userRepository.findById(createFriendshipDTO.getFriend_id())
                .orElseThrow(() -> new NoSuchElementException("Friend not found"));

        friendship.setUser(user);
        friendship.setFriend(friend);
        friendship.setStatus(createFriendshipDTO.getStatus() != null ? createFriendshipDTO.getStatus() : "pending");

        friendshipRepository.save(friendship);
    }

    public void updateFriendship(int id, UpdateFriendshipDTO updateFriendshipDTO) {
        Friendship friendshipToUpdate = friendshipRepository.findById(id);

        if (updateFriendshipDTO.getUser_id() != null) {
            User user = userRepository.findById(updateFriendshipDTO.getUser_id())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            friendshipToUpdate.setUser(user);
        }
        if (updateFriendshipDTO.getFriend_id() != null) {
            User friend = userRepository.findById(updateFriendshipDTO.getFriend_id())
                    .orElseThrow(() -> new NoSuchElementException("Friend not found"));
            friendshipToUpdate.setFriend(friend);
        }
        if (updateFriendshipDTO.getStatus() != null) {
            friendshipToUpdate.setStatus(updateFriendshipDTO.getStatus());
        }

        friendshipRepository.save(friendshipToUpdate);
    }

    public void deleteFriendship(int id) {
        Friendship friendshipToDelete = friendshipRepository.findById(id);

        friendshipRepository.delete(friendshipToDelete);
    }
}