package com.majumundur.Models.DTO.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse <T> {

    private Integer totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private Integer size;
    private T data;
}

