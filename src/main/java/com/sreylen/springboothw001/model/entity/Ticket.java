package com.sreylen.springboothw001.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private Long ticketId;
    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private String paymentStatus;
    private String ticketStatus;
    private String seatNumber;


    public Object getId() {
        return null;
    }
}