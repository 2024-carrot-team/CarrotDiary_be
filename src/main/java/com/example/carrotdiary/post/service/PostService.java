package com.example.carrotdiary.post.service;

import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.image.service.ImageService;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.member.repository.MemberRepository;
import com.example.carrotdiary.post.dto.PostRequestDto;
import com.example.carrotdiary.post.dto.PostResponseDto;
import com.example.carrotdiary.post.dto.PostResponseDto.PostIdDto;
import com.example.carrotdiary.post.repository.PostRepository;
import com.example.carrotdiary.post.entity.Post;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ImageService imageService;


    // Post 등록
    @Transactional
    public PostIdDto createPost(String userEmail, PostRequestDto postRequestDto, MultipartFile file) throws IOException {

        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NoSuchElementException("조회된 아이디가 없습니다."));

        Image image = imageService.uploadPostImage(file);

        Post post = Post.addPost(member, postRequestDto.getTitle(), image);

        postRepository.save(post);

        return new PostIdDto(post);
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

    // Post 수정

    @Transactional
    public Result updatePost(Long postId, PostRequestDto postRequestDto, MultipartFile image) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post 가 없습니다."));

        if (postRequestDto.getTitle() != null) {
            post.updatePostTitle(postRequestDto.getTitle());
        }

        if (image != null && !image.isEmpty()) {
            Image postImage = post.getImage();
            if (postImage != null) {
                imageService.deleteImage(postImage.getId());
            }
            Image newImage = imageService.uploadPostImage(image);
            post.updatePostImage(newImage);
        }

        postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return new Result(postResponseDto);
    }


    // Post 삭제
    @Transactional
    public void deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Post 아이디가 없습니다."));

        postRepository.delete(post);
    }

}
