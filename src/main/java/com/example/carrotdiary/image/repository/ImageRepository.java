package com.example.carrotdiary.image.repository;

import com.example.carrotdiary.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
