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
    public List<SchedulerResponseDto> findSchedule(SchedulerRequestDto dto) {

        Scheduler scheduler = new Scheduler(dto.getName(), dto.getUserUpdatedAt());

        return schedulerRepository.findSchedule(scheduler);
    }

    @Override
    public SchedulerResponseDto findScheduleById(Long id) {

        Scheduler scheduler = schedulerRepository.findScheduleByIdOrElseThrow(id); //스케줄이 있는 경우에만 값 반환

        return new SchedulerResponseDto(scheduler);
    }

    @Transactional
    @Override
    public SchedulerResponseDto updateSchedule(Long id, String todo, String name, String password) {

        //할일과 이름 둘 중 하나라도 미입력 시 BAD REQUEST 반환
        if(todo == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

        //id로 해당 일정 조회
        Scheduler scheduler = schedulerRepository.findScheduleByIdOrElseThrow(id);

        //password null값 검증
        if(scheduler.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is null in DB for id = " + id);
        }

        //비밀번호 검증
        if(!scheduler.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect.");
        }

        int updatedRow = schedulerRepository.updateSchedule(id, todo, name, password); //이걸 통해 DB에 있는 메모 데이터 수정

        //해당 id값을 가진 Row 수가 0 이라면 notFound
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
