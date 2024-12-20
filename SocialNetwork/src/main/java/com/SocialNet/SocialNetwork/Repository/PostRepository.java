package com.SocialNet.SocialNetwork.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SocialNet.SocialNetwork.Entites.Post;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(int userId); // Новый метод для поиска постов по user_id
}
