package com.example.carrotdiary.image.service;

import com.example.carrotdiary.image.dto.ImageResponseDto;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.image.repository.ImageRepository;
import java.util.ArrayList;
import java.util.List;
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

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // 여러 Image 등록
    @Transactional
    public List<Image> uploadImages(List<MultipartFile> images) throws IOException {

        List<Image> uploadedImages = new ArrayList<>();

        for (MultipartFile multipartFile : images) {
            if (!multipartFile.isEmpty()) {
                ImageResponseDto imageResponseDto = uploadImageToS3(multipartFile); // S3에 저장될 파일 이름 생성

                uploadedImages.add(new Image(imageResponseDto.getFileName(), imageResponseDto.getImageUrl()));
            }
        }

        return uploadedImages;
    }

    // 단일 Image 등록
    // Image 등록 메소드 이름 변경이 필요할것같음. Member에서도 사용하게 됨.
    @Transactional
    public Image uploadPostImage(MultipartFile multipartFile) throws IOException {

        ImageResponseDto imageResponseDto = uploadImageToS3(multipartFile);

        Image postImage = new Image(imageResponseDto.getFileName(), imageResponseDto.getImageUrl());

        imageRepository.save(postImage);

        return postImage;


    }

    // Image 수정
    @Transactional
    public void updateImage(Long imageId, MultipartFile multipartFile) throws IOException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 Image 아이디가 없습니다."));

        if (image.getFileName() != null) {
            deleteImageFromS3(image.getFileName());
        }

        ImageResponseDto imageResponseDto = uploadImageToS3(multipartFile);
        image.updateImage(imageResponseDto.getFileName(), imageResponseDto.getImageUrl());

        imageRepository.save(image);
    }

    // Image 삭제
    @Transactional
    public void deleteImages(List<Long> imageIds) {

        for (Long imageId : imageIds) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new IllegalArgumentException("조회된 Image 아이디가 없습니다."));

            String s3FileName = image.getFileName();
            imageRepository.delete(image);
            deleteImageFromS3(s3FileName);
        }
    }

    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 Image 아이디가 없습니다."));

        String s3FileName = image.getFileName();
        imageRepository.delete(image);
        deleteImageFromS3(s3FileName);

    }

    private void deleteImageFromS3(String fileName) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName));
    }

    private ImageResponseDto uploadImageToS3(MultipartFile multipartFile) throws IOException {
        String s3FileName = getS3FileName(multipartFile);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .key(s3FileName)
                .build();

        RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
        s3Client.putObject(putObjectRequest, requestBody);

        String imageUrl = s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(s3FileName)
                .build()).toString();

        return new ImageResponseDto(s3FileName, imageUrl);
    }

    private static String getS3FileName(MultipartFile multipartFile) {
        return UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
    }
}