package com.example.carrotdiary.image.repository;

import com.example.carrotdiary.image.entity.Image;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByImageUrl(String imageUrl);

    @Query("select i.id from Image i where i.diary.id = :diaryId")
    List<Long> findImageIdsByDiaryId(@Param("diaryId") Long diaryId);

}
