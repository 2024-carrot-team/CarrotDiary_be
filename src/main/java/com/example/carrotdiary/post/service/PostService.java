package com.example.carrotdiary.post.service;

import com.example.carrotdiary.common.Result;

import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.image.repository.ImageRepository;
import com.example.carrotdiary.member.repository.MemberRepository;
import com.example.carrotdiary.post.dto.PostRequestDto;
import com.example.carrotdiary.post.dto.PostResponseDto.PostDto;
import com.example.carrotdiary.post.repository.PostRepository;
import com.example.carrotdiary.post.entity.Post;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    // Post 등록
    @Transactional
    public Long createPost(Long memberId, PostRequestDto.updatePostDto updatePostDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));
        Image image = imageRepository.findById(updatePostDto.getImageId())
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));
        Post post = Post.addPost(member, updatePostDto.getTitle(), image);

        postRepository.save(post);

        return post.getId();
    }

    // Post 조회
    @Transactional
    public Result getPost(Long memberId) {

        List<Post> posts = postRepository.findByMemberId(memberId);
        List<PostDto> result = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return new Result(result);
    }

    // Post 수정
    @Transactional
    public Result updatePost(Long postId, PostRequestDto.updatePostDto updatePostDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post 아이디가 없습니다."));

        if (updatePostDto.getImageId() != null && !updatePostDto.getImageId().equals(post.getImage().getId())) {
            Image image = imageRepository.findById(updatePostDto.getImageId())
                    .orElseThrow(() -> new NoSuchElementException("조회된 Image 아이디가 없습니다."));
            post.updatePost(updatePostDto.getTitle(), image);
        } else {
            post.updatePost(updatePostDto.getTitle(), post.getImage());
        }

        return new Result(post);
    }


    // Post 삭제
    @Transactional
    public void deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post 아이디가 없습니다."));

        postRepository.delete(post);
    }
}
