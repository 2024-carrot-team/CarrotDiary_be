package com.example.carrotdiary.postdiary.repository;

import com.example.carrotdiary.postdiary.entity.PostDiary;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostDiaryRepository extends JpaRepository<PostDiary, Long>, PostDiaryCustom {

}
