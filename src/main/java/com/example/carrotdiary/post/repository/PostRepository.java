package com.example.carrotdiary.post.repository;

import com.example.carrotdiary.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {


    @EntityGraph(attributePaths = {"image"})
    @Query("select p from Post p join fetch p.member m where m.email = :userEmail")
    List<Post> findWithImagesByMemberEmail(@Param("userEmail") String userEmail);

    @Query("select m.email from Post p join p.member m where p.id = :postId")
    String findMemberEmailByPostId(@Param("postId") Long postId);

}
