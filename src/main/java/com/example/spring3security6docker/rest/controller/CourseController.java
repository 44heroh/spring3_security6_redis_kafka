package com.example.spring3security6docker.rest.controller;

import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import com.example.spring3security6docker.dto.CourseDto;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.mapper.CourseMapper;
import com.example.spring3security6docker.repository.CourseRepository;
import com.example.spring3security6docker.service.CourseService;
import com.example.spring3security6docker.service.impl.RedisServiceImp;
import com.example.spring3security6docker.service.impl.CourseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/course")
public class CourseController {

    private final CourseRepository repository;
    private final CourseService service;
    private final Logger logger;
    private final CourseMapper mapper;
    private final RedisServiceImp<String, Object> redisService;

    public CourseController(
            CourseRepository repository,
            CourseServiceImpl service,
            CourseMapper mapper,
            RedisServiceImp redisService
    ) {
        this.repository = repository;
        this.service = service;
        this.logger = LoggerFactory.getLogger(CourseController.class);
        this.mapper = mapper;
        this.redisService = redisService;
    }

    @GetMapping("/last5")
    public ResponseEntity<List<CourseDto>> last5() throws Exception {
        return ResponseEntity.ok(service.last5());
    }

    @PostMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<CourseDto>> getAllCourses(
            @RequestBody PagerQueryDto pagerQueryDto
    ) throws ParseException {
        System.out.println("PagerQueryDto => ");
        System.out.println(pagerQueryDto);

        // hand cache
//        Page<CourseDto> courses;
//        final String key = String.format("courses:%s", pagerQueryDto).trim();
//        logger.info("getAllCourses key => " + key);
//        logger.info("getAllCourses by key from redis => " + redisService.getValue(key));
//        if (redisService.getValue(key) == null) {
//            courses = service.getCourses(pagerQueryDto);
//            logger.info("getAllCourses => " + courses);
//            redisService.setValue(key, courses);
//        } else {
//            courses = (Page<CourseDto>) redisService.getValue(key);
//        }

        // auto cache
        Page<CourseDto> courses = service.getCourses(pagerQueryDto);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Optional<CourseDto>> getCourseById(
        @PathVariable Long id
    ) throws Exception {
        Optional<CourseDto> courseDto = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            courseDto = service.getCourseById(id).map(mapper::toDto);
        } catch (NoSuchElementException e) { // если объект не будет найден
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(courseDto);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<CourseDto> create(@RequestBody @Valid CourseCreateRequestDto request) throws Exception {
        return ResponseEntity.ok(mapper.toDto(service.save(request)));
    }
}
