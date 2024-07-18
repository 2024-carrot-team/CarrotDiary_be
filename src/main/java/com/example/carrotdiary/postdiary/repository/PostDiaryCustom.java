package com.example.carrotdiary.postdiary.repository;

import com.example.carrotdiary.diary.entity.DiarySearch;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.postdiary.dto.PostDiaryFlatDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostDiaryCustom {

    Page<PostDiaryFlatDto> findAllPostDiariesPaging(Pageable pageable, Member currentMember);

    Page<PostDiaryFlatDto> findPostDiaryBySearch(DiarySearch diarySearch, String searchContent,
                                                 Pageable pageable, Member currentMember);
    String findMemberEmailByPostDiaryId(Long postDiaryId);
}
