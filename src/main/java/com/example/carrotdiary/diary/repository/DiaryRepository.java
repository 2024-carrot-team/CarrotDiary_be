package com.example.carrotdiary.diary.repository;

import com.example.carrotdiary.diary.entity.Diary;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

}
