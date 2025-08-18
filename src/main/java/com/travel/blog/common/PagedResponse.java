package com.travel.blog.common;

import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PagedResponse<T> {

    private List<T> data;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;

    public PagedResponse(Page<T> pageData) {
        this.data = pageData.getContent();
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalPages = pageData.getTotalPages();
        this.totalElements = pageData.getTotalElements();
    }
}
