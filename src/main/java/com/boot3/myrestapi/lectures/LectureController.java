package com.boot3.myrestapi.lectures;

import com.boot3.myrestapi.common.ErrorsResource;
import com.boot3.myrestapi.exception.BusinessException;
import com.boot3.myrestapi.lectures.dto.LectureReqDto;
import com.boot3.myrestapi.lectures.dto.LectureResDto;
import com.boot3.myrestapi.lectures.hateoas.LectureResource;
import com.boot3.myrestapi.userinfo.UserInfo;
import com.boot3.myrestapi.userinfo.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/lectures", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class LectureController {
    private final LectureRepository lectureRepository;
    private final ModelMapper modelMapper;
    private final LectureValidator lectureValidator;

    //Constructor Injection
//    public LectureController(LectureRepository lectureRepository) {
//        this.lectureRepository = lectureRepository;
//    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity updateLecture(@PathVariable Integer id,
                                        @RequestBody @Valid LectureReqDto lectureReqDto,
                                        Errors errors,
                                        @CurrentUser UserInfo currentUser) throws Exception {

        Optional<Lecture> optionalLecture = lectureRepository.findById(id);
        if (optionalLecture.isEmpty()) {
            throw new BusinessException(id + " Lecture Not Found", HttpStatus.NOT_FOUND);
        }
        //입력항목 검증
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        //비지니스 로직에 따른 입력항목 검증
        lectureValidator.validate(lectureReqDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        //Optional객체에서 Lecture 객체를 꺼낸다
        Lecture existingLecture = optionalLecture.get();
        //등록한 사용자만 수정할 권한이 있는지 체크한다.
        if((existingLecture.getUserInfo() != null) &&
                (!existingLecture.getUserInfo().equals(currentUser))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        //LectureReqDto 객체를 Lecture 로 값을 복사한다
        this.modelMapper.map(lectureReqDto, existingLecture);
        //DB에 update
        existingLecture.update();
        Lecture savedLecture = this.lectureRepository.save(existingLecture);
        //Lecture 를 LectureResDto로 변환
        LectureResDto lectureResDto = modelMapper.map(savedLecture, LectureResDto.class);
        if(currentUser != null)
            lectureResDto.setEmail(savedLecture.getUserInfo().getEmail());
        //LectureResDto를 LectureResouce로 Wrapping하기
        LectureResource lectureResource = new LectureResource(lectureResDto);
        return ResponseEntity.ok(lectureResource);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity getLecture(@PathVariable Integer id,
                                     @CurrentUser UserInfo currentUser) throws Exception {
        Optional<Lecture> optionalLecture = this.lectureRepository.findById(id);
        if(optionalLecture.isEmpty()) {
            //return ResponseEntity.notFound().build(); //404
            throw new BusinessException(id + " Lecture Not Found", HttpStatus.NOT_FOUND);
        }
        Lecture lecture = optionalLecture.get();
        LectureResDto lectureResDto = modelMapper.map(lecture, LectureResDto.class);
        if(currentUser != null)
            lectureResDto.setEmail(lecture.getUserInfo().getEmail());

        LectureResource lectureResource = new LectureResource(lectureResDto);
        if((lecture.getUserInfo() != null) && (lecture.getUserInfo().equals(currentUser))) {
            lectureResource.add(linkTo(LectureController.class)
                    .slash(lecture.getId()).withRel("update-lecture"));
        }
        return ResponseEntity.ok(lectureResource);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity queryLectures(Pageable pageable,
                                        PagedResourcesAssembler<LectureResDto> assembler,
                                        @CurrentUser UserInfo currentUser) throws Exception {
        Page<Lecture> lecturePage = this.lectureRepository.findAll(pageable);
        //Page<Lecture> => Page<LectureResDto>
        Page<LectureResDto> lectureResDtoPage =
                lecturePage.map(lecture -> {
                    LectureResDto lectureResDto = new LectureResDto();
                    if(lecture.getUserInfo() != null){
                        lectureResDto.setEmail(lecture.getUserInfo().getEmail());
                    }
                    modelMapper.map(lecture, lectureResDto);
                    return lectureResDto;
                });
        //Page<LectureResDto> => PagedModel<EntityModel<LectureResDto>>
        //PagedModel<EntityModel<LectureResDto>> pagedModel = assembler.toModel(lectureResDtoPage);

        PagedModel<LectureResource> pagedModel =
                assembler.toModel(lectureResDtoPage, lectureResDto -> new LectureResource(lectureResDto));

        if(currentUser != null) {
            pagedModel.add(linkTo(LectureController.class).withRel("create-lecture"));
        }
        return ResponseEntity.ok(pagedModel);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity createLecture(@RequestBody @Valid LectureReqDto lectureReqDto,
                                        Errors errors,
                                        @CurrentUser UserInfo currentUser) throws Exception {
        //입력항목 검증
        if(errors.hasErrors()) {
            return badRequest(errors);
        }
        
        //비지니스 로직에 따른 입력항목 검증
        this.lectureValidator.validate(lectureReqDto, errors);
        if(errors.hasErrors()) {
            return badRequest(errors);
        }
        
        Lecture lecture = modelMapper.map(lectureReqDto, Lecture.class);
        //free와 offline 필드값 수정하기
        lecture.update();
        lecture.setUserInfo(currentUser);
        Lecture addLecture = lectureRepository.save(lecture);
        //Lecture를  LectureResDto로 매핑
        LectureResDto lectureResDto = modelMapper.map(addLecture, LectureResDto.class);
        lectureResDto.setEmail(addLecture.getUserInfo().getEmail());

        // http://localhost:8080/api/lectures/10
        WebMvcLinkBuilder selfLinkBuilder =
                WebMvcLinkBuilder.linkTo(LectureController.class).slash(addLecture.getId());
        URI createUri = selfLinkBuilder.toUri();

        LectureResource lectureResource = new LectureResource(lectureResDto);
        //"query-lectures": { "href": "http://localhost:8080/api/lectures"}
        lectureResource.add(linkTo(LectureController.class).withRel("query-lectures"));
        //"update-lecture": { "href": "http://localhost:8080/api/lectures/1"}
        lectureResource.add(selfLinkBuilder.withRel("update-lecture"));

        return ResponseEntity.created(createUri).body(lectureResource);
    }

    private static ResponseEntity<ErrorsResource> badRequest(Errors errors) {

        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}