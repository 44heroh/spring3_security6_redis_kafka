package com.example.spring3security6docker.mapper;

import com.example.spring3security6docker.dao.entity.User;
import com.example.spring3security6docker.dto.UserDto;
import com.example.spring3security6docker.dto.request.SignupRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto map(User userEntity);
    @InheritInverseConfiguration
    User map(UserDto dto);
}
