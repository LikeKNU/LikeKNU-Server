package com.woopaca.likeknu.entity;

import com.woopaca.likeknu.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "academic_calendar")
public class AcademicCalendar extends BaseEntity {

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    protected AcademicCalendar() {
        super(Domain.ACADEMIC_CALENDAR);
    }

    @Builder
    public AcademicCalendar(String contents, LocalDate startDate, LocalDate endDate) {
        this();
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
