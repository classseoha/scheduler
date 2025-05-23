package com.example.scheduler.controller;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Scheduler;
import com.example.scheduler.service.SchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class SchedulerController {

    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {

        this.schedulerService = schedulerService;
    }

    //새로운 일정 생성 요청, 응답 메서드
    @PostMapping
    public ResponseEntity<SchedulerResponseDto> createSchedule(@RequestBody SchedulerRequestDto dto) {

        return new ResponseEntity<>(schedulerService.createSchedule(dto), HttpStatus.CREATED);
    }

    //이름, 수정일자를 검색하여 등록된 일정 목록 조회 요청, 응답 메서드(name, updatedAt)
    @GetMapping
    public ResponseEntity<List<SchedulerResponseDto>> findSchedule(@RequestBody SchedulerRequestDto dto) {

        return new ResponseEntity<>(schedulerService.findSchedule(dto), HttpStatus.OK);
    }

    //특정 일정 상세 조회 요청, 응답 메서드(id)
    @GetMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(schedulerService.findScheduleById(id), HttpStatus.OK);
    }

    //선택한 일정 내용 수정 요청, 응답 메서드(todo, name)
    @PatchMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> updateSchedule(@PathVariable Long id, @RequestBody SchedulerRequestDto dto) {

        return new ResponseEntity<>(schedulerService.updateSchedule(id, dto.getTodo(), dto.getName(), dto.getPassword()), HttpStatus.OK);
    }

    //선택한 일정 삭제 요청, 응답 메서드(id)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody SchedulerRequestDto dto) {

        schedulerService.deleteSchedule(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
