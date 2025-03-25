package com.example.scheduler.dto;

import com.example.scheduler.entity.Scheduler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SchedulerResponseDto {

    private Long id;
    private String name;
    private String todo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SchedulerResponseDto(Scheduler scheduler) {
        this.id = scheduler.getId();
        this.name = scheduler.getName();
        this.todo = scheduler.getTodo();
        this.createdAt = scheduler.getCreatedAt();
        this.updatedAt = scheduler.getUpdatedAt();
    }

}
