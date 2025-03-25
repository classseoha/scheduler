package com.example.scheduler.repository;

import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Scheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulerRepository {

    SchedulerResponseDto createSchedule(Scheduler scheduler);

    List<SchedulerResponseDto> findSchedule();

    Optional<Scheduler> findScheduleById(Long id);

    Scheduler findScheduleByIdOrElseThrow(Long id);

    int updateSchedule(Long id, String todo, String name, String password);

    int deleteSchedule(Long id);

}
