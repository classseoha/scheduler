package com.example.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerRequestDto {

    private String name;
    private String password;
    private String todo;
    private String userUpdatedAt;

    //일정 리스트 검색 조회 시 필요한 요청
    public SchedulerRequestDto(String name, String userUpdatedAt) {
        this.name = name;
        this.userUpdatedAt = userUpdatedAt;
    }
}
