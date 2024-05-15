package com.example.carrotdiary.postdiary.service;

import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.post.entity.Post;
import com.example.carrotdiary.post.repository.PostRepository;
import com.example.carrotdiary.postdiary.dto.PostDiaryRequestDto.PostDiarySearchDto;
import com.example.carrotdiary.postdiary.dto.PostDiaryResponseDto.PostDiaryDto;
import com.example.carrotdiary.postdiary.dto.PostDiaryResponseDto.PostDiaryIdDto;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import com.example.carrotdiary.postdiary.repository.PostDiaryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDiaryService {
    private final PostRepository postRepository;
    private final PostDiaryRepository postDiaryRepository;

    // 등록
    public PostDiaryIdDto createPostDiary(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post가 없습니다."));

        PostDiary postDiary = PostDiary.addPostDiary(post);

        postDiaryRepository.save(postDiary);

        return new PostDiaryIdDto(postDiary);
    }

    // 조회
    @Transactional
    public Long getPostDiary(Long postDiaryId) {
        PostDiary postDiary = postDiaryRepository.findById(postDiaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 PostDiaryId 가 없습니다."));

        return postDiary.getId();
    }

    @Transactional
    public Result getMainPostDiaries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PostDiary> allPostDiaries = postDiaryRepository.findAllPostDiariesPaging(pageable);

        List<PostDiaryDto> result = allPostDiaries.stream()
                .map(PostDiaryDto::new)
                .toList();

        return new Result(result);
    }

    @Transactional
    public Result getPostDiariesBySearch(PostDiarySearchDto postDiarySearchDto, Pageable pageable) {
        Page<PostDiary> postDiaryBySearch = postDiaryRepository.findPostDiaryBySearch(postDiarySearchDto.getDiarySearch(),
                postDiarySearchDto.getSearchContent(), pageable);

        List<PostDiaryDto> result = postDiaryBySearch.stream()
                .map(PostDiaryDto::new)
                .toList();

        return new Result(result);
    }


    // 삭제
    @Transactional
    public void deletePostDiary(String userEmail, Long postDiaryId) {
        validateEmail(userEmail, postDiaryId);

        PostDiary postDiary = postDiaryRepository.findById(postDiaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));

        Post post = postDiary.getPost();

        postDiaryRepository.delete(postDiary);
    }

    private void validateEmail(String userEmail, Long postDiaryId) {
        String emailOfPostDiary = postDiaryRepository.findMemberEmailByPostDiaryId(postDiaryId);
        if (!userEmail.equals(emailOfPostDiary)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}
