package com.example.carrotdiary.follow.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-sns.properties")
public class DeviceTokenService {

    @Value("${cloud.aws.sns.platformApplicationArn}")
    private String platformApplicationArn;

    private final AmazonSNS amazonSNS;
    private final MemberRepository memberRepository;

    public String registerToken(String token, String email) {
        String endpointArn = createSnsEndpoint(token);
        saveToken(token, endpointArn, email);
        return endpointArn;
    }

    private String createSnsEndpoint(String token) {
        CreatePlatformEndpointRequest request = new CreatePlatformEndpointRequest()
                .withPlatformApplicationArn(platformApplicationArn)
                .withToken(token);
        CreatePlatformEndpointResult result = amazonSNS.createPlatformEndpoint(request);
        return result.getEndpointArn();
    }

    private void saveToken(String token, String endpointArn, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + email));
        member.setDeviceTokenInMemberEntity(token);
        member.setSnsEndpointArnInMemberEntity(endpointArn);
        memberRepository.save(member);
    }

    public String getEndpointArnByToken(String email) {
        return memberRepository.findByEmail(email)
                .map(Member::getSnsEndpointArn)
                .orElse(null);
    }

}
