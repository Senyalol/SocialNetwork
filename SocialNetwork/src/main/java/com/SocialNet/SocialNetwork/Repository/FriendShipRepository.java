package com.SocialNet.SocialNetwork.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SocialNet.SocialNetwork.Entites.Friendship;

@Repository
public interface FriendShipRepository extends JpaRepository<Friendship, Integer> {
    Friendship findById(int id);
}
