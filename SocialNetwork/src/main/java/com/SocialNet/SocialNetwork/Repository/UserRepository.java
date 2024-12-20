package com.SocialNet.SocialNetwork.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SocialNet.SocialNetwork.Entites.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
