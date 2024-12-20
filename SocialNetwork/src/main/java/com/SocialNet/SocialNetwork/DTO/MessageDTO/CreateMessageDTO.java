package com.SocialNet.SocialNetwork.DTO.MessageDTO;
import lombok.Data;

@Data
public class CreateMessageDTO {
    private Integer message_id;
    private Integer sender_id;
    private Integer receiver_id;
    private String user_content;
    private String receiver_content;
}