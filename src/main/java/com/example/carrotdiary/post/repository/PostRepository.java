package com.example.carrotdiary.post.repository;

import com.example.carrotdiary.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberId(Long memberId);

    List<Post> findByMemberEmail(String userEmail);

    @EntityGraph(attributePaths = {"image"})
    @Query("select p from Post p join fetch p.member m where m.email = :userEmail")
    List<Post> findWithImagesByMemberEmail(@Param("userEmail") String userEmail);

    Optional<Post> findByIdAndMemberEmail(Long id, String userEmail);
}
