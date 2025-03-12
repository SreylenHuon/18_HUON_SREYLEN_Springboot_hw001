package com.sreylen.springboothw001.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Paginationinfo {
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}