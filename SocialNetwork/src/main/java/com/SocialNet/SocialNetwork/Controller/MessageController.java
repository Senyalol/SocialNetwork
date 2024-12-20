package com.SocialNet.SocialNetwork.Controller;

import com.SocialNet.SocialNetwork.Service.MessageService;
import com.SocialNet.SocialNetwork.DTO.MessageDTO.CreateMessageDTO;
import com.SocialNet.SocialNetwork.DTO.MessageDTO.ShortMessageDTO;
import com.SocialNet.SocialNetwork.DTO.MessageDTO.UpdateMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<ShortMessageDTO> getAllMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/{id}")
    public ShortMessageDTO getMessageById(@PathVariable int id) {
        return messageService.getMessageById(id);
    }

    @GetMapping("/between/{senderId}/{receiverId}")
    public List<ShortMessageDTO> getMessagesBetween(@PathVariable int senderId, @PathVariable int receiverId) {
        return messageService.getMessagesBetween(senderId, receiverId);
    }

    @PostMapping
    public void createMessage(@RequestBody CreateMessageDTO createMessageDTO) {
        messageService.createMessage(createMessageDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMessage(@PathVariable("id") int id, @Valid @RequestBody UpdateMessageDTO updateMessageDTO) {
        try {
            messageService.updateMessage(id, updateMessageDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") int id) {
        try {
            messageService.deleteMessage(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}