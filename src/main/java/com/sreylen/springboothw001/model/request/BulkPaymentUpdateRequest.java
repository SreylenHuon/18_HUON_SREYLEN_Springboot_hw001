package com.sreylen.springboothw001.model.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BulkPaymentUpdateRequest {
    // Getters and Setters
    private List<Long> ticketIds;
    private String newPaymentStatus;

}
