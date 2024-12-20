package com.SocialNet.SocialNetwork.DTO.PostDTO;

import java.time.Instant;
import lombok.Data;

@Data
public class ShortPostInfoDTO {
    private Integer post_id;
    private Integer user_id;
    private String text;
    private String imageUrl;
    private Instant createdAt;
}
