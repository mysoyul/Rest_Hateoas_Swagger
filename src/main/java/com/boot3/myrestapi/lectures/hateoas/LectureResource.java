package com.boot3.myrestapi.lectures.hateoas;

import com.boot3.myrestapi.lectures.dto.LectureResDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LectureResource extends RepresentationModel<LectureResource> {
    private LectureResDto lectureResDto;
    public LectureResource(LectureResDto resDto) {
        this.lectureResDto = resDto;
    }
    
    public LectureResDto getLectureResDto() {
        return lectureResDto;
    }

}
