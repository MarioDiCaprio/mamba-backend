package com.mariodicaprio.mamba.responses;


import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
public class PageResponse<T> {

    private final int totalPages;

    private final int index;

    private final int size;

    private final List<T> content;

    public PageResponse(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.index = page.getNumber() + 1;
        this.size = page.getSize();
        this.content = page.getContent();
    }

}
