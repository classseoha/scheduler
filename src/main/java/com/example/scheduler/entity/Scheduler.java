package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Scheduler {

    private Long id;
    private String name;
    private String password;
    private String todo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userUpdatedAt;


    //새 게시물 생성 시 자동으로 시간 설정
    public Scheduler(String name, String password, String todo) {
        this.name = name;
        this.password = password;
        this.todo = todo;
        this.createdAt = LocalDateTime.now(); //생성시간 설정
        this.updatedAt = LocalDateTime.now(); //수정시간 설정
    }

    public Scheduler(Long id, String name, String todo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.todo = todo;
        this.createdAt = createdAt; //생성시간 설정
        this.updatedAt = updatedAt; //수정시간 설정
    }

    public Scheduler(Long id, String name, String password, String todo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.todo = todo;
        this.createdAt = createdAt; //생성시간 설정
        this.updatedAt = updatedAt; //수정시간 설정
    }

    public Scheduler(String name, String userUpdatedAt) {
        this.name = name;
        this.userUpdatedAt = userUpdatedAt;
    }

    public void update(String name, String password, String todo) {
        this.name = name;
        this.password = password;
        this.todo = todo;
        this.updatedAt = LocalDateTime.now(); //수정시간 업데이트
    }



}
