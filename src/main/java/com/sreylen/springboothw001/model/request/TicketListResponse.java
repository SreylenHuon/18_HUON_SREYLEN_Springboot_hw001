package com.sreylen.springboothw001.model.request;

import com.kshrd.springhomework.model.entity.Ticket;
import com.sreylen.springboothw001.model.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TicketListResponse {
    private List<Ticket> items;
    private PaginationInfo pagination;
}
