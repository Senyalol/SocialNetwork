package com.SocialNet.SocialNetwork.DTO.FriendshipDTO;
import lombok.Data;

@Data
public class UpdateFriendshipDTO {
    private Integer friendship_id;
    private Integer user_id;
    private Integer friend_id;
    private String status;
}
