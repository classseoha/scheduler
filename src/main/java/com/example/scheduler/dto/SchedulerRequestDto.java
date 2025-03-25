package com.example.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerRequestDto {

    private String todo;
    private String name;
    private String password;

}
