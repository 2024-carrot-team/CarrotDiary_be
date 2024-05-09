package com.example.carrotdiary.diary.repository;

import com.example.carrotdiary.diary.entity.Diary;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryRepositoryCustom {

    List<Diary> findByPostDiaryId(Long postDiaryId);

    Page<Diary> findPostDiaryAndDiaryByPostIdPaging(Long postId, Pageable pageable);
}
