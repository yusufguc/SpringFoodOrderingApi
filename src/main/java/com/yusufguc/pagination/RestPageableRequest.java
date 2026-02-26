package com.yusufguc.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * Encapsulates pagination and sorting parameters for API requests.
 * Provides default values and validation constraints for consistent data fetching.
 */
@Getter
@Setter
public class RestPageableRequest {

    /**
     * The zero-based index of the page to retrieve.
     */
    @Min(value = 0, message = "Page number cannot be negative")
    private Integer pageNumber = 0;

    /**
     * The number of records to return per page.
     */
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    private Integer pageSize = 10;

    /**
     * The field name by which the result set should be sorted.
     */
    private String sortBy;

    /**
     * The direction of the sort (ASC for ascending, DESC for descending).
     */
    private SortDirection sortDirection = SortDirection.ASC;

    /**
     * An optional keyword for filtering or searching within the results.
     */
    private String search;
}
