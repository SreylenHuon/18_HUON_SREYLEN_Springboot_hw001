package com.sreylen.springboothw001;

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
public class ApiResponseTicket {
    private boolean success;
    private String message;
    private HttpStatus status;
    private Object payload;
    private LocalDate timestamp;

    // Static method to create a success response
    public static ApiResponseTicket createSuccessMessage(String message, Object payload) {
        ApiResponseTicket response = new ApiResponseTicket();
        response.setSuccess(true);
        response.setMessage(message);
        response.setStatus(HttpStatus.OK);
        response.setPayload(payload);
        response.setTimestamp(LocalDate.now());
        return response;
    }

    // Static method to create an error response
    public static ApiResponseTicket createErrorMessage(String message, HttpStatus status) {
        ApiResponseTicket response = new ApiResponseTicket();
        response.setSuccess(false);
        response.setMessage(message);
        response.setStatus(status);
        response.setPayload(null);
        response.setTimestamp(LocalDate.now());
        return response;
    }

    // Getters and setters remain the same
}