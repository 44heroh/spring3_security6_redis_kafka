package com.example.spring3security6docker.service;

import com.example.spring3security6docker.dao.entity.Course;
import com.example.spring3security6docker.dto.CourseDto;
import com.example.spring3security6docker.dto.PagerQueryDto;
import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    public Page<CourseDto> getCourses(PagerQueryDto pagerQueryDto);
    public Optional<Course> getCourseById(Long id) throws Exception;
    public Course save(CourseCreateRequestDto request);
    public List<CourseDto> last5();
}
