package com.sreylen.springboothw001.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationInfo {
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}