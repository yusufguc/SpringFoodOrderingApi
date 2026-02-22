package com.yusufguc.pagination;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RestPageableResponse<T> {

    private List<T> content;

    private int pageNumber;
    private int pageSize;

    private long totalElements;
    private int totalPages;

    private boolean first;
    private boolean last;
}