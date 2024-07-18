package com.example.carrotdiary.comment.repository;

import com.example.carrotdiary.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.child where c.postDiary.id = :postDiaryId and c.parent is null")
    List<Comment> findParentWithChildByPostDiaryId(@Param("postDiaryId") Long postDiaryId);

    List<Comment> findByPostDiaryId(Long postDiaryId);

}
