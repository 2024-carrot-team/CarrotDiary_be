package com.example.carrotdiary.image.service;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.diary.repository.DiaryRepository;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.image.repository.ImageRepository;
import com.example.carrotdiary.post.entity.Post;
import com.example.carrotdiary.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Client s3Client;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final DiaryRepository diaryRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // Diary Image 등록
    @Transactional
    public Long uploadDiaryImage(MultipartFile multipartFile, Long diaryId) throws IOException {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 Diary 아이디가 없습니다."));

        Image diaryImage = Image.addDiaryImage(uploadImageToS3(multipartFile), getS3FileName(multipartFile), diary);
        imageRepository.save(diaryImage);

        return diaryImage.getId();
    }

    // Post Image 등록
    @Transactional
    public Long uploadPostImage(MultipartFile multipartFile, Long postId) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 Post 아이디가 없습니다."));

        Image postImage = Image.addPostImage(uploadImageToS3(multipartFile), getS3FileName(multipartFile), post);
        imageRepository.save(postImage);

        return postImage.getId();

    }

    // Image 수정
    @Transactional
    public void updateImage(Long imageId, MultipartFile multipartFile) throws IOException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 Image 아이디가 없습니다."));

        if (image.getFileName() != null) {
            deleteImageFromS3(image.getFileName());
        }

        image.updateImage(uploadImageToS3(multipartFile), getS3FileName(multipartFile));

        imageRepository.save(image);
    }

    // Image 삭제
    @Transactional
    public void deleteImage(Long imageId) {

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 Image 아이디가 없습니다."));

        String s3FileName = image.getFileName();
        deleteImageFromS3(s3FileName);

        imageRepository.delete(image);
    }

    private void deleteImageFromS3(String fileName) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName));
    }

    private String uploadImageToS3(MultipartFile multipartFile) throws IOException {
        String s3FileName = getS3FileName(multipartFile);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .key(s3FileName)
                .build();

        RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
        s3Client.putObject(putObjectRequest, requestBody);

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(s3FileName)
                .build();

        return s3Client.utilities().getUrl(getUrlRequest).toString();
    }

    private static String getS3FileName(MultipartFile multipartFile) {
        return UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
    }
}