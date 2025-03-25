package com.example.scheduler.repository;

import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Scheduler;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SchedulerRepositoryImpl implements SchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public SchedulerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SchedulerResponseDto createSchedule(Scheduler scheduler) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("scheduler").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("todo", scheduler.getTodo());
        parameters.put("name", scheduler.getName());
        parameters.put("password", scheduler.getPassword());
        parameters.put("createdAt", scheduler.getCreatedAt());
        parameters.put("updatedAt", scheduler.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

//        List<SchedulerResponseDto> result = jdbcTemplate.query("select * from memo", schedulerRowMapper());
//        String createdAt = jdbcTemplate.queryForObject("select createdAt from scheduler where id = ?", String.class, key.toString());

        return new SchedulerResponseDto(key.longValue(), scheduler.getName(), scheduler.getTodo(), scheduler.getCreatedAt(), scheduler.getUpdatedAt());
    }

    @Override
    public List<SchedulerResponseDto> findSchedule() {

        return jdbcTemplate.query("select * from scheduler", schedulerRowMapper());
    }

    @Override
    public Optional<Scheduler> findScheduleById(Long id) {

        List<Scheduler> result = jdbcTemplate.query("select * from where id = ?", schedulerRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public Scheduler findScheduleByIdOrElseThrow(Long id) {

        List<Scheduler> result = jdbcTemplate.query("select * from scheduler where id = ?", schedulerRowMapperV3(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String todo, String name, String password) {

        return jdbcTemplate.update("update scheduler set todo = ?, name = ?, updatedAt = NOW() where id = ?", todo, name, id);
    }

    @Override
    public int deleteSchedule(Long id) {

        return jdbcTemplate.update("delete from scheduler where id = ?", id);
    }

    private RowMapper<SchedulerResponseDto> schedulerRowMapper() {

        return new RowMapper<SchedulerResponseDto>() {
            @Override
            public SchedulerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new SchedulerResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Scheduler> schedulerRowMapperV2() {

        return new RowMapper<Scheduler>() {
            @Override
            public Scheduler mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Scheduler(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Scheduler> schedulerRowMapperV3() {

        return new RowMapper<Scheduler>() {
            @Override
            public Scheduler mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Scheduler(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("todo"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }
}
