package com.yusufguc.pagination;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * A generic wrapper for paginated API responses.
 * Standardizes the data structure for lists, including page metadata.
 */
@Getter
@Builder
public class RestPageableResponse<T> {

    /**
     * The list of data items for the current page.
     */
    private List<T> content;

    /**
     * The index of the current page (starts from 0).
     */
    private int pageNumber;

    /**
     * The number of elements requested per page.
     */
    private int pageSize;

    /**
     * The total count of elements across all pages in the database.
     */
    private long totalElements;

    /**
     * The total number of pages calculated based on the page size.
     */
    private int totalPages;

    /**
     * Indicates if this is the first page of the result set.
     */
    private boolean first;
    /**
     * Indicates if this is the last page of the result set.
     */
    private boolean last;
}