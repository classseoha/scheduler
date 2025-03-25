package com.example.scheduler.service;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Scheduler;
import com.example.scheduler.repository.SchedulerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @Override
    public SchedulerResponseDto createSchedule(SchedulerRequestDto dto) {

        Scheduler scheduler = new Scheduler(dto.getName(), dto.getPassword(), dto.getTodo());

        return schedulerRepository.createSchedule(scheduler);
    }

    @Override
    public List<SchedulerResponseDto> findSchedule() {

        return schedulerRepository.findSchedule();
    }

    @Override
    public SchedulerResponseDto findScheduleById(Long id) {

        Scheduler scheduler = schedulerRepository.findScheduleByIdOrElseThrow(id);

        return new SchedulerResponseDto(scheduler);
    }

    @Transactional
    @Override
    public SchedulerResponseDto updateSchedule(Long id, String todo, String name, String password) {

        if(todo == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

        //id로 해당 일정 조회
        Scheduler scheduler = schedulerRepository.findScheduleByIdOrElseThrow(id);

        if(scheduler.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is null in DB for id = " + id);
        }

        //비밀번호 검증
        if(!scheduler.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect.");
        }

        int updatedRow = schedulerRepository.updateSchedule(id, todo, name, password);

        if(updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Scheduler updatedScheduler = schedulerRepository.findScheduleByIdOrElseThrow(id);

        return new SchedulerResponseDto(updatedScheduler);
    }

    @Override
    public void deleteSchedule(Long id, String password) {

        //id로 해당 일정 조회
        Scheduler scheduler = schedulerRepository.findScheduleByIdOrElseThrow(id);

        if(scheduler.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is null in DB for id = " + id);
        }

        //비밀번호 검증
        if(!scheduler.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect.");
        }

        //비밀번호 일치시 삭제
        int deleteRow = schedulerRepository.deleteSchedule(id);

        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
