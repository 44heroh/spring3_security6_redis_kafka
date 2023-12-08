package com.example.spring3security6docker.pager;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class PageImplCustom<T> extends org.springframework.data.domain.PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageImplCustom(@JsonProperty("content") List<T> content,
                          @JsonProperty("number") int page,
                          @JsonProperty("size") int size,
                          @JsonProperty("totalElements") long total) {

        super(content, PageRequest.of(page, size), total);
    }
}
