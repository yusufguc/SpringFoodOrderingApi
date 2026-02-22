package com.yusufguc.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPageableRequest {

    @Min(value = 0, message = "Page number cannot be negative")
    private Integer pageNumber = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    private Integer pageSize = 10;

    private String sortBy;

    private SortDirection sortDirection = SortDirection.ASC;

    private String search;
}
