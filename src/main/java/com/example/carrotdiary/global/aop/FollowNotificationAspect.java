package com.example.carrotdiary.global.aop;


import com.example.carrotdiary.follow.service.NotificationService;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-sns.properties")
public class FollowNotificationAspect {

    private final NotificationService notificationService;
    private final MemberRepository memberRepository;

    @Value("${sns.topic.arn}")
    private String topicArn;

    @Pointcut(value = "execution(* com.example.carrotdiary.follow.service.FollowService.follow(..)) && args(followerEmail, followingEmail)", argNames = "followerEmail,followingEmail")
    public void followMethod(String followerEmail, String followingEmail) {}

    @AfterReturning(pointcut = "followMethod(followerEmail, followingEmail)", argNames = "followerEmail,followingEmail")
    public void sendFollowNotification(String followerEmail, String followingEmail) {

        // followingEmail을 사용하여 snsEndpointArn 가져오기
        memberRepository.findByEmail(followingEmail)
                .map(Member::getSnsEndpointArn)
                .ifPresent(
                        snsEndpointArn -> notificationService.sendFollowNotification(snsEndpointArn, followerEmail, followingEmail));

    }

}
