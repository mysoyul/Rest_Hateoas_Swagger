package com.boot3.myrestapi.lectures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureReqDto {
    @NotBlank(message = "Name은 필수 입력항목입니다.")
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime beginEnrollmentDateTime;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime closeEnrollmentDateTime;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime beginLectureDateTime;
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endLectureDateTime;
    private String location;
    @Min(0)
    private int basePrice;

    @Min(0)
    private int maxPrice;

    @Min(10)
    private int limitOfEnrollment;
}