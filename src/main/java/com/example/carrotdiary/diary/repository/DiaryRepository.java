package com.example.carrotdiary.diary.repository;

import com.example.carrotdiary.diary.entity.Diary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("select d from Diary d where d.postDiary.id = :postDiaryId order by d.creatDate asc")
    Optional<Diary> findPreviewContent(Long postDiaryId);
    List<Diary> findByPostDiaryId(Long postDiaryId);

}
