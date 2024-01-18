package com.example.carrotdiary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Email
    private String email;

    private String password;
    private String nickName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateTime;

    // @OneToMany(mappedBy = "member")
    // private List<Post> posts = new ArrayList<>();
}
