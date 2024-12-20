package com.SocialNet.SocialNetwork.Repository;

import com.SocialNet.SocialNetwork.Entites.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR (m.sender.id = :receiverId AND m.receiver.id = :senderId)")
    List<Message> findMessagesBySenderIdAndReceiverId(@Param("senderId") int senderId, @Param("receiverId") int receiverId);
}