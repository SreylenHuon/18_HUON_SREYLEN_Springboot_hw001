package com.sreylen.springboothw001.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseTicket<T> {
    private boolean success;
    private String message;
    private HttpStatus status;
    private T payload;
    private LocalDate timestamp;

    public static <T> ApiResponseTicket<T> createSuccessMessage(String message, T payload) {
        return new ApiResponseTicket<>(true, message, HttpStatus.OK, payload, LocalDate.now());
    }

    public static <T> ApiResponseTicket<T> createErrorMessage(String message, HttpStatus status) {
        return new ApiResponseTicket<>(false, message, status, null, LocalDate.now());
    }
}