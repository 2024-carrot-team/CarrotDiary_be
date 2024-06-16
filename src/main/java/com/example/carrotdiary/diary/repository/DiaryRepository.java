package com.example.carrotdiary.diary.repository;

import com.example.carrotdiary.diary.entity.Diary;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("select d from Diary d where d.postDiary.id = :postDiaryId order by d.createDate asc ")
    Page<Diary> findAscByPostDiaryId(Long postDiaryId, Pageable pageable);
    List<Diary> findByPostDiaryId(Long postDiaryId);

}
