package com.example.carrotdiary.diary.service;



import com.example.carrotdiary.diary.dto.DiaryRequestDto;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.diary.dto.DiaryResponseDto.DiaryContentDto;
import com.example.carrotdiary.diary.dto.DiaryResponseDto.DiaryDto;
import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.diary.repository.DiaryRepository;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.image.repository.ImageRepository;
import com.example.carrotdiary.image.service.ImageService;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import com.example.carrotdiary.postdiary.repository.PostDiaryRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final PostDiaryRepository postDiaryRepository;
    private final DiaryRepository diaryRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    // Diary 등록
    @Transactional
    public Long createDiary(Long postDiaryId, String content, List<MultipartFile> images) throws IOException {
        PostDiary postDiary = postDiaryRepository.findById(postDiaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 PostDiary 아이디가 없습니다"));

        List<Image> uploadImages = imageService.uploadImages(images);

        Diary diary = Diary.addDiary(postDiary, content, uploadImages);

        diaryRepository.save(diary);
        imageRepository.saveAll(uploadImages);

        return diary.getId();
    }

    // Diary 조회
    @Transactional
    public Result getPreviewDiary(Long postId) {
        List<Long> postDiaryId = postDiaryRepository.findIdsByPostId(postId);
        List<DiaryContentDto> result = new ArrayList<>();

        for (Long id : postDiaryId) {
            Page<Diary> diary = diaryRepository.findAscByPostDiaryId(id, PageRequest.of(0,1));
            result.addAll(diary.map(DiaryContentDto::new)
                    .getContent());
        }
        return new Result(result);
    }

    @Transactional
    public Result getAllDiary(Long postDiaryId) {
        List<Diary> diary = diaryRepository.findByPostDiaryId(postDiaryId);
        List<DiaryDto> result = diary.stream()
                .map(DiaryDto::new)
                .collect(Collectors.toList());

        return new Result(result);
    }

    @Transactional
    public Result getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Diary 가 없습니다."));

        DiaryDto diaryDto = new DiaryDto(diary);

        return new Result(diaryDto);
    }

    @Transactional
    public Result updateDiary(Long diaryId, DiaryRequestDto.updateDiaryDto updateDiaryDto, List<MultipartFile> images) throws IOException {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Diary 가 없습니다."));

        if (updateDiaryDto.getContent() != null) {
            diary.updateDiaryContent(updateDiaryDto.getContent());
        }

        if (images != null) {
            List<Image> newImages = imageService.uploadImages(images);
            diary.updateDiaryImage(newImages);

        }
        if (updateDiaryDto.getDeleteImageIds() != null) {
            imageService.deleteImages(updateDiaryDto.getDeleteImageIds());
        }
        diaryRepository.save(diary);

        DiaryDto diaryDto = new DiaryDto(diary);

        return new Result(diaryDto);
    }

    // Diary 삭제
    @Transactional
    public void deleteDiary(Long diaryId) {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Diary 아이디가 없습니다."));

        diaryRepository.delete(diary);
    }
}
