package com.example.spring3security6docker.pager;

import com.example.spring3security6docker.config.PageConfig;
import com.example.spring3security6docker.dto.PagerQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

public class Pager {

    private PageConfig pageConfig;
    private int page;
    private int pageSize;
    private Sort sort;

    public Pager(PagerQueryDto pagerQueryDto) {
        page = pagerQueryDto.getPage() == null ? 1 : pagerQueryDto.getPage();
        pageSize = pagerQueryDto.getPageSize() == null ? pageConfig.PAGE_SIZE_DEFAULT : pagerQueryDto.getPageSize();
        String sortBy = pagerQueryDto.getSortBy() == null ? pageConfig.COLUMN_SORT_DEFAULT : pagerQueryDto.getSortBy();
        Sort.Direction direction = pagerQueryDto.getSortDirection() == null || pagerQueryDto.getSortDirection().trim().length() == 0 || pagerQueryDto.getSortDirection().trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        sort = Sort.by(direction, sortBy, pageConfig.COLUMN_SORT_DEFAULT);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(page - 1, pageSize, sort);
    }
}
