package ru.samoylov.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskResponse {
    private Long id;
    private Long userId;
    private Date completedTaskTime;
    private String status;
    private String title;
    private String description;

}
