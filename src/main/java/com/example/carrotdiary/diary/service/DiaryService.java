package com.example.carrotdiary.diary.service;



import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.diary.dto.DiaryResponseDto.DiaryContentDto;
import com.example.carrotdiary.diary.dto.DiaryResponseDto.DiaryDto;
import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.diary.repository.DiaryRepository;
import com.example.carrotdiary.image.dto.ImageResponseDto;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.image.repository.ImageRepository;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import com.example.carrotdiary.postdiary.repository.PostDiaryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    // Diary 등록
    @Transactional
    public Result createDiary(Long postDiaryId, String content, List<Image> images) {
        PostDiary postDiary = postDiaryRepository.findById(postDiaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 PostDiary 아이디가 없습니다"));

        Diary diary = Diary.addDiary(postDiary, content, images);

        diaryRepository.save(diary);
        imageRepository.saveAll(images);

        DiaryDto diaryDto = new DiaryDto(diary);

        return new Result(diaryDto);
    }

    // Diary 조회
    @Transactional
    public Result getPreviewDiary(Long postId) {
        List<Long> postDiaryId = postDiaryRepository.findIdsByPostId(postId);
        List<DiaryContentDto> result = new ArrayList<>();

        for (Long id : postDiaryId) {
            Optional<Diary> diary = diaryRepository.findPreviewContent(id);
            diary.ifPresent(d -> result.add(new DiaryContentDto(d)));
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
                .orElseThrow(() -> new NoSuchElementException("조회된 Diary 아이디가 없습니다."));

        DiaryDto diaryDto = new DiaryDto(diary);

        return new Result(diaryDto);

    }

//    @Transactional
//    public Long updateDiary(Long diaryId, String content, List<Image> images) {
//        Diary diary = diaryRepository.findById(diaryId)
//                .orElseThrow(() -> new NoSuchElementException("조회된 Diary 아이디가 없습니다."));
//
//        diary.updateDiary(content);
//
//        if (imageIdsTo)
//
//    }





    // Diary 삭제
    @Transactional
    public void deleteDiary(Long diaryId) {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 Diary 아이디가 없습니다."));

        diaryRepository.delete(diary);

    }

}
