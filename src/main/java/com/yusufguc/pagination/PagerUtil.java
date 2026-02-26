package com.yusufguc.pagination;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;

/**
 * Utility class to handle pagination and sorting conversions.
 * Bridges the gap between Spring Data's Pageable and custom REST pagination objects.
 */
@UtilityClass
public class PagerUtil {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Whitelist of fields allowed for sorting to prevent SQL injection or invalid property references.
     */
    private static final List<String> ALLOWED_SORT_FIELDS =
            List.of("name", "createdAt", "price","stock");

    /**
     * Converts a custom RestPageableRequest into a Spring Data Pageable object.
     * Includes logic for default values, page size limits, and validated sorting.
     */
    public Pageable toPageable(RestPageableRequest request) {
        if (request == null) {
            return PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);
        }

        int page = request.getPageNumber() != null
                ? request.getPageNumber()
                : DEFAULT_PAGE;

        int size = request.getPageSize() != null
                ? request.getPageSize()
                : DEFAULT_SIZE;

        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }

        if (request.getSortBy() != null &&
                ALLOWED_SORT_FIELDS.contains(request.getSortBy())) {

            Sort.Direction direction =
                    request.getSortDirection() == SortDirection.DESC
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;

            return PageRequest.of(page, size,
                    Sort.by(direction, request.getSortBy()));
        }
        return PageRequest.of(page, size);
    }

    /**
     * Maps a Spring Data Page object into a custom RestPageableResponse.
     * Uses a mapper function to transform entity objects into DTOs during the conversion.
     */
    public static <T, R> RestPageableResponse<R> toPageResponse(
            Page<T> page,
            Function<T, R> mapper
    ) {

        List<R> content = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        return RestPageableResponse.<R>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}