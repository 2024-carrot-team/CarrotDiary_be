package com.example.carrotdiary.follow.controller;


import com.example.carrotdiary.follow.dto.TokenRequestDto;
import com.example.carrotdiary.follow.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {


    private final DeviceTokenService tokenService;


    @PostMapping("/api/deviceToken/register")
    public ResponseEntity<String> registerToken(@RequestBody TokenRequestDto tokenRequest) {

        String endpointArn = tokenService.registerToken(tokenRequest.token(), tokenRequest.email());

        return ResponseEntity.ok("Token registered successfully with endpoint ARN: " + endpointArn);
    }
}

