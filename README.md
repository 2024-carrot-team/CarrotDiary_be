# Project - Delivery

---

# 👥 팀원소개

🤴 김진욱, 정재형


## 🥃 Github 주소

 🏛️ [Carrot Diary](https://github.com/2024-carrot-team/CarrotDiary_be)

## 🥃 팀 노션 주소

[Carrot Diary](https://naraspc.notion.site/Carrot-Diary-c82350f8b48148e9b761111c2074d28b?pvs=74
) 

# 🎯 프로젝트 주제, 목표

주제 : 일기를 심어 당근을 키우자! 당근 다이어리 App 프로젝트

목표 : 일기장 어플리케이션을 제작하여, 마켓에 배포하고 실 트래픽을 받아 처리하기

---

# 💫 트러블 슈팅 및 기술적 의사 결정 👇

1. 취약한 비밀번호와 보안그룹 전사설정에 의한 RDS 해킹 이슈
보안그룹을 0,0,0,0/0으로 전사오픈하여 사용하던 RDS가 해킹당하는 이슈가 발생

  A. 0,0,0,0/0으로 설정한 보안그룹때문에 브루트 포스의 타겟이 됨
  
  B. PWD를 1234qwer로 단순하게 구성하여 쉽게 돌파 당하였다.

두가지로 파악하여 보안그룹을 개인 IP, EC2로 변경하고 
PWD를 복잡하게 변경하여 대처함. 날아간 데이터는 복구할 수 없었음. 

DB 백업과 보안설정에 대한 중요성을 깨닫게 됨. 

---
# 기능 구현 

## 회원 및 로그인 담당 - 기능 구현

1) JWT와 Security를 활용한 로그인 기능 구현
- Filter를 통한 인터셉터로 로그인 구현
- JWT Utils를 통한 쿠키에 토큰 저장
- security를 통한 권한제어

2) 회원 CRUD 구현
- JPA를 활용한 회원 CRUD 기능 구현

3) Kotlin으로 구현된 어플리케이션단과의 연결
- App 출시를 위해 Kotlin으로 구성된 어플리케이션과 연동
- 요청 Header에 쿠키가 담기지 않는 문제 해결

4) EC2, RDS를 통한 인프라 구축

   
---
# 구현 예정 기능 목록
1. 팔로잉 / 팔로워 기능 구현 예정
- 일기의 공개범위를 팔로워 / 팔로잉 기준으로 구분 예정 (Public, private)
2. CICD, BlueGreen
- GitAction, Docker, NginX를 활용한 CICD와 무중단배포 구현 예정
