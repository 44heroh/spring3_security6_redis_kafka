package com.example.spring3security6docker.mapper;

import com.example.spring3security6docker.dao.entity.Course;
import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import com.example.spring3security6docker.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

   CourseMapper INSTANCE = Mappers.getMapper( CourseMapper.class );

    CourseDto toDto(Course source);
    CourseDto requestToDto(CourseCreateRequestDto source);
    Course toEntity(CourseDto source);
    Course requestToEntity(CourseCreateRequestDto source);
}
