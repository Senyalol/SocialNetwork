package com.SocialNet.SocialNetwork.Entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_id_gen")
    @SequenceGenerator(name = "messages_id_gen", sequenceName = "messages_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sender_id", nullable = false)
    private com.SocialNet.SocialNetwork.Entites.User sender;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_id", nullable = false)
    private com.SocialNet.SocialNetwork.Entites.User receiver;

    @NotNull
    @Column(name = "user_content", nullable = false, length = Integer.MAX_VALUE)
    private String userContent;

    @Column(name = "receiver_content", length = Integer.MAX_VALUE)
    private String receiverContent;

}