package com.example.carrotdiary.member.entity;

import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.global.constants.Role;
import com.example.carrotdiary.member.dto.MemberRequestDto.updateRequestDto;
import com.example.carrotdiary.post.entity.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue()
    private long id;
    private String email;

    private String password;
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime brithDayTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();



    public void updateMember(updateRequestDto updateRequestDto) {
        this.email = updateRequestDto.getEmail();
        this.password = updateRequestDto.getPassword();
        this.nickname = updateRequestDto.getNickname();
        this.brithDayTime = updateRequestDto.getBrithDayTime();
    }


}
