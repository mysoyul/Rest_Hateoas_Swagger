package com.boot3.myrestapi.lectures;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    //finder method
    //select * from lecture where name = ?
    List<Lecture> findByName(String name);

}