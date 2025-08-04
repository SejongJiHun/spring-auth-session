# 프로젝트 소개
간단한 세션 기반 인증 시스템입니다.
Spring Boot를 기반으로 로그인, 회원가입, 로그아웃, 세션 체크, 세션 유지 기능을 제공합니다.

## 기능
- 로그인('/api/login') / 회원가입('/api/signup') / 로그아웃('/api/logout')
- 세션 체크 ('/api/me')
- 세션 유지 및 연장 (`/api/ping`)
- 세션 만료 시 자동 로그아웃

## 프로젝트 설정 
- `application.properties` 참고  
- H2 DB 사용

## 테이블 생성 쿼리
create table if not exists users(
	  id int auto_increment primary key,
    email varchar(50),
    password varchar(100),
    nickname varchar(10),
    role varchar(10)
);

## 기술 스택
- Java 17
- Spring Boot
- H2 Database
- HTML + JavaScript
