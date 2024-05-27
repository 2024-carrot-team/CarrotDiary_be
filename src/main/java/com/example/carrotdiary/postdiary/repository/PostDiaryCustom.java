package com.example.carrotdiary.postdiary.repository;

import com.example.carrotdiary.diary.entity.DiarySearch;
import com.example.carrotdiary.postdiary.dto.PostDiaryFlatDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostDiaryCustom {

    Page<PostDiaryFlatDto> findAllPostDiariesPaging(Pageable pageable);

    Page<PostDiaryFlatDto> findPostDiaryBySearch(DiarySearch diarySearch, String searchContent, Pageable pageable);
    String findMemberEmailByPostDiaryId(Long postDiaryId);
}
