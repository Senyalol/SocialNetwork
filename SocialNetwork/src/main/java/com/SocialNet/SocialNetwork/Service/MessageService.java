package com.SocialNet.SocialNetwork.Service;

import com.SocialNet.SocialNetwork.DTO.MessageDTO.CreateMessageDTO;
import com.SocialNet.SocialNetwork.DTO.MessageDTO.ShortMessageDTO;
import com.SocialNet.SocialNetwork.DTO.MessageDTO.UpdateMessageDTO;
import com.SocialNet.SocialNetwork.Entites.Message;
import com.SocialNet.SocialNetwork.Repository.MessageRepository;
import com.SocialNet.SocialNetwork.Entites.User;
import com.SocialNet.SocialNetwork.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private static final List<String> TABOO_WORDS = List.of("Воронец","Блин","Бляха");

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<ShortMessageDTO> getMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ShortMessageDTO getMessageById(int id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));
        return convertToDTO(message);
    }

    public List<ShortMessageDTO> getMessagesBetween(int senderId, int receiverId) {
        List<Message> messages = messageRepository.findMessagesBySenderIdAndReceiverId(senderId, receiverId);
        return messages.stream()
                .distinct()
                .map(this::convertToDTO)
                .toList();
    }

    public void createMessage(CreateMessageDTO createMessageDTO) {
        Message message = new Message();
        User sender = userRepository.findById(createMessageDTO.getSender_id())
                .orElseThrow(() -> new NoSuchElementException("Sender not found"));
        User receiver = userRepository.findById(createMessageDTO.getReceiver_id())
                .orElseThrow(() -> new NoSuchElementException("Receiver not found"));

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setUser_content(filterMessage(createMessageDTO.getUser_content())); // Фильтрация
        message.setReceiver_content(createMessageDTO.getReceiver_content());

        messageRepository.save(message);
    }

    public void updateMessage(int id, UpdateMessageDTO updateMessageDTO) {
        Message messageToUpdate = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        if (updateMessageDTO.getSender_id() != null) {
            User sender = userRepository.findById(updateMessageDTO.getSender_id())
                    .orElseThrow(() -> new NoSuchElementException("Sender not found"));
            messageToUpdate.setSender(sender);
        }
        if (updateMessageDTO.getReceiver_id() != null) {
            User receiver = userRepository.findById(updateMessageDTO.getReceiver_id())
                    .orElseThrow(() -> new NoSuchElementException("Receiver not found"));
            messageToUpdate.setReceiver(receiver);
        }
        if (updateMessageDTO.getUser_content() != null) {
            messageToUpdate.setUser_content(filterMessage(updateMessageDTO.getUser_content())); // Фильтрация
        }
        if (updateMessageDTO.getReceiver_content() != null) {
            messageToUpdate.setReceiver_content(updateMessageDTO.getReceiver_content());
        }

        messageRepository.save(messageToUpdate);
    }

    public void deleteMessage(int id) {
        Message messageToDelete = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        messageRepository.delete(messageToDelete);
    }

    private ShortMessageDTO convertToDTO(Message message) {
        ShortMessageDTO messageDTO = new ShortMessageDTO();
        messageDTO.setMessage_id(message.getId());
        messageDTO.setSender_id(message.getSender().getId());
        messageDTO.setReceiver_id(message.getReceiver().getId());
        messageDTO.setUser_content(message.getUser_content());
        messageDTO.setReceiver_content(message.getReceiver_content());
        return messageDTO;
    }

    private String filterMessage(String message) {
        for (String tabooWord : TABOO_WORDS) {
            message = message.replaceAll("(?i)" + tabooWord, "***"); // Регистронезависимая замена
        }
        return message;
    }
}