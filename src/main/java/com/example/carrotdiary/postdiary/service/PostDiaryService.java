package com.example.carrotdiary.postdiary.service;

import com.example.carrotdiary.post.entity.Post;
import com.example.carrotdiary.post.repository.PostRepository;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import com.example.carrotdiary.postdiary.repository.PostDiaryRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDiaryService {
    private final PostRepository postRepository;
    private final PostDiaryRepository postDiaryRepository;

    // 등록
    @Transactional
    public Long createPostDiary(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));

        PostDiary postDiary = PostDiary.addPostDiary(post);

        postDiaryRepository.save(postDiary);

        return postDiary.getId();
    }

    // 삭제
    @Transactional
    public void deletePostDiary(Long postDiaryId) {

        PostDiary postDiary = postDiaryRepository.findById(postDiaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));

        postDiaryRepository.delete(postDiary);
    }



}
