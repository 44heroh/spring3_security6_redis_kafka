package com.example.spring3security6docker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class PageConfig {

    public static final String COLUMN_SORT_DEFAULT = "id";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
}
