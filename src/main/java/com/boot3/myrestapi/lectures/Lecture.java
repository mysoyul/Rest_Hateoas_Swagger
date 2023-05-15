package com.boot3.myrestapi.lectures;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
public class Lecture {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime beginEnrollmentDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime closeEnrollmentDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime beginLectureDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endLectureDateTime;
    
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;

    private boolean free;
    private LectureStatus lectureStatus = LectureStatus.DRAFT;
}    