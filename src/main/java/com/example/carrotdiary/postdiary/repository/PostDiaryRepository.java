package com.example.carrotdiary.postdiary.repository;

import com.example.carrotdiary.postdiary.entity.PostDiary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostDiaryRepository extends JpaRepository<PostDiary, Long> {

    @Query("select pd.id from PostDiary pd where pd.post.id = :postId")
    List<Long> findIdsByPostId(Long postId);

    @Query("select pd from PostDiary pd order by pd.id desc")
    List<PostDiary> findAllPostDiaries();

}
