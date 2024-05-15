package com.example.carrotdiary.postdiary.repository;

import com.example.carrotdiary.diary.entity.DiarySearch;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostDiaryCustom {

    Page<PostDiary> findAllPostDiariesPaging(Pageable pageable);

    Page<PostDiary> findPostDiaryBySearch(DiarySearch diarySearch, String searchContent, Pageable pageable);
    String findMemberEmailByPostDiaryId(Long postDiaryId);
}
