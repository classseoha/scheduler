package com.example.scheduler.service;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulerService {

    SchedulerResponseDto createSchedule(SchedulerRequestDto dto);

    List<SchedulerResponseDto> findSchedule(SchedulerRequestDto dto);

    SchedulerResponseDto findScheduleById(Long id);

    SchedulerResponseDto updateSchedule(Long id, String todo, String name, String password);

    void deleteSchedule(Long id, String password);




}


