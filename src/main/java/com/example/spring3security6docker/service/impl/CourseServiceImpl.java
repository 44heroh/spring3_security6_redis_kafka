package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import com.example.spring3security6docker.pager.Pager;
import com.example.spring3security6docker.rest.controller.CourseController;
import com.example.spring3security6docker.dao.entity.Course;
import com.example.spring3security6docker.dto.CourseDto;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.mapper.CourseMapper;
import com.example.spring3security6docker.repository.CourseRepository;
import com.example.spring3security6docker.service.CourseService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service
// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
@CacheConfig(cacheNames = "courseCache")
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    public static final String ID_COLUMN = "id";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
    private final Logger logger;
    private final CourseMapper mapper;

    public CourseServiceImpl(
            CourseRepository repository,
            CourseMapper mapper
    ) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(CourseController.class);
        this.mapper = mapper;
    }

    @Override
    @Cacheable(cacheNames = "courses")
    public Page<CourseDto> getCourses(PagerQueryDto pagerQueryDto) {
        Pager pager = new Pager(pagerQueryDto);
        Page<Course> ans = repository.findAll(pager.getPageRequest());
        return convertPageCourseToDTO(ans);
    }

    @Override
    @Cacheable(cacheNames = "course", key = "#id", unless = "#result == null")
    public Optional<Course> getCourseById(Long id) throws Exception {
        Optional<Course> course = repository.findById(id);
        if(course.isEmpty()) {
            throw new NoSuchElementException("id=" + id + " not found");
        }
        return repository.findById(id);
    }

    @Override
    @CacheEvict(cacheNames = "customers", allEntries = true)
    public Course save(CourseCreateRequestDto request) {
        boolean exists = !repository.findByParams(request.getAuthor(), request.getName()).isEmpty();
        if(exists){
            throw new IllegalArgumentException("User with : " + request.getName() +", " + request.getAuthor() + " already exists");
        }

        Course course = new Course(request);

        return repository.save(course);
    }

    @Override
    public List<CourseDto> last5() {
        Optional<List<Course>> ans = repository.findLast5();
        return convertListCourseToDTO(ans.orElseThrow());
    }

    public Page<CourseDto> convertPageCourseToDTO(Page<Course> source) {
        return source.map(mapper::toDto);
    }

    public List<CourseDto> convertListCourseToDTO(List<Course> source) {
        return source.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
