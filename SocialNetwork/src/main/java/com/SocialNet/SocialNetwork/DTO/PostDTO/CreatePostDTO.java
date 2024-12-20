package com.SocialNet.SocialNetwork.DTO.PostDTO;

import lombok.Data;
import java.time.Instant;

@Data
public class CreatePostDTO {

    private Integer post_id;
    private Integer user_id;
    private String text;
    private String imageUrl;
    private Instant createdAt;

}
