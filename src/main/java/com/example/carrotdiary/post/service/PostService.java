package com.example.carrotdiary.post.service;

import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.member.entity.MemberEntity;
import com.example.carrotdiary.image.repository.ImageRepository;
import com.example.carrotdiary.member.repository.MemberRepository;
import com.example.carrotdiary.post.dto.PostRequestDto;
import com.example.carrotdiary.post.dto.PostResponseDto;
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
    public Long createPost(String userEmail, PostRequestDto postRequestDto) {

        MemberEntity member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));
        Image image = imageRepository.findByImageUrl(postRequestDto.getImageUrl())
                .orElseThrow(() -> new NoSuchElementException("조회된 이미지가 없습니다."));
        Post post = Post.addPost(member, postRequestDto.getTitle(), image);

        postRepository.save(post);

        return post.getId();
    }

    // Post 조회
    @Transactional
    public Result getPosts(String userEmail) {

        List<Post> posts = postRepository.findWithImagesByMemberEmail(userEmail);
        List<PostResponseDto> result = posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new Result(result);
    }

    /*// Post 수정

    // 이미지와 타이틀 중 타이틀만 수정하고 이미지를 냅둘경우 클라이언트에서 보내는 데이터가
    //수정된 데이터와 이미지 = null 인가?
    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post 아이디가 없습니다."));

        // 이미지를 수정을 한 경우
        if (!Objects.equals(postRequestDto.getImageUrl(), post.getImage().getImageUrl())) {

            Image image = new Image(postRequestDto.getImageUrl(), )
            imageRepository.save()
            Image image = imageRepository.findByImageUrl(postRequestDto.getImageUrl())
                    .orElseThrow(() -> new NoSuchElementException("조회된 Image 아이디가 없습니다."));
            post.updatePost(postRequestDto.getTitle(), image);
            // 이미지를 수정을 안한 경우
        } else {
            post.updatePost(postRequestDto.getTitle(), post.getImage());
        }
    }*/


    // Post 삭제
    @Transactional
    public void deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post 아이디가 없습니다."));

        postRepository.delete(post);
    }

}
