package com.example.Java_11.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkSchedule {
    private Long id;
    private long staffId;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkSchedule(long staffId, LocalDate workDate, LocalTime startTime, LocalTime endTime)
    {
        this.staffId = staffId;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
