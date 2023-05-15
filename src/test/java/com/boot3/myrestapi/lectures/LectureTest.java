package com.boot3.myrestapi.lectures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LectureTest {
    @Test
    public void builder() {
        Lecture lecture = Lecture.builder()
                .name("Spring REST API")
                .description("REST API developmemt with Spring")
                .build();
        assertEquals("Spring REST API", lecture.getName());
    }
    @Test
    public void javaBean()
    {
        String name = "Lecture"; //Give
        String description = "Spring";
        Lecture lecture = new Lecture(); //When
        lecture.setName(name);
        lecture.setDescription(description);
        assertThat(lecture.getName()).isEqualTo(name); //Then
        assertThat(lecture.getDescription()).isEqualTo(description);
    }
}