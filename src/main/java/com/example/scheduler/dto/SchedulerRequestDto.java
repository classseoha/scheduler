package com.example.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerRequestDto {

    private String name;
    private String password;
    private String todo;
    private String userUpdatedAt;

    public SchedulerRequestDto(String name, String userUpdatedAt) {
        this.name = name;
        this.userUpdatedAt = userUpdatedAt;
    }
}
