package com.boot3.myrestapi.common;

import com.boot3.myrestapi.lectures.LectureController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {
    @GetMapping("/api")
    public RepresentationModel index() {
        var index = new RepresentationModel ();
        index.add(linkTo(LectureController.class).withRel("lectures"));
        return index;
    }
}
