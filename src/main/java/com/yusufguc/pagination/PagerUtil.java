package com.yusufguc.pagination;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@UtilityClass
public class PagerUtil {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    private static final List<String> ALLOWED_SORT_FIELDS =
            List.of("name", "createdAt", "city");

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
}