package com.example.carrotdiary.post.repository;

import com.example.carrotdiary.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberId(Long memberId);

    List<Post> findByMemberEmail(String userEmail);

    Optional<Post> findByIdAndMemberEmail(Long id, String userEmail);
}
